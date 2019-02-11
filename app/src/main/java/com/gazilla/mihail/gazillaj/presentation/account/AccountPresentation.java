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
/** Презентер аккаунт активити {@link com.gazilla.mihail.gazillaj.ui.account.AccountActivity} */
public class AccountPresentation {

    /** Поле интерфейса для убравления активити */
    private AccountView accountView;
    /** Поле интератор для управления запросами */
    private AccountInteractor accountInteractor;
    /** Поле для работы с сохраненными данными для приложения */
    private SharedPref sharedPref;

    public AccountPresentation(AccountView accountView, Context context) {
        this.accountView = accountView;
        accountInteractor = new AccountInteractor();
        sharedPref = new SharedPref(context);
    }
    /** Метод поиска сохраненных данных о User */
    public void checkUserInfo(){
        if(sharedPref.myPreff()){
            String n = sharedPref.getNameFromPref();
            String p = sharedPref.getPhoneFromPref();
            String e = sharedPref.getEmailFromPref();
            /** Установка данных User в поля */
            accountView.setUserInfo(n,p,e);
        }
    }
    /** Метод анализа формата номера */
    /** Возращяет либо номер в нужном формате, либо пустую строку */
    private String checkFormatPhone(String s) {
        if (s==null||s.equals("")) return ""; // пусте поле

        if(s.charAt(0)=='8'&&s.length()==11){ // 8 ххх ххх хх хх
            return ""+ s.charAt(1)+s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10);
        }
        else if (s.charAt(0)=='+'&&s.charAt(1)=='7'&&s.length()==12) // +7 ххх ххх хх хх
            return ""+ s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10)+s.charAt(11);
        else if (s.charAt(0)=='9'&&s.length()==10) // 9хх ххх хх хх
            return s;
        else {
            accountView.showWorningDialog("Неверный формат номера");
            return "";
        }
    }
    /** Метод сохранения новых данных User */
    public void newUserInfo(String name, String phone, String email){
        accountView.showLoadingDialog();

        phone = checkFormatPhone(phone);

        sharedPref.saveName(name);
        sharedPref.saveMyPhone(phone);
        sharedPref.saveMyEmail(email);

        updateUserInfo(name, phone, email);
    }
    /** метод отправки новых данных на сервер */
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
                    accountView.showWorningDialog("Ошибка: "+success.getMessage());
                    new BugReport().sendBugInfo(success.getMessage(), "AccountPresentation.updateUserInfo.reservResponse");
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
