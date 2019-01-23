package com.gazilla.mihail.gazillaj.presentation.registration;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.interactor.RegAndAutorizInteractor;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class RegAndAutorizPresenter {

   private RegAndAutorizView regAndAutorizView;
   private RegAndAutorizInteractor interactor;
    private SharedPref sharedPref;

    public RegAndAutorizPresenter(RegAndAutorizView regAndAutorizView, RegAndAutorizInteractor interactor, Context context) {
        this.regAndAutorizView = regAndAutorizView;
        this.interactor = interactor;
        sharedPref = new SharedPref(context);
    }

    public void detCodeForLogin(String phone, String email){
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
                Log.i("Loog", "акк не найден - " + error);
                if (error.contains("unknown email"))
                    regAndAutorizView.showErrorr("Аккаунт с данной почтой не найден");
                else if (error.contains("unknown phone"))
                    regAndAutorizView.showErrorr("Аккаунт с данным номером не найден");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });
    }

    public void sendCodeForLogin(String code){
        Log.i("Loog", "отправка защитного кода");
        interactor.sendCodeLoging(code, new AutorizationCallBack() {
            @Override
            public void AutorizCallBack(UserWithKeys userWithKeys) {
                Log.i("Loog", "защитный код отправлен");
                Initialization.setUserWithKeys(userWithKeys);
                saveKey();
                regAndAutorizView.startProgramm(true);
            }

            @Override
            public void shouError(String error) {
                Log.i("Loog", "защитный код ошибка - " + error);
                if (error.contains("code doesn't exist"))
                    regAndAutorizView.showErrorr("Неверный код");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "защитный код ошибкаТ - " + throwable.getMessage());
            }
        });
    }

    public void registrationApi(String name, String phone, String email, String referer, String promo){


        Log.i("Loog", "registration");
        interactor.registrationApi(name, phone, email, "", referer, promo, new AutorizationCallBack(){
            @Override
            public void AutorizCallBack(UserWithKeys userWithKeys) {
                Initialization.setUserWithKeys(userWithKeys);
                saveKey();
                regAndAutorizView.startProgramm(true);
            }

            @Override
            public void shouError(String error) {
                Log.i("Loog", "registration err - " + error);
                if (error.contains("phone or email already in use"))
                    regAndAutorizView.showErrorr("Аккаунт с такими данными уже зарегестрирован");
                else if (error.contains("referlink doesn't exist"))
                    regAndAutorizView.showErrorr("Аккаунт с такими данными уже зарегестрирован");
                //regAndAutorizView.startProgramm(false);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "t - " + throwable.getMessage());
            }
        });
    }

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
