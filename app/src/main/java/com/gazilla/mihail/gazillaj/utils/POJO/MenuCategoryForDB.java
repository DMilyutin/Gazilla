package com.gazilla.mihail.gazillaj.utils.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class MenuCategoryForDB {

    @PrimaryKey
    private int id;
    private String name;

    public MenuCategoryForDB(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
