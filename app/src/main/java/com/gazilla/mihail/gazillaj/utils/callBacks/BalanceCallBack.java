package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.utils.POJO.Balances;

public interface BalanceCallBack {

    void myBalance(Balances balances);
    void showError(String error);
}
