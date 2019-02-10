package com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic;

import android.util.Log;

import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SongCallBack;

import java.util.List;

public class StockMusicPresenter {

    private PromoInteractor promoInteractor;
    private StockMusicView musicView;

    public StockMusicPresenter(StockMusicView stockMusicView) {
        musicView = stockMusicView;
        promoInteractor = new PromoInteractor();
    }

    public void getSong(){
        promoInteractor.playList(new SongCallBack() {
            @Override
            public void songFromServer(List<Song> song) {
                musicView.setPlayList(song);
            }

            @Override
            public void errorSongFromServer(String error) {
                Log.i("Loog", "Ошибка загрузки плейлиста " + error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "Ошибка загрузки плейлиста T" + throwable.getMessage());
            }
        });
    }
}
