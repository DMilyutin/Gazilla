package com.gazilla.mihail.gazillaj.presentation.account;

import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.model.interactor.AccountInteractor;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class AccountPresentation {


    private AccountView accountView;
    private AccountInteractor accountInteractor;

    public AccountPresentation(AccountView accountView) {
        this.accountView = accountView;
        accountInteractor = new AccountInteractor();
    }

    public void updateUserInfo(String name, String phone, String email){

        String dat = "email="+email+"&"+
                "name="+name+"&"+
                "phone="+phone;

        String signatur = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(),  dat);
        Log.i("Loog", "udate phone - "+phone);
        accountInteractor.updateUser(name, phone, email, signatur, new SuccessCallBack() {
            @Override
            public void reservResponse(Success success) {
                accountView.responseUpdate(success);
            }

            @Override
            public void errorResponse(String error) {
                Log.i("Loog", "updateUser error- "+error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "updateUser throwable- "+throwable.getMessage());
            }
        });
    }

}
