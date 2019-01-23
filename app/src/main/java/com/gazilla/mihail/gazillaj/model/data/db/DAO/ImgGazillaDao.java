package com.gazilla.mihail.gazillaj.model.data.db.DAO;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;

import java.util.List;

@Dao
public interface ImgGazillaDao {


    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void newImg(List<ImgGazilla> imgGazillaList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void newOneImg(ImgGazilla imgGazilla);

    @Query("SELECT * FROM ImgGazilla")
    List<ImgGazilla> getOllImgFromBD();

    @Query("SELECT * FROM ImgGazilla WHERE id = :id ")
    ImgGazilla getImgFromDbById(int id);

    @Query("DELETE FROM ImgGazilla")
    void deleteOllImgTable();
}
