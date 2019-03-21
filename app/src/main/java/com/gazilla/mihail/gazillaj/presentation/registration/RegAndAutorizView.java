package com.gazilla.mihail.gazillaj.presentation.registration;

import com.arellomobile.mvp.MvpView;

public interface RegAndAutorizView extends MvpView {



    void visibleETCode();
    void startProgramm(Boolean response);

    void showLoadingDialog();
    void showWarningDialog(String err);
    void showErrorDialog(String err, String location);
    void clouaeAppDialog();

}
