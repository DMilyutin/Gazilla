package com.gazilla.mihail.gazillaj.model.repository;

import com.gazilla.mihail.gazillaj.model.data.db.DAO.MenuDBDao;

import java.util.concurrent.Callable;

public class CallableClearMenuTable  implements Callable<Boolean> {

    private MenuDBDao menuDBDao;

    public CallableClearMenuTable(MenuDBDao menuDBDao) {
        this.menuDBDao = menuDBDao;
    }

    @Override
    public Boolean call() throws Exception {
        menuDBDao.deleteOllTable();
        return true;
    }
}
