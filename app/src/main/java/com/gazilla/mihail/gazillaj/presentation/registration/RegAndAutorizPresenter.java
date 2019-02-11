package com.gazilla.mihail.gazillaj.presentation.registration;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.interactor.RegAndAutorizInteractor;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
/** Пресентор упраления активити {@link com.gazilla.mihail.gazillaj.ui.registration.RegAndAutorizActivity} */
public class RegAndAutorizPresenter {

   private RegAndAutorizView regAndAutorizView;
   private RegAndAutorizInteractor interactor;
    private SharedPref sharedPref;

    public RegAndAutorizPresenter(RegAndAutorizView regAndAutorizView, RegAndAutorizInteractor interactor, Context context) {
        this.regAndAutorizView = regAndAutorizView;
        this.interactor = interactor;
        sharedPref = new SharedPref(context);
    }

    //------------------------------регистрация-----------------------------------------
    /** Метод определения типа регистрации(проверка на пустой промо и регистрация с промокодом или рефералкой ) */
    public void regNewUser(boolean withPromo, String promo){
        if (withPromo && promo.equals("")){
            regAndAutorizView.showWarningDialog("Промокод не может быть пустым");
            return;
        }
        regAndAutorizView.showLoadingDialog();
        String type = poromoORrefer(promo);

        if (type.equals("Promo"))
            registrationApi("", "", "", "", promo);
        else
            registrationApi("", "", "",  promo, "");

    }
    /** Метод анализа промо поля (рефералка или промокод) */
    private String poromoORrefer(String text){
        if (text.contains("0")||text.contains("1")||text.contains("2")||text.contains("3")||
                text.contains("4")||text.contains("5")||text.contains("6")||text.contains("7")||
                text.contains("8")||text.contains("9"))
            return "Refer";
        else
            return "Promo";

    }
    /** Метод регистрации нового пользователя */
    private void registrationApi(String name, String phone, String email, String referer, String promo){
        interactor.registrationApi(name, phone, email, "", referer, promo, new AutorizationCallBack(){
            @Override
            public void AutorizCallBack(UserWithKeys userWithKeys) {
                regAndAutorizView.clouaeAppDialog();
                Initialization.setUserWithKeys(userWithKeys);
                saveKey();
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
    /** Метод запроса кода восстановления  */
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
    /** Метод отправки кода восстановления аккаунта */
    public void sendCodeForLogin(String code){
        interactor.sendCodeLoging(code, new AutorizationCallBack() {
            @Override
            public void AutorizCallBack(UserWithKeys userWithKeys) {
                Log.i("Loog", "защитный код отправлен");
                Initialization.setUserWithKeys(userWithKeys);
                saveKey();
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


    /** Сохранение ключей и данных пользователя при регистрации или авторизации */
    private void saveKey(){
        Log.i("Loog", "saveKey");
        sharedPref.saveNewPrivateKey(Initialization.userWithKeys.getPrivatekey());
        sharedPref.saveNewPublicKey(Initialization.userWithKeys.getPublickey());
        sharedPref.saveNewId(Initialization.userWithKeys.getId());
        sharedPref.saveName(Initialization.userWithKeys.getName());
        sharedPref.saveMyPhone(Initialization.userWithKeys.getPhone());
        sharedPref.saveMyEmail(Initialization.userWithKeys.getEmail());
    }

}
