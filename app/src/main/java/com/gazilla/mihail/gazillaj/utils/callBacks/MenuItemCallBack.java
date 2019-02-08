package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;

import java.util.List;

public interface MenuItemCallBack {
    void menuItem(List<MenuItem> menuItemList);
    void showError(String error);
}
