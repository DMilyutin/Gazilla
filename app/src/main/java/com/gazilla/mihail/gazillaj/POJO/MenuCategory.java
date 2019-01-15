package com.gazilla.mihail.gazillaj.POJO;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;


public class MenuCategory {


    private int id;

    private String name;
    private List<MenuItem> items;


    public MenuCategory(int id, String name, List<MenuItem> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public MenuCategory(String name, List<MenuItem> items) {

        this.name = name;
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public int getId() {
        return id;
    }
}
