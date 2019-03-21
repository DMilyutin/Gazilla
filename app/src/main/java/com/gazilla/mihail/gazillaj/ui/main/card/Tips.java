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
    private Boolean tipsIsWork = false;

    private boolean wheelTip = false;// true при нажатии на дракона
    private boolean balanceTip = false;
    private boolean nacopTip = false;
    private boolean draconTip = false;
    private boolean registrTip = false;
    private boolean reserveTip = false;

    public Tips(CardView cardView, Context context) {
        this.cardView = cardView;
        sharedPref = new SharedPref(context);
        //firstStartApp = sharedPref.getFirstStart();
        firstStartApp = false;
    }

    /**
     * показать подсказку с колесом - нажать на колесо
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
     */

    public void initTips(){
        Log.i("Loog", "Is first start - " + sharedPref.getFirstStart());
        //firstStartApp = sharedPref.getFirstStart();
        firstStartApp = false;
        /*if (firstStartApp){
            cardView.firstDialogTip();
            nextTips(1);}*/
        /*if (true)
            nextTips(1);*/
    }

    public void nextTips(int i){
        switch (i){
            case 1:{
                sharedPref.saveFirstStart(false);
                tipsIsWork = true;
                cardView.wheelTip(true);
                break;
            }
            case 2:{
                if (wheelTip){
                    stopWheelTip();
                    startBalanceTip();
                    break;
                }
            }
            case 3:{
                if (balanceTip){
                    stopBalanceTip();
                    startNacopTip();
                    break;
                }
            }
            case 4:{
                if (nacopTip){
                    stopNacopTip();
                    startLvlDragonTip();
                    break;
                }
            }
            case 5:{
                if (draconTip){
                    stopLvlDragonTip();
                    startRegistrTip();
                    break;
                }
            }
            case 6:{
                if (registrTip){
                    stopRegistrTip();
                    startReserveTip();
                    break;
                }
            }
            case 7:{
                if (reserveTip){
                    stopReserveTip();
                    tipsIsWork = false;
                    break;
                }
            }
        }
    }

    private void stopWheelTip(){
        cardView.wheelTip(false);
    }

    private void startBalanceTip(){
        cardView.balanceTip(true);
    }

    private void stopBalanceTip(){ cardView.balanceTip(false); }

    private void startNacopTip(){
        cardView.nacopTip(true);
    }

    private void stopNacopTip(){
        cardView.nacopTip(false);
    }

    private void startLvlDragonTip(){
        cardView.lvlDraconTip(true);
    }

    private void stopLvlDragonTip(){
        cardView.lvlDraconTip(false);
    }

    private void startRegistrTip(){
        cardView.registrTip(true);
    }

    private void stopRegistrTip(){
        cardView.registrTip(false);
    }

    private void startReserveTip(){
        cardView.reserveTip(true);
    }

    private void stopReserveTip(){
        cardView.reserveTip(false);
    }

    public boolean getTipsIsWork() { return tipsIsWork; }

    public boolean getFirstStartApp() { return firstStartApp; }

    public void setWheelTip(boolean wheelTip) {
        this.wheelTip = wheelTip;
    }

    public void setBalanceTip(boolean balanceTip) {
        this.balanceTip = balanceTip;
    }

    public void setNacopTip(boolean nacopTip) {
        this.nacopTip = nacopTip;
    }

    public void setDraconTip(boolean draconTip) {
        this.draconTip = draconTip;
    }

    public void setRegistrTip(boolean registrTip) {
        this.registrTip = registrTip;
    }

    public void setReserveTip(boolean reserveTip) {
        this.reserveTip = reserveTip;
    }

    public boolean isWheelTip() {
        return wheelTip;
    }

    public boolean isBalanceTip() {
        return balanceTip;
    }

    public boolean isNacopTip() {
        return nacopTip;
    }

    public boolean isDraconTip() {
        return draconTip;
    }

    public boolean isRegistrTip() {
        return registrTip;
    }
}
