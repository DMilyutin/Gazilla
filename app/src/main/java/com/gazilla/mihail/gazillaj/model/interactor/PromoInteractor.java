package com.gazilla.mihail.gazillaj.model.interactor;

import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.DragonWeyCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PlaylistSongCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SongCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

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

    public void playList(PlaylistSongCallBack playlistSongCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.playListFromServer(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""), playlistSongCallBack, failCallBack);
    }

    public void ollSong(SongCallBack songCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.songFromServer(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""), songCallBack, failCallBack);
    }

    public void sendNextSong(int id, String publKey, String signatura, SuccessCallBack successCallBack, FailCallBack failCallBack){
        Initialization.repositoryApi.sendNextSong(id, publKey, signatura, successCallBack, failCallBack);
    }
}
