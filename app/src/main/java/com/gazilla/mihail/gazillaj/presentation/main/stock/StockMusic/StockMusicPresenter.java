package com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic;

import android.util.Log;

import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.POJO.PlaylistSongs;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PlaylistSongCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SongCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

import java.util.List;

public class StockMusicPresenter {

    private PromoInteractor promoInteractor;
    private StockMusicView musicView;
    private boolean isPlaying;

    public StockMusicPresenter(StockMusicView stockMusicView) {
        musicView = stockMusicView;
        promoInteractor = new PromoInteractor();
        isPlaying= false;
    }

    public void getPlayList(){
        promoInteractor.playList(new PlaylistSongCallBack() {
            @Override
            public void playlist(PlaylistSongs playlistSongs) {
                if (playlistSongs!=null){
                if (playlistSongs.getPlaing()){
                    isPlaying=true;
                    if (playlistSongs.getSong()!=null)
                        musicView.setPlayList(playlistSongs.getSong());
                    else
                        musicView.showErrorMes("В очереди ничего нет");
                }
                else
                    musicView.showErrorMes("Плеер сейчас выключен");
                }
            }

            @Override
            public void errorPlaylist(String error) {
                musicView.showErrorMes("Непредвиденная ошибка. Приносим свои извенения");
                Log.i("Loog", "Ошибка загрузки плейлиста " + error);
                new BugReport().sendBugInfo(error, "StockMusicPresenter.getPlayList.errorSongFromServer");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                musicView.showErrorMes("Непредвиденная ошибка. Приносим свои извенения");
                Log.i("Loog", "Ошибка загрузки плейлиста T" + throwable.getMessage());
                new BugReport().sendBugInfo(throwable.getMessage(), "StockMusicPresenter.getPlayList.setError.Throwable");
            }
        });

    }

    public void getOllSongForNextSong(){
        promoInteractor.ollSong(new SongCallBack() {
            @Override
            public void songFromServer(List<Song> song) {
                if (song!=null)
                musicView.setPlayList(song);
            }

            @Override
            public void errorSongFromServer(String error) {
                musicView.showErrorMes("Непредвиденная ошибка. Приносим свои извенения");
                new BugReport().sendBugInfo(error, "StockMusicPresenter.getOllSongForNextSong.errorSongFromServer");

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                musicView.showErrorMes("Непредвиденная ошибка. Приносим свои извенения");
                new BugReport().sendBugInfo(throwable.getMessage(), "StockMusicPresenter.getOllSongForNextSong.setError.Throwable");
            }
        });

    }

    public void sendMyNextSong(Song song){
        String dat = "next="+ song.getId();

        String publicKey = Initialization.userWithKeys.getPublickey();
        String signature = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), dat);

        promoInteractor.sendNextSong(song.getId(), publicKey, signature, new SuccessCallBack() {
            @Override
            public void reservResponse(Success success) {
                if (success.isSuccess()){
                    musicView.backToPlaylist();
                    musicView.showErrorMes("Песня успешно добавлена в очередь");

                }
                else {
                    /*switch (success.getMessage()){
                        case "permission denied":
                            musicView.showErrorMes("Отказано в разрешении");
                            break;
                        case "unknown song":
                            musicView.showErrorMes("Неизвестная песня");
                            break;
                        case "already in queue":
                            musicView.showErrorMes("Песня уже в очереди");
                            break;
                        case "song was played recently":
                            musicView.showErrorMes("Эта песня недавно играла, попробуйте позже");
                            break;
                        case "too much for u":
                            musicView.showErrorMes("У вас уже 2 песни в очереди");
                            break;
                    }*/
                    musicView.showErrorMes(success.getMessage());
                }
            }

            @Override
            public void errorResponse(String error) {
                musicView.showErrorMes("Непредвиденная ошибка. Приносим свои извенения");
                new BugReport().sendBugInfo(error, "StockMusicPresenter.sendMyNextSong.errorResponse");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                musicView.showErrorMes("Непредвиденная ошибка. Приносим свои извенения");
                new BugReport().sendBugInfo(throwable.getMessage(), "StockMusicPresenter.sendMyNextSong.setError.Throwable");
            }
        });
    }

    public Boolean getPlaying() {
        return isPlaying;
    }
}
