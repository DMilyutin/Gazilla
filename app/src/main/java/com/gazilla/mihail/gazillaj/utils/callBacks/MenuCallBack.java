package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.MenuCategory;

import java.util.List;

public interface MenuCallBack {
    void ollMenu(List<MenuCategory> menuCategoryList);
    void showError(int error);
}
