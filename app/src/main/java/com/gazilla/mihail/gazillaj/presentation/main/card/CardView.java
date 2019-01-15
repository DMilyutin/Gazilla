package com.gazilla.mihail.gazillaj.presentation.main.card;

import android.graphics.Bitmap;

import java.util.Map;

public interface CardView {

    void setValueProgressBar(int maxValue, int userValue);
    void initListWithLvl(int myLvl, Map<Integer, Integer> mapLvl);
    void setQRcode(Bitmap bitmap);
    void showError(String error);
    void setSpins(int qty);
    void myWin(String type, String win, Bitmap bitmap);
    void initLvlForKoleso(int res);
}
