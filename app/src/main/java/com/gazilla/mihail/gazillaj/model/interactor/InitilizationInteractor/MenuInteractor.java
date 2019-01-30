package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;

import java.util.List;


public class MenuInteractor {

    private PresentsInteractor presentsInteractor;
    private Context context;

    public MenuInteractor(Context context) {
        this.context = context;
        this.presentsInteractor = new PresentsInteractor();
    }

    public void checVersion(String latestVersionDB){
        Log.i("Loog", "checVersion menu");

        String publickey = Initialization.userWithKeys.getPublickey();
        String signature = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), "");

        Initialization.repositoryApi.lastVersionMenu(publickey, signature, new LVersionDBMenuCallBack() {
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
            public void showError(int error) {
                Log.i("Loog", "error menu - " + error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
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
        Initialization.repositoryDB.clearMenuTable();
    }

    private void upDateMenu(){
        Log.i("Loog", "upDateMenu");
        presentsInteractor.menuServer(new MenuCallBack() {
            @Override
            public void ollMenu(List<MenuCategory> menuCategoryList) {
                Initialization.repositoryDB.newMenuFromServer(new MenuAdapterApiDb().fromMenuCategory(menuCategoryList));
                //new PhotoMemuInterator().startInitPhotoMenu(menuCategoryList);
            }

            @Override
            public void showError(int error) {

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });
    }


}
