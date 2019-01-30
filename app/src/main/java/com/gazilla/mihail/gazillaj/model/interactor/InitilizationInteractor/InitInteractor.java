package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.util.Log;

import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.utils.POJO.Balances;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.BalanceCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class InitInteractor {

    InnitView innitView;

    public boolean checkVersionMemu(String latestVersionDB, InnitView innitView, Context context){
        this.innitView = innitView;
        MenuInteractor menuInteractor = new MenuInteractor(context);
        menuInteractor.checVersion(latestVersionDB);

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
            public void showError(int error) {
                Log.i("Loog", "myBalances err -" + error);
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "myBalances t -"+throwable.getMessage());
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

            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {

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
        innitView.startMainActivity();
    }

}
