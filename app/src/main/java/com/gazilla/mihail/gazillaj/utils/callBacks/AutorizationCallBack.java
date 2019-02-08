package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;

public interface AutorizationCallBack {

    void AutorizCallBack(UserWithKeys userWithKeys);
    void showError(String error);
}
