package com.gazilla.mihail.gazillaj.presentation.reserve;

import com.gazilla.mihail.gazillaj.utils.POJO.Reserve;

public interface ReserveView {
    void checkUserInfo();
    void inputUserInfo(String name, String phone);
    void putReserve(Reserve reserve, Boolean preorder);
    void resultReserve(String result);
    void showErrorr(String error);
    void unRegUser();
}
