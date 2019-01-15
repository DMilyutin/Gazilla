package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.Success;

public interface SuccessCallBack {

    void reservResponse(Success success);
    void errorResponse(String error);

}
