package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.DragonWeyCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;

public class PromoInteractor {

    public void promoFromDB(PromoCallBack promoCallBack, FailCallBack failCallBack){
        //Initialization.repositoryDB.promoFromDB(promoCallBack);

        Initialization.repositoryApi.ollPromo(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                promoCallBack, failCallBack);
    }

    public void promoFromDB2(PromoCallBack promoCallBack){
        Initialization.repositoryDB.promoFromDB(promoCallBack);
    }

    public void dragonStock(DragonWeyCallBack dragonWeyCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.dragonwing(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                dragonWeyCallBack, failCallBack);
    }
}
