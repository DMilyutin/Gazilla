package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class AccountInteractor {

    public void updateUser(String name, String phone, String email, String publicKey, String signature,
                           SuccessCallBack successCallBack, FailCallBack failCallBack){
        InitializationAp.getInstance().getRepositoryApi().updateUserData(name, phone, email, publicKey, signature, successCallBack, failCallBack);
    }

}
