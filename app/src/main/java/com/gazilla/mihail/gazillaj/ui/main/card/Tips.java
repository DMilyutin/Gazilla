package com.gazilla.mihail.gazillaj.ui.main.card;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardView;

/**
 * Класс подсказок
 * */

public class Tips {

    private CardView cardView;
    private SharedPref sharedPref;

    private Boolean firstStartApp;

    private boolean wheelTip = false;
    private boolean balanceTip = false;
    private boolean nacopTip = false;
    private boolean draconTip = false;
    private boolean registrTip = false;
    private boolean reserveTip = false;



    public Tips(CardView cardView, Context context) {
        this.cardView = cardView;
        sharedPref = new SharedPref(context);
        //initTips();
    }

    /**
     *
     * показать подсказку с колесом - нажать на колесо
     *
     *
     * показать подсказку с баллами - далее
     *
     * показать подсказку с накоплением - нажать на поле
     *
     * показать подсказку на уровень - нажать на уровень
     *
     * показать подсказку регистрации - далее
     *
     * показать подсказку резерва - понятно
     *
     * сохранить как второе посещение
     *
     *

     * */

    public void initTips(){
        Log.i("Loog", "Is first start - " + sharedPref.getFirstStart());
        firstStartApp = sharedPref.getFirstStart();
        /*if (firstStartApp)
            startTips();*/
        if (false)
            startTips();
    }

    public void nextTips(){
        if (wheelTip){
            wheelTip = false;
            stopWheelTip();
            balanceTip = true;
            startBalanceTip();
        }
        else if (balanceTip){
            balanceTip=false;
            stopBalanceTip();
            nacopTip = true;
            startNacopTip();
        }
        else if (nacopTip){
            nacopTip=false;
            stopNacopTip();
            draconTip= true;
            startLvlDragonTip();
        }
        else if (draconTip){
            draconTip=false;
            stopLvlDragonTip();
            registrTip=true;
            startRegistrTip();
        }
        else if (registrTip){
            registrTip=false;
            stopRegistrTip();
            reserveTip=true;
            startReserveTip();
        }
        else if (reserveTip){
            reserveTip=false;
            stopReserveTip();
            stopTips();
        }

    }

    public void stopTips(){
        sharedPref.saveFirstStart(false);
        cardView.wheelTip(false);
        wheelTip = false;
        balanceTip = false;
        draconTip = false;
        nacopTip = false;
        registrTip = false;
        reserveTip = false;
    }

    private void startTips(){
        wheelTip = true;
        startWheelTip();

    }

    private void startWheelTip(){
        if (wheelTip)
            cardView.wheelTip(true);
    }

    private void stopWheelTip(){
        cardView.wheelTip(false);
    }

    private void startBalanceTip(){
        if (balanceTip)
            cardView.balanceTip(true);
    }
    private void stopBalanceTip(){

            cardView.balanceTip(false);
    }


    private void startNacopTip(){
        if (nacopTip){
            cardView.nacopTip(true);

        }
    }

    private void stopNacopTip(){
        cardView.nacopTip(false);
    }

    private void startLvlDragonTip(){
        if (draconTip){
            cardView.lvlDraconTip(true);
        }

    }
    private void stopLvlDragonTip(){
        cardView.lvlDraconTip(false);
    }

    private void startRegistrTip(){
        if (registrTip)
            cardView.registrTip(true);
    }

    private void stopRegistrTip(){
        cardView.registrTip(false);
    }

    private void startReserveTip(){
        if (reserveTip)
            cardView.reserveTip(true);
    }

    private void stopReserveTip(){

            cardView.reserveTip(false);
    }

    public Boolean getFirstStartApp() {
        return firstStartApp;
    }
}
