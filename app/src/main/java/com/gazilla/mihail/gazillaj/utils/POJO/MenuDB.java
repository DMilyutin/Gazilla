package com.gazilla.mihail.gazillaj.utils.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MenuDB {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String nameCategory;
    private String gsonMenuItem;


    public MenuDB( String nameCategory, String gsonMenuItem) {

        this.nameCategory = nameCategory;
        this.gsonMenuItem = gsonMenuItem;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getGsonMenuItem() {
        return gsonMenuItem;
    }

    public void setGsonMenuItem(String gsonMenuItem) {
        this.gsonMenuItem = gsonMenuItem;
    }

}


