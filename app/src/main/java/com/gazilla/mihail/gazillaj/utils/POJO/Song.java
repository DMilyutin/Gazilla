package com.gazilla.mihail.gazillaj.utils.POJO;

public class Song {

    private int id;
    private String name;
    private String artist;
    private String length;

    public Song(int id, String name, String artist, String length) {
        this.id = id;
        this.name = name;
        this.artist = artist;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getLength() {
        return length;
    }
}
