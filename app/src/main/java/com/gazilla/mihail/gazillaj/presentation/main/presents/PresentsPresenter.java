package com.gazilla.mihail.gazillaj.presentation.main.presents;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuItemCallBack;

import java.util.List;


public class PresentsPresenter  {

    private MenuAdapterApiDb adapterApiDb;

    private PresentAdapterView presentAdapterView;
    private PresentsView presentsView;
    private PresentsInteractor presentsInteractor;
    private InitializationAp initializationAp = InitializationAp.getInstance();

    //private Realm realm;

    public PresentsPresenter(PresentsView presentsView, PresentsInteractor presentsInteractor, PresentAdapterView presentAdapterView) {
       // realm = Realm.getInstance(context);
        adapterApiDb = new MenuAdapterApiDb();
        this.presentsView = presentsView;
        this.presentsInteractor = presentsInteractor;
        this.presentAdapterView = presentAdapterView;
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


    public void menuFromServer(){
        presentsView.setVisibleProgressBar();
        String publickKey = initializationAp.getUserWithKeys().getPublickey();
        String signature = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), "");
        initializationAp.getRepositoryApi().ollMenu(publickKey, signature, new MenuCallBack() {
            @Override
            public void ollMenu(List<MenuCategory> menuCategoryList) {
                presentsView.setUnvisibleProgressBar();
                presentsView.setAdapterPresents(menuCategoryList);

            }

            @Override
            public void showError(String error) {
                presentsView.setUnvisibleProgressBar();
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                presentsView.setUnvisibleProgressBar();
            }
        });
    }


    /*private void getMenuFromRealm(){
        Log.i("Loog", "getFromRealm get");
        realm.beginTransaction();
        RealmResults<MenuDB> menuCategoryRealmResults = realm.where(MenuDB.class).findAll();
        if (!menuCategoryRealmResults.isEmpty()){
            Log.i("Loog", "getFromRealm 0 - " + menuCategoryRealmResults.get(0).getNameCategory());
            presentsView.setAdapterPresents(adapterApiDb.fromMenuDB(menuCategoryRealmResults));
        }
        else
            menuFromServer();
        realm.commitTransaction();
    }

    public void closeRealm(){
        realm.close();
    }*/

}
