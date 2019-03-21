package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.Balances;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.Notificaton;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.callBacks.BalanceCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.NotificationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import java.util.List;

import io.reactivex.Observable;

public class InitInteractor {

    private InnitView innitView;

    private SharedPreferences sharedPref;
    private InitializationAp initializationAp;

    public InitInteractor(SharedPreferences sharedPref, InnitView innitView) {
        this.sharedPref = sharedPref;
        this.innitView = innitView;
        initializationAp = InitializationAp.getInstance();

    }

    /*public boolean checkVersionMenu(String latestVersionDB, InnitView innitView, Context context){
        this.innitView = innitView;
        MenuInteractor menuInteractor = new MenuInteractor(context);
        menuInteractor.checVersion(latestVersionDB);
        sharedPref = new SharedPref(context);

        Observable.just(menuInteractor.checVersion(latestVersionDB))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {}, throwable -> {}, ()->{});*/



        //return true;



    //_________________________________Загрузка баланса___________________________________________

    public void myBalances(){


        String publickey = initializationAp.getUserWithKeys().getPublickey();
        String signature = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), "");

        Log.i("Loog", "myBalances");
        initializationAp.getRepositoryApi().myBalances(publickey, signature, new BalanceCallBack() {
            @Override
            public void myBalance(Balances balances) {
                initializationAp.getUserWithKeys().setSum(balances.getSum());
                initializationAp.getUserWithKeys().setScore(balances.getScore());
                userData();

            }

            @Override
            public void showError(String error) {
                new BugReport().sendBugInfo(error, "InitInteractor.myBalances.showError");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "InitInteractor.myBalances.setError.Throwable");
            }
        });
    }


    private void userData(){
        initializationAp.getRepositoryApi().userData(initializationAp.getUserWithKeys().getPublickey(),
                initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(),""),
                new UserCallBack() {
            @Override
            public void userCallBack(User user) {
                setUserwithUserKey(user);

            }

            @Override
            public void errorUser(String error) {
                new BugReport().sendBugInfo(error, "InitInteractor.userData.errorUser");
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                new BugReport().sendBugInfo(throwable.getMessage(), "InitInteractor.userData.setError.Throwable");
            }
        });
    }

    private void setUserwithUserKey(User user) {
        initializationAp.getUserWithKeys().setId(user.getId());
        initializationAp.getUserWithKeys().setLevel(user.getLevel());
        initializationAp.getUserWithKeys().setRefererLink(user.getRefererLink());
        initializationAp.getUserWithKeys().setName(user.getName());
        initializationAp.getUserWithKeys().setPhone(user.getPhone());
        initializationAp.getUserWithKeys().setFavorites(user.getFavorites());
        notificationInfo();
    }

    private void notificationInfo(){
        Log.i("Loog", "notificationInfo");
        String publickey = initializationAp.getUserWithKeys().getPublickey();
        String signature = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), "");

        initializationAp.getRepositoryApi().getLastVersionNotification(publickey, signature, new LVersionDBMenuCallBack() {
            @Override
            public void versionDBMenu(LatestVersion latestVersion) {
                Log.i("Loog", "notificationInfo - goog");
                if (latestVersion.getDate().equals(sharedPref.getString("versionNotification", "")))
                    innitView.startMainActivity();
                else{
                    updateNotification(latestVersion);
                    //sharedPref.saveVersionNotification(latestVersion.getDate());
                }
            }

            @Override
            public void showError(String error) {
                Log.i("Loog", "InitInteractor.notificationInfo.showNotificationError " + error);
                innitView.startMainActivity();
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "InitInteractor.notificationInfo.setError.Throwable "+ throwable.getMessage());
                //new BugReport().sendBugInfo(throwable.getMessage(), "InitInteractor.notificationInfo.setError.Throwable");
                innitView.startMainActivity();
            }
        });
    }

    private void updateNotification(LatestVersion latestVersion1){
        Log.i("Loog", "updateNotification");
        String publickey = initializationAp.getUserWithKeys().getPublickey();
        String signature = initializationAp.signatur(initializationAp.getUserWithKeys().getPrivatekey(), "");

        initializationAp.getRepositoryApi().getOllNotification(publickey, signature, new NotificationCallBack() {
            @Override
            public void ollNotification(List<Notificaton> notificationList) {
                Log.i("Loog", "updateNotification.good");
                if (notificationList!=null){
                    Log.i("Loog", "updateNotification.good");
                    if (notificationList.size()!=0)
                    initializationAp.setNotificaton(notificationList.get(notificationList.size()-1));
                    initializationAp.setLatestVersion(latestVersion1);

                }
                innitView.startMainActivity();
            }

            @Override
            public void showNotificationError(String error) {
                Log.i("Loog", "InitInteractor.updateNotification.showNotificationError " + error);
                innitView.startMainActivity();
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
               // new BugReport().sendBugInfo(throwable.getMessage(), "InitInteractor.updateNotification.setError.Throwable");
                Log.i("Loog", "InitInteractor.updateNotification.setError.Throwable " + throwable.getMessage());
                innitView.startMainActivity();
            }
        });
    }

}
