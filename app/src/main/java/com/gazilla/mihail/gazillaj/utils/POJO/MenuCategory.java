package com.gazilla.mihail.gazillaj.utils.POJO;

import java.util.List;


public class MenuCategory{

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }
}
