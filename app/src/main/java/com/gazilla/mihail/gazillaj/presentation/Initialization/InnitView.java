package com.gazilla.mihail.gazillaj.presentation.Initialization;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;

public interface InnitView extends MvpView {

    void startMainActivity();
    void startRegistrationActivity();
    void showErrorer(String error);
}
