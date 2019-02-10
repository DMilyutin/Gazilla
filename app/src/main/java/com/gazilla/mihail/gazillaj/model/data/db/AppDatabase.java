package com.gazilla.mihail.gazillaj.model.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.model.data.db.DAO.ImgGazillaDao;
import com.gazilla.mihail.gazillaj.model.data.db.DAO.MenuDBDao;
import com.gazilla.mihail.gazillaj.model.data.db.DAO.PromoDao;
/** абстрактный класс для работы с БД (Room) */
@Database(entities = {MenuDB.class, ImgGazilla.class, PromoItem.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract MenuDBDao menuDBDao();
    public abstract PromoDao promoDao();
    public abstract ImgGazillaDao imgDao();
}
