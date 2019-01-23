package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.User;

public interface UserCallBack {
    void userCallBack(User user);
    void errorUser(String error);
}
