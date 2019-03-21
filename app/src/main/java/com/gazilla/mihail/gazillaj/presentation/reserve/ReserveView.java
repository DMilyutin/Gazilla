package com.gazilla.mihail.gazillaj.presentation.reserve;

import com.arellomobile.mvp.MvpView;
import com.gazilla.mihail.gazillaj.utils.POJO.Reserve;

public interface ReserveView extends MvpView {

    void inputUserInfo(String name, String phone);
    void putReserve(Reserve reserve, Boolean preorder);
    void resultReserve(String result);
    void showWorningDialog(String err);
    void showLoadingDialog();
    void clouseAppDialog();
}
