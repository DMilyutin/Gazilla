package com.gazilla.mihail.gazillaj.presentation.detail.present;


import android.graphics.Bitmap;

import com.arellomobile.mvp.MvpView;

public interface DetailPresentView extends MvpView {

    void openFirstDialog();

    void openSecondDialog();

    void acsessBuy();

    void setImgItem(Bitmap bitmap);

    void errorDialog(String error);
}
