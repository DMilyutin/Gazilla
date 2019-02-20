package com.gazilla.mihail.gazillaj.presentation.main.stock.StockMusic;

import com.gazilla.mihail.gazillaj.utils.POJO.Song;

import java.util.List;

public interface StockMusicView {
    void setPlayList(List<Song> song);
    void showErrorMes(String error);
    void backToPlaylist();
}
