package com.gazilla.mihail.gazillaj.utils.callBacks;

import com.gazilla.mihail.gazillaj.POJO.Balances;

public interface BalanceCallBack {

    void myBalance(Balances balances);
    void showError(int error);
}
