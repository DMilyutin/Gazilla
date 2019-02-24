package com.gazilla.mihail.gazillaj.presentation.main.card;

import android.graphics.Bitmap;

import java.util.Map;

public interface CardView {

    void setValueProgressBar(int maxValue, int userValue);
    void initListWithLvl(int myLvl, Map<Integer, Integer> mapLvl);
    void setQRcode(Bitmap bitmap);
    void setSpins(int qty);
    void myWin(String win, String res);
    void initLvlForKoleso(int res);
    void startWheeling();
    void firstDialogTip();
    void wheelTip(Boolean show);
    void balanceTip(Boolean show);
    void nacopTip(Boolean show);
    void lvlDraconTip(Boolean show);
    void registrTip(Boolean show);
    void reserveTip(Boolean show);
    void nextTip(int N);
}
