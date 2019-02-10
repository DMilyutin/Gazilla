package com.gazilla.mihail.gazillaj.model.repository.MenuAdapter;

import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
/** класс конвертации списка меню для сохранения в базу данных рум */
public class MenuAdapterApiDb {

    private Gson GSON;


    public MenuAdapterApiDb() {
        this.GSON = new Gson();
    }

    /** формат для сохранения в бд */
    public List<MenuDB> fromMenuCategory(List<MenuCategory> menuCategoryList){

        List<MenuDB> menuDBList = new ArrayList<>();

        String name;
        String gsonMenuItem;
        List<MenuItem> itemList;

        for(int i = 0; i< menuCategoryList.size(); i++){

             name = menuCategoryList.get(i).getName();
             itemList = menuCategoryList.get(i).getItems();

             gsonMenuItem = GSON.toJson(itemList);

         MenuDB menuDB = new MenuDB(name, gsonMenuItem);
         menuDBList.add(menuDB);
        }
        return menuDBList;
    }
    /** Формат для работы в приложении */
    public List<MenuCategory> fromMenuDB(List<MenuDB> menuDBList){

        Type listType = new TypeToken<ArrayList<MenuItem>>(){}.getType();
        List<MenuCategory> menuCategoryList = new ArrayList<>();

        String name;
        List<MenuItem> itemList;

        for(int z = 0; z<menuDBList.size(); z++){
            name = menuDBList.get(z).getNameCategory();
            itemList = GSON.fromJson(menuDBList.get(z).getGsonMenuItem(), listType);

            MenuCategory menuCategory = new MenuCategory(name, itemList);

            menuCategoryList.add(menuCategory);
        }
        return menuCategoryList;
    }

}
