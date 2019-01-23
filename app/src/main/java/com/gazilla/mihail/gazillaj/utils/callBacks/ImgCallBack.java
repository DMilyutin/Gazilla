package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;

import java.util.List;

public interface ImgCallBack {

    void ollImgFromDB(List<ImgGazilla> imgGazillaList);
    void imgById(ImgGazilla imgGazilla);
}
