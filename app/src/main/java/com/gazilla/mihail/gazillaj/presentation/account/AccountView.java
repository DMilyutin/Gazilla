package com.gazilla.mihail.gazillaj.presentation.account;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface AccountView  extends MvpView {

    void setUserInfo(String name, String phone, String email, String id);
    void showWorningDialog(String err);
    void showLoadingDialog();
    void clouseAppDialog();
}
