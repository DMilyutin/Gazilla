package com.gazilla.mihail.gazillaj.presentation.account;

import android.content.SharedPreferences;
import android.util.Log;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.model.interactor.AccountInteractor;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

@InjectViewState
public class AccountPresentation extends MvpPresenter<AccountView> {

    private AccountInteractor accountInteractor;
    private SharedPreferences sharedPref;
    private InitializationAp initializationAp = InitializationAp.getInstance();


    public AccountPresentation(SharedPreferences sharedPref ) {
        accountInteractor = new AccountInteractor();
        this.sharedPref = sharedPref;
    }

    public void checkUserInfo(){
        if (sharedPref!=null){
            String n = sharedPref.getString("myName", "");
            String p = sharedPref.getString("myPhone", "");
            String e = sharedPref.getString("myEmail", "");
            String id = String.valueOf(initializationAp.getUserWithKeys().getId());

            if (getViewState()!=null)
            getViewState().setUserInfo(n,p,e, id);
            else
                Log.i("Loog", "getViewState - null");
        }
    }

    private String checkFormatPhone(String s) {
        if (s==null||s.equals("")) return ""; // пусте поле
        s = s.replaceAll("\\s", "");
        s = s.replaceAll("-", "");
        if (s.length() < 11 || s.length() > 12) {
            getViewState().showWorningDialog("Неверный формат номера");
            return "";
        }
        if (s.charAt(0) == '8' && s.length() == 11) {
            s = s.substring(1);
            if (!s.matches("\\d+")) {
                getViewState().showWorningDialog("Неверный формат номера");
                return "";
            } else {
                return s;
            }
        } else if (s.charAt(0) == '+' && s.charAt(1) == '7' && s.length() == 12) {
            s = s.substring(2);
            if (!s.matches("\\d+")) {
                getViewState().showWorningDialog("Неверный формат номера");
                return "";
            } else {
                return s;
            }
        } else {
            getViewState().showWorningDialog("Неверный формат номера");
            return "";
        }
    }

    public void newUserInfo(String name, String phone, String email){
        getViewState().showLoadingDialog();

        phone = checkFormatPhone(phone);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (name==null)
            name="";
        if (phone==null)
            phone="";
        if (email==null)
            email="";

        editor.putString("myName", name);
        editor.putString("myPhone", phone);
        editor.putString("myEmail", email);
        editor.commit();

        /*sharedPref.saveName(name);
        sharedPref.saveMyPhone(phone);
        sharedPref.saveMyEmail(email);*/

        updateUserInfo(name, phone, email);
    }

    private void updateUserInfo(String name, String phone, String email){

        String dat = "email="+email+"&"+
                "name="+name+"&"+
                "phone="+phone;

        Log.i("Loog", dat);

        String signatur = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(),  dat);

        accountInteractor.updateUser(name, phone, email, initializationAp.getUserWithKeys().getPublickey(), signatur, new SuccessCallBack() {
            @Override
            public void reservResponse(Success success) {
                getViewState().clouseAppDialog();
                if (success.isSuccess())
                    getViewState().showWorningDialog("Ваши данные успешно сохранены");
                else{
                    getViewState().showWorningDialog("Ошибка: данные использованы для другого аккаунта");
                    //new BugReport().sendBugInfo(success.getMessage(), "AccountPresentation.updateUserInfo.reservResponse");
                    //new BugReport().sendBugInfo("", "");
                }

            }

            @Override
            public void errorResponse(String error) {
                getViewState().clouseAppDialog();
                new BugReport().sendBugInfo(error, "AccountPresentation.updateUserInfo.errorResponse");

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                getViewState().clouseAppDialog();
                new BugReport().sendBugInfo("throwable.getMessage()", "AccountPresentation.updateUserInfo.setError.Throwable");

            }
        });
    }

    @Override
    public void onDestroy() {
        Log.i("Loog", "презентор уничтожен");
        super.onDestroy();
    }
}
