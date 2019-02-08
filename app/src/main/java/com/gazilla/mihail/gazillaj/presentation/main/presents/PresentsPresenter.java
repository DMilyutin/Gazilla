package com.gazilla.mihail.gazillaj.presentation.main.presents;

import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.ImgCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuDBCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuItemCallBack;

import java.util.List;

public class PresentsPresenter {

    private MenuAdapterApiDb adapterApiDb;

    private PresentAdapterView presentAdapterView;
    private PresentsView presentsView;
    private PresentsInteractor presentsInteractor;

    public PresentsPresenter(PresentsView presentsView, PresentsInteractor presentsInteractor, PresentAdapterView presentAdapterView) {
        adapterApiDb = new MenuAdapterApiDb();
        this.presentsView = presentsView;
        this.presentsInteractor = presentsInteractor;
        this.presentAdapterView = presentAdapterView;
    }

    public void initMenu(){
        presentsInteractor.menuDB(new MenuDBCallBack() {
            @Override
            public void ollMenu(List<MenuDB> menuDBList) {

                img(menuDBList);
            }

            @Override
            public void showError(String error) {
                new BugReport().sendBugInfo(error, "PresentsPresenter.initMenu.showError");
            }
        });

    }

    public void initGifts(){
        presentsInteractor.myGifts(new MenuItemCallBack() {
            @Override
            public void menuItem(List<MenuItem> menuItemList) {
                presentsView.setAdapterGifts(menuItemList);

            }

            @Override
            public void showError(String error) {
                new BugReport().sendBugInfo(error, "PresentsPresenter.initGifts.showError");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "PresentsPresenter.initGifts.setError.Throwable");
                Log.i("Loog", "Errort - " + throwable.getMessage());
            }
        });
    }

    private void img(List<MenuDB> menuDBList){
        presentsInteractor.myImgMenu(new ImgCallBack() {
            @Override
            public void ollImgFromDB(List<ImgGazilla> imgGazillaList) {
                presentsView.setAdapterPresents(adapterApiDb.fromMenuDB(menuDBList), imgGazillaList);
            }

            @Override
            public void imgById(ImgGazilla imgGazilla) {

            }
        });
    }



}
