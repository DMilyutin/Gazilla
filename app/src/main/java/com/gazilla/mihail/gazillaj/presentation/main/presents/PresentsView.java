package com.gazilla.mihail.gazillaj.presentation.main.presents;

import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;

import java.util.List;

public interface PresentsView {

    void setAdapterPresents(List<MenuCategory> categories, List<ImgGazilla> imgGazillaList);
    void setAdapterGifts(List<MenuItem> gifts);
}
