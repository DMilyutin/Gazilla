package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class AccountInteractor {

    public void updateUser(String name, String phone, String email, String signature,
                           SuccessCallBack successCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.updateUserData(name, phone, email, signature, successCallBack, failCallBack);
    }

}
