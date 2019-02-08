package com.gazilla.mihail.gazillaj.utils.callBacks;

import java.io.IOException;

import okhttp3.ResponseBody;

public interface StaticCallBack {
    void myStatic(ResponseBody responseBody) throws IOException;
    void showError(String error);
}
