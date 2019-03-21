package com.gazilla.mihail.gazillaj.presentation.main.presentation;

import com.arellomobile.mvp.MvpView;

public interface MainView extends MvpView {

    void updateInfo(int score);
    void showErrorDialog(String error);
    void startReserveActivity();
    void sendAnswerNotification(int alertId, int commandId);
    void openMenuPresent();
    void openMenuStocks();
}
