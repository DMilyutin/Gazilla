package com.gazilla.mihail.gazillaj.utils.POJO;

import java.util.List;

public class PlaylistSongs {

    private Boolean isPlaying;
    private List<Song> list;

    public PlaylistSongs(Boolean isPlaying, List<Song> list) {
        this.isPlaying = isPlaying;
        this.list = list;
    }

    public Boolean getPlaing() {
        return isPlaying;
    }

    public List<Song> getSong() {
        return list;
    }
}
