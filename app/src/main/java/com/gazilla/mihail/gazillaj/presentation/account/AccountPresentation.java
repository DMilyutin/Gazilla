package com.gazilla.mihail.gazillaj.presentation.account;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.model.interactor.AccountInteractor;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class AccountPresentation {


    private AccountView accountView;
    private AccountInteractor accountInteractor;
    private SharedPref sharedPref;

    public AccountPresentation(AccountView accountView, Context context) {
        this.accountView = accountView;
        accountInteractor = new AccountInteractor();
        sharedPref = new SharedPref(context);
    }

    public void checkUserInfo(){
        if(sharedPref.myPreff()){
            String n = sharedPref.getNameFromPref();
            String p = sharedPref.getPhoneFromPref();
            String e = sharedPref.getEmailFromPref();
            accountView.setUserInfo(n,p,e);
        }
    }

    private String checkFormatPhone(String s) {
        if (s==null||s.equals("")) return ""; // пусте поле
        s = s.replaceAll("\\s", "");
        s = s.replaceAll("-", "");
        if (s.length() < 11 || s.length() > 12) {
            accountView.showWorningDialog("Неверный формат номера");
            return "";
        }
        if (s.charAt(0) == '8' && s.length() == 11) {
            s = s.substring(1);
            if (!s.matches("\\d+")) {
                accountView.showWorningDialog("Неверный формат номера");
                return "";
            } else {
                return s;
            }
        } else if (s.charAt(0) == '+' && s.charAt(1) == '7' && s.length() == 12) {
            s = s.substring(2);
            if (!s.matches("\\d+")) {
                accountView.showWorningDialog("Неверный формат номера");
                return "";
            } else {
                return s;
            }
        } else {
            accountView.showWorningDialog("Неверный формат номера");
            return "";
        }
    }

    public void newUserInfo(String name, String phone, String email){
        accountView.showLoadingDialog();

        phone = checkFormatPhone(phone);

        sharedPref.saveName(name);
        sharedPref.saveMyPhone(phone);
        sharedPref.saveMyEmail(email);

        updateUserInfo(name, phone, email);
    }

    private void updateUserInfo(String name, String phone, String email){

        String dat = "email="+email+"&"+
                "name="+name+"&"+
                "phone="+phone;

        Log.i("Loog", dat);

        String signatur = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(),  dat);

        accountInteractor.updateUser(name, phone, email, signatur, new SuccessCallBack() {
            @Override
            public void reservResponse(Success success) {
                accountView.clouseAppDialog();
                if (success.isSuccess())
                    accountView.showWorningDialog("Ваши данные успешно сохранены");
                else{
                    accountView.showWorningDialog("Ошибка: данные использованы для другого аккаунта");
                    //new BugReport().sendBugInfo(success.getMessage(), "AccountPresentation.updateUserInfo.reservResponse");
                    //new BugReport().sendBugInfo("", "");
                }

            }

            @Override
            public void errorResponse(String error) {
                accountView.clouseAppDialog();
                new BugReport().sendBugInfo(error, "AccountPresentation.updateUserInfo.errorResponse");

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                accountView.clouseAppDialog();
                new BugReport().sendBugInfo("throwable.getMessage()", "AccountPresentation.updateUserInfo.setError.Throwable");

            }
        });
    }

}
