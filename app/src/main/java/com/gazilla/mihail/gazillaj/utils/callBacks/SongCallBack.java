package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.Song;

import java.util.List;

public interface SongCallBack {

    void songFromServer(List<Song> song);
    void errorSongFromServer(String error);

}
