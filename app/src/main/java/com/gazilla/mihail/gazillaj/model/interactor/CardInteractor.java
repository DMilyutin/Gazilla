package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LevelsCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.QTYCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.WheelCallBack;

public class CardInteractor {


    private InitializationAp initializationAp = InitializationAp.getInstance();

    public void mySpins(QTYCallBack qtyCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().mySpins(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""),
                qtyCallBack, failCallBack);
    }

    public void wheeling(WheelCallBack wheelCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().wheeling(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""),
                wheelCallBack, failCallBack);

    }

    public void level(String publickKey, String signature, LevelsCallBack levelsCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().levels(publickKey, signature, levelsCallBack, failCallBack);
    }
}
