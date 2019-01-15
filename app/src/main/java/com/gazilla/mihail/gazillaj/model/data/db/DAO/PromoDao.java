package com.gazilla.mihail.gazillaj.model.data.db.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.gazilla.mihail.gazillaj.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.POJO.PromoItem;

import java.util.List;

@Dao
public interface PromoDao {

    @Query("SELECT * FROM PromoItem")
    List<PromoItem> getPromoDB();

    @Query("SELECT * FROM PromoItem WHERE id = :id")
    PromoItem getPromoByIdDB(int id);

    @Insert
    void addNewPromoDB(PromoItem promoItem);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePromoDB(PromoItem promoItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void loadOllOllPromo(List<PromoItem> promoItemList);

    @Delete
    void deletePromoDB(PromoItem promoItem);

    @Query("DELETE FROM PromoItem")
    void deleteOllTablePromo();
}
