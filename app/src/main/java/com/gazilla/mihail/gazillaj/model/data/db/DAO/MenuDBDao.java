package com.gazilla.mihail.gazillaj.model.data.db.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gazilla.mihail.gazillaj.POJO.MenuDB;


import java.util.List;

@Dao
public interface MenuDBDao {

    @Query("SELECT * FROM MenuDB")
    List<MenuDB> getOllMenuItemDB();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void newMenuDB(List<MenuDB> menuDBList);

    @Query("DELETE FROM MenuDB")
    void deleteOllTable();

    //___________________________________________________________

    @Query("SELECT * FROM MenuDB WHERE id = :id")
    MenuDB getMenuItemByIdDB(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void newMenuItemDB(MenuDB menuDB);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMenuItemDB(MenuDB menuDB);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateOllMenu(List<MenuDB> menuDBList);

    @Delete
    void deleteMenuItemDB(MenuDB menuDB);


}
