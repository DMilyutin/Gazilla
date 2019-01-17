package com.gazilla.mihail.gazillaj.presentation.main.presents;

import android.util.Log;

import com.gazilla.mihail.gazillaj.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.POJO.Success;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.ui.main.presents.Adapter.PresentsAdapter;
import com.gazilla.mihail.gazillaj.ui.main.presents.Presents;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.ImgCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuDBCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuItemCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

import java.util.ArrayList;
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
            public void showError(int error) {

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
            public void showError(int error) {
                Log.i("Loog", "Error - " + error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
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
