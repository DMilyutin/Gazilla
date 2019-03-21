package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuItemCallBack;

public class PresentsInteractor {

    private InitializationAp initializationAp = InitializationAp.getInstance();

    public void menuServer(MenuCallBack menuCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().ollMenu(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""), menuCallBack, failCallBack);

    }

    public void myGifts(MenuItemCallBack menuItemCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().giftsOnServer(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""),
                menuItemCallBack, failCallBack);
    }
}
