package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.MenuDB;

import java.util.List;

public interface MenuDBCallBack {

    void ollMenu(List<MenuDB> menuDBList);
    void showError(int error);
}
