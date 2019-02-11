package com.gazilla.mihail.gazillaj.presentation.account;

public interface AccountView {

    void setUserInfo(String name, String phone, String email);
    void showWorningDialog(String err);
    void showLoadingDialog();
    void clouseAppDialog();
}
