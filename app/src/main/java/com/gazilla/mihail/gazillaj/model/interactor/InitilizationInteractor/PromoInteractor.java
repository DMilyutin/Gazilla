package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBPromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;

import java.util.List;

public class PromoInteractor {

    private SharedPref sharedPref;


    private void checkVersionPromo(String publickey, String signature){
        Log.i("Loog", "checkVersionPromo");
        Initialization.repositoryApi.lastVersionPromo(publickey, signature, new LVersionDBPromoCallBack() {
            @Override
            public void versionDBPromo(LatestVersion latestVersion) {
                if(!sharedPref.getVersionPromo().equals(latestVersion.getDate())){
                    claerPromoTable();
                    loadPromoFromServer(publickey, signature, latestVersion);
                }

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

    private void loadPromoFromServer(String publickey, String signature, LatestVersion latestVersion){
        Log.i("Loog", "loadPromoFromServer");
        Initialization.repositoryApi.ollPromo(publickey, signature, new PromoCallBack() {
            @Override
            public void myPromo(List<PromoItem> promoItemList) {
                Log.i("Loog", "promo server -" + promoItemList.size());
                loadPromoInDB(promoItemList, latestVersion);
            }

            @Override
            public void showError(String error) {

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

            }
        });
    }

    private void claerPromoTable(){
        Log.i("Loog", "claerPromoTable");
        Initialization.repositoryDB.clearPromoTable();
    }

    private void loadPromoInDB(List<PromoItem> promoItemList, LatestVersion latestVersion){
        Log.i("Loog", "loadPromoInDB");
        Initialization.repositoryDB.loadOllNewPromo(promoItemList);
        sharedPref.saveVersionPromo(latestVersion.getDate());
    }

}
