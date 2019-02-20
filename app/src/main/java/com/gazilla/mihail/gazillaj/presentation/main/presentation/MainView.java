package com.gazilla.mihail.gazillaj.presentation.main.presentation;

public interface MainView {

    void updateInfo(int score);
    void showErrorDialog(String error);
    void startReserveActivity();
    void sendAnswerNotification(int alertId, int commandId);
    void openMenuPresent();
    void openMenuStocks();
}
