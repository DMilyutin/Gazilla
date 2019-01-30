package com.gazilla.mihail.gazillaj.presentation.detail.present;


import android.graphics.Bitmap;

public interface DetailPresentView {

    void openFirstDialog();

    void openSecondDialog();

    void acsessBuy();

    void setImgItem(Bitmap bitmap);

    void errorDialog(String error);
}
