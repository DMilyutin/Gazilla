package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.POJO.Balances;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.Notificaton;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.BalanceCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.NotificationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class InitInteractor {

    private InnitView innitView;
    private Context context;
    private SharedPref sharedPref;

    public boolean checkVersionMenu(String latestVersionDB, InnitView innitView, Context context){
        this.innitView = innitView;
        MenuInteractor menuInteractor = new MenuInteractor(context);
        menuInteractor.checVersion(latestVersionDB);
        sharedPref = new SharedPref(context);

        /*bservable.just(menuInteractor.checVersion(latestVersionDB))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aBoolean -> {}, throwable -> {}, ()->{});*/



        return true;
    }



    //_________________________________Загрузка баланса___________________________________________

    public void myBalances(){

        String publickey = Initialization.userWithKeys.getPublickey();
        String signature = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), "");

        Log.i("Loog", "myBalances");
        Initialization.repositoryApi.myBalances(publickey, signature, new BalanceCallBack() {
            @Override
            public void myBalance(Balances balances) {
                Initialization.userWithKeys.setSum(balances.getSum());
                Initialization.userWithKeys.setScore(balances.getScore());
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
        Initialization.repositoryApi.userData(new UserCallBack() {
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
        Initialization.userWithKeys.setId(user.getId());
        Initialization.userWithKeys.setLevel(user.getLevel());
        Initialization.userWithKeys.setRefererLink(user.getRefererLink());
        Initialization.userWithKeys.setName(user.getName());
        Initialization.userWithKeys.setPhone(user.getPhone());
        Initialization.userWithKeys.setFavorites(user.getFavorites());
        notificationInfo();
    }

    private void notificationInfo(){
        Log.i("Loog", "notificationInfo");
        String publickey = Initialization.userWithKeys.getPublickey();
        String signature = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), "");

        Initialization.repositoryApi.getLastVersionNotification(publickey, signature, new LVersionDBMenuCallBack() {
            @Override
            public void versionDBMenu(LatestVersion latestVersion) {
                Log.i("Loog", "notificationInfo - goog");
                if (latestVersion.getDate().equals(sharedPref.getVersionNotification()))
                    innitView.startMainActivity();
                else{
                    updateNotification();
                    sharedPref.saveVersionNotification(latestVersion.getDate());
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

    private void updateNotification(){
        Log.i("Loog", "updateNotification");
        String publickey = Initialization.userWithKeys.getPublickey();
        String signature = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), "");

        Initialization.repositoryApi.getOllNotification(publickey, signature, new NotificationCallBack() {
            @Override
            public void ollNotification(List<Notificaton> notificationList) {
                Log.i("Loog", "updateNotification.good");
                if (notificationList!=null){
                    Log.i("Loog", "updateNotification.good");
                    Initialization.notificaton = notificationList.get(notificationList.size()-1);
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
