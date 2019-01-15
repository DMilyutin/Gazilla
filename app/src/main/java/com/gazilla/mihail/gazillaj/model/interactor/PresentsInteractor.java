package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.ImgCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuDBCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuItemCallBack;

public class PresentsInteractor {

    public void menuServer(MenuCallBack menuCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.ollMenu(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""), menuCallBack, failCallBack);

    }

    public void menuDB(MenuDBCallBack menuDBCallBack){
        Initialization.repositoryDB.menuFromDB(menuDBCallBack);
    }

    public void myImgMenu(ImgCallBack imgCallBack){
        Initialization.repositoryDB.imgFromBD(imgCallBack);
    }

    public void myGifts(MenuItemCallBack menuItemCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.giftsOnServer(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                menuItemCallBack, failCallBack);
    }
}
