package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.PlaylistSongs;

import java.util.List;

public interface PlaylistSongCallBack {

    void playlist(PlaylistSongs playlistSongs);
    void errorPlaylist(String error);
}
