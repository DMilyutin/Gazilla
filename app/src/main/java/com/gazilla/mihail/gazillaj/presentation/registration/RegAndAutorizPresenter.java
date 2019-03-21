package com.gazilla.mihail.gazillaj.presentation.registration;

import android.content.Context;
import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.interactor.RegAndAutorizInteractor;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RegAndAutorizPresenter extends MvpPresenter<RegAndAutorizView> {

   private RegAndAutorizView regAndAutorizView;
   private RegAndAutorizInteractor interactor;
    private SharedPref sharedPref;
    private Context context;
    private InitializationAp initializationAp = InitializationAp.getInstance();

    public RegAndAutorizPresenter(RegAndAutorizView regAndAutorizView, RegAndAutorizInteractor interactor, Context context) {
        this.regAndAutorizView = regAndAutorizView;
        this.interactor = interactor;
        sharedPref = new SharedPref(context);
        this.context = context;
    }

    //------------------------------регистрация-----------------------------------------

    public void regNewUser(boolean withPromo, String promo){
        if (withPromo && promo.equals("")){
            regAndAutorizView.showWarningDialog("Промокод не может быть пустым");
            return;
        }
        regAndAutorizView.showLoadingDialog();
        String type = poromoORrefer(promo);


        Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return AdvertisingIdClient.getAdvertisingIdInfo(context).getId(); // id гугл рекламы
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {

                    if (type.equals("Promo"))
                        registrationApi("", "", "", "", promo, s);
                    else
                        registrationApi("", "", "",  promo, "", s);

                    Log.i("Loog", "id гугл рекламы" + s);
                }, throwable -> {
                    new BugReport().sendBugInfo(throwable.getMessage(), "Ошибка определения id девайса");
                    new AppDialogs().warningDialog(context, "Ошибка определения вашего ID");
                    Log.i("Loog", "id гугл рекламы T" + throwable.getMessage());
                } );

    }

    private String poromoORrefer(String text){
        if (text.matches("[0-9]+"))
            return "Refer";
        else
            return "Promo";
    }


    private void registrationApi(String name, String phone, String email, String referer, String promo, String myDeviceID){

        interactor.registrationApi(name, phone, email, "", referer, promo, myDeviceID,  new AutorizationCallBack(){
            @Override
            public void AutorizCallBack(UserWithKeys userWithKeys) {

                regAndAutorizView.clouaeAppDialog();
                initializationAp.setUserWithKeys(userWithKeys);
                saveKey(userWithKeys);
                regAndAutorizView.startProgramm(true);
            }
            @Override
            public void showError(String error) {

                if (error.contains("phone or email already in use")){
                    regAndAutorizView.clouaeAppDialog();
                    regAndAutorizView.showWarningDialog("Аккаунт с такими данными уже зарегестрирован");
                }
                else if (error.contains("referlink doesn't exist")){
                    regAndAutorizView.clouaeAppDialog();
                    regAndAutorizView.showWarningDialog("Неверный промокод");
                }
                else{
                    regAndAutorizView.showErrorDialog("Ошибка: " + error, "RegAndAutorizPresenter.registrationApi.shouError");
                    new BugReport().sendBugInfo(error, "RegAndAutorizPresenter.registrationApi.showError");
                }
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "t - " + throwable.getMessage());
                new BugReport().sendBugInfo(throwable.getMessage(), "RegAndAutorizPresenter.registrationApi.Throwable");
            }
        });
    }



    //-------------------------------авторизация-----------------------------------------

    public void getCodeForLogin(String phone, String email){
        Log.i("Loog", "восстановление акк");
        interactor.getCodeForLogin(phone, email, new SuccessCallBack() {
            @Override
            public void reservResponse(Success success) {
                if (success.isSuccess()){
                    Log.i("Loog", "код на почте");
                    regAndAutorizView.visibleETCode();
                }
            }
            @Override
            public void errorResponse(String error) {

                if (error.contains("unknown email"))
                    regAndAutorizView.showWarningDialog("Аккаунт с данной почтой не найден");
                else if (error.contains("unknown phone"))
                    regAndAutorizView.showWarningDialog("Аккаунт с данным номером не найден");
                else
                    new BugReport().sendBugInfo(error, "RegAndAutorizPresenter.getCodeForLogin.showError");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "RegAndAutorizPresenter.getCodeForLogin.setError.Throwable");
            }
        });
    }

    public void sendCodeForLogin(String code){
        interactor.sendCodeLoging(code, new AutorizationCallBack() {
            @Override
            public void AutorizCallBack(UserWithKeys userWithKeys) {
                Log.i("Loog", "защитный код отправлен");
                InitializationAp.getInstance().setUserWithKeys(userWithKeys);
                saveKey(userWithKeys);
                regAndAutorizView.startProgramm(true);
            }

            @Override
            public void showError(String error) {
                if (error.contains("code doesn't exist"))
                    regAndAutorizView.showWarningDialog("Неверный код");
                else
                    new BugReport().sendBugInfo(error, "RegAndAutorizPresenter.sendCodeForLogin.showError");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "RegAndAutorizPresenter.sendCodeForLogin.setError.Throwable");
            }
        });
    }



    private void saveKey(UserWithKeys userWithKeys){
        Log.i("Loog", "saveKey");

        sharedPref.saveNewPrivateKey(initializationAp.getUserWithKeys().getPrivatekey());
        sharedPref.saveNewPublicKey(initializationAp.getUserWithKeys().getPublickey());
        sharedPref.saveNewId(initializationAp.getUserWithKeys().getId());
        sharedPref.saveName(initializationAp.getUserWithKeys().getName());
        sharedPref.saveMyPhone(initializationAp.getUserWithKeys().getPhone());
        sharedPref.saveMyEmail(initializationAp.getUserWithKeys().getEmail());
    }

}
