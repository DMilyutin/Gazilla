package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class RegAndAutorizInteractor {

    public void getCodeForLogin(String phone, String password, SuccessCallBack successCallBack,
                             FailCallBack failCallBack){
        Initialization.repositoryApi.getCodeForLogin(phone, password, successCallBack, failCallBack);
    }

    public void sendCodeLoging(String code, AutorizationCallBack autorizationCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.sendCodeForLoging(code, autorizationCallBack, failCallBack);
    }

    public void registrationApi(String name, String phone, String email, String password, String referer, String promo,
                                AutorizationCallBack autorizationCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.registration(name, phone, email, password, referer, promo, autorizationCallBack, failCallBack);
    }
}
