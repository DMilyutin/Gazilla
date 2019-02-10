package com.gazilla.mihail.gazillaj.presentation.reserve;

import com.gazilla.mihail.gazillaj.utils.POJO.Reserve;

public interface ReserveView {

    void inputUserInfo(String name, String phone);
    void putReserve(Reserve reserve, Boolean preorder);
    void resultReserve(String result);
    void showWorningDialog(String err);
    void showLoadingDialog();
    void clouseAppDialog();
}
