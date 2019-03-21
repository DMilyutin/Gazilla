package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class RegAndAutorizInteractor {

    private InitializationAp initializationAp = InitializationAp.getInstance();

    public void getCodeForLogin(String phone, String password, SuccessCallBack successCallBack,
                             FailCallBack failCallBack){
        initializationAp.getRepositoryApi().getCodeForLogin(phone, password, successCallBack, failCallBack);
    }

    public void sendCodeLoging(String code, AutorizationCallBack autorizationCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().sendCodeForLoging(code, autorizationCallBack, failCallBack);
    }

    public void registrationApi(String name, String phone, String email, String password, String referer, String promo, String myDiviceID,
                                AutorizationCallBack autorizationCallBack, FailCallBack failCallBack){

        initializationAp.getRepositoryApi().registration(name, phone, email, password, referer, promo, myDiviceID,autorizationCallBack, failCallBack);
    }

}
