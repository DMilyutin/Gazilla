package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.callBacks.DragonWeyCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PlaylistSongCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SongCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

public class PromoInteractor {

    private InitializationAp initializationAp = InitializationAp.getInstance();

    public void promoFromDB(PromoCallBack promoCallBack, FailCallBack failCallBack){
        //InitializationAp.repositoryDB.promoFromDB(promoCallBack);

        initializationAp.getRepositoryApi().ollPromo(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""),
                promoCallBack, failCallBack);
    }

    public void dragonStock(DragonWeyCallBack dragonWeyCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().dragonwing(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""),
                dragonWeyCallBack, failCallBack);
    }

    public void playList(PlaylistSongCallBack playlistSongCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().playListFromServer(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""), playlistSongCallBack, failCallBack);
    }

    public void ollSong(SongCallBack songCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().songFromServer(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), ""), songCallBack, failCallBack);
    }

    public void sendNextSong(int id, String publKey, String signatura, SuccessCallBack successCallBack, FailCallBack failCallBack){
        initializationAp.getRepositoryApi().sendNextSong(id, publKey, signatura, successCallBack, failCallBack);
    }
}
