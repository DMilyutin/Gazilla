package com.gazilla.mihail.gazillaj.presentation.main.stock.StoksDragonWay;

import android.util.Log;

import com.arellomobile.mvp.MvpPresenter;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.POJO.Levels;
import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.utils.callBacks.DragonWeyCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;

public class DragonWayPresentation extends MvpPresenter<DragonWayView> {


    private PromoInteractor promoInteractor;
    private DragonWayView dragonWayView;

    public DragonWayPresentation(DragonWayView dragonWayView) {
        this.promoInteractor = new PromoInteractor();
        this.dragonWayView = dragonWayView;
    }

    public void myLvlDragonWay(){
        promoInteractor.dragonStock(new DragonWeyCallBack() {
            @Override
            public void myLvl(Levels levels) {
                dragonWayView.myLevel(levels.getLevel());
            }

            @Override
            public void errorTxt(String s) {
                new BugReport().sendBugInfo(s, "DragonWayPresentation.myLvlDragonWay.errorTxt");
                Log.i("Loog", "myLvlDragonWay error - " + s);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "DragonWayPresentation.myLvlDragonWay.setError.Throwable");
                Log.i("Loog", "myLvlDragonWay errorT - " + throwable.getMessage());
            }
        });
    }

}
