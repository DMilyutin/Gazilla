package com.gazilla.mihail.gazillaj.presentation.registration;

public interface RegAndAutorizView {



    void visibleETCode();
    void startProgramm(Boolean response);

    void showLoadingDialog();
    void showWarningDialog(String err);
    void showErrorDialog(String err, String location);
    void clouaeAppDialog();
}
