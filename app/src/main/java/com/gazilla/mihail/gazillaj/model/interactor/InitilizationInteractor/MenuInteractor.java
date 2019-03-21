package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuDB;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;

import java.util.List;

import io.realm.Realm;


public class MenuInteractor {

    private PresentsInteractor presentsInteractor;
    private Context context;

    public MenuInteractor(Context context) {
        this.context = context;
        this.presentsInteractor = new PresentsInteractor();
    }

    public void checVersion(String latestVersionDB){
        Log.i("Loog", "checVersion menu");

        InitializationAp initializationAp = InitializationAp.getInstance();

        String publickey = initializationAp.getUserWithKeys().getPublickey();
        String signature = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), "");

        initializationAp.getRepositoryApi().lastVersionMenu(publickey, signature, new LVersionDBMenuCallBack() {
            @Override
            public void versionDBMenu(LatestVersion latestVersion) {
                Log.i("Loog", "latestVersion -"+ latestVersion.getDate());
                Log.i("Loog", "shared latestVersion -"+ latestVersionDB);

                if(!(latestVersionDB.equals(latestVersion.getDate()))){
                    clearMenuTable();
                    upDateMenu();
                    upDateLatestVersion(latestVersion);
                }
            }

            @Override
            public void showError(String error) {

                new BugReport().sendBugInfo(error, "MenuInteractor.checkVersion.showError");
                Log.i("Loog", "error menu - " + error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "MenuInteractor.checkVersion.setError.Throwable");
                Log.i("Loog", "throwable menu - " + throwable.getMessage());
            }
        });

    }

    private void upDateLatestVersion(LatestVersion latestVersion) {
        Log.i("Loog", "upDateLatestVersion");
        SharedPref sharedPref = new SharedPref(context);
        sharedPref.saveVersionMenuCategory(latestVersion.getDate());
    }

    private void clearMenuTable(){
        Log.i("Loog", "clearMenuTable");
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.clear(MenuDB.class);
        realm.commitTransaction();
        realm.close();
    }

    private void upDateMenu(){
        Log.i("Loog", "upDateMenu");
        presentsInteractor.menuServer(new MenuCallBack() {
            @Override
            public void ollMenu(List<MenuCategory> menuCategoryList) {
                saveMenuInRealm(new MenuAdapterApiDb().fromMenuCategory(menuCategoryList));
            }

            @Override
            public void showError(String error) {
                new BugReport().sendBugInfo(error, "MenuInteractor.upDateMenu.showError");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "MenuInteractor.upDateMenu.setError.Throwable");
            }
        });
    }

    private void saveMenuInRealm(List<MenuDB> menuDBList){
        Log.i("Loog", "saveMenuInRealm save start");
        Realm realm = Realm.getInstance(context);
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(menuDBList);
        realm.commitTransaction();
        realm.close();
    }


}
