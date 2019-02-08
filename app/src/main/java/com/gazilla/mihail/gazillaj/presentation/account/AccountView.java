package com.gazilla.mihail.gazillaj.presentation.account;

import com.gazilla.mihail.gazillaj.utils.POJO.Success;

public interface AccountView {

    void setUserInfo(String name, String phone, String email);
    void showWorningDialog(String err);
    void showLoadingDialog();
    void clouseAppDialog();
    void showErrorDialog(String err, String locatoin);
}
