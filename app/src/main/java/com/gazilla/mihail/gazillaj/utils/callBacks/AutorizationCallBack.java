package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.UserWithKeys;

import retrofit2.Response;

public interface AutorizationCallBack {

    void AutorizCallBack(UserWithKeys userWithKeys);
    void shouError(String error);
}
