package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.POJO.Balances;
import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.BalanceCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBPromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class InitInteractor {

    private InnitView innitView;
    private SharedPref sharedPref;

    private Context context;

    public InitInteractor(Context context, InnitView innitView) {
        this.context=context;
        this.innitView = innitView;
        this.sharedPref = new SharedPref(context);

    }

    public void startInit(){
        Log.i("Loog", "___________________");

        checkUserDate();
    }



    //________________________________ Проверка интернета_______________________________________


    private static boolean hasConnection(final Context context) {
        Log.i("Loog", "hasConnection");
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifiInfo != null && wifiInfo.isConnected())
        {
            return true;
        }
        wifiInfo = cm.getActiveNetworkInfo();
        if (wifiInfo != null && wifiInfo.isConnected())
        {

            return true;
        }
        Log.i("Loog", "hasConnection инета нет");
        return false;
    }
    //______________________________Проверка данных в телефоне _____________________________________


    private void checkUserDate(){



        Log.i("Loog", "checkUserDate");

        Boolean hasConnection = hasConnection(context);
        Boolean sharedPrefData = sharedPref.myPreff();

        String code = null;

        if(hasConnection && sharedPrefData) code = "11";
        if(!hasConnection && !sharedPrefData) code = "00";
        if(!hasConnection && sharedPrefData) code = "01";
        if(hasConnection && !sharedPrefData) code = "10";

        switch (code){
            case "11" : {
                Log.i("Loog", "checkUserDate - есть интернет и прошлые данные");
                // проверка версии бд и запуск
                if(Initialization.userWithKeys== null)  // Если нет User, создаем
                    Initialization.setUserWithKeys(new UserWithKeys(
                            sharedPref.getIdFromPref(),
                            sharedPref.getPublickKeyFromPref(),
                            sharedPref.getPrivateKeyFromPref()));

               initing();
                break;
            }
            case "00" : {
                Log.i("Loog", "checkUserDate - нет интернет и нет прошлых данных");
                // ошибка
                break;
            }
            case "01" : {
                Log.i("Loog", "checkUserDate - нет интернет и есть прошлые данные");
                // загрузка данных с бд
                if(Initialization.userWithKeys== null)  // Если нет User, создаем
                    Initialization.setUserWithKeys(new UserWithKeys(
                            sharedPref.getIdFromPref(),
                            sharedPref.getPublickKeyFromPref(),
                            sharedPref.getPrivateKeyFromPref()));
                Initialization.userWithKeys.setName(sharedPref.getNameFromPref());
                Initialization.userWithKeys.setPhone(sharedPref.getPhoneFromPref());
                Initialization.userWithKeys.setEmail(sharedPref.getEmailFromPref());
                innitView.startMainActivity();
                break;
            }
            case "10" : {
                Log.i("Loog", "checkUserDate - есть интернет и нет прошлых данных");
                innitView.startRegistrationActivity();
                //registration("", "","","");
                // регистрация, проверка версии бд и запуск
                break;
            }
            default: {
                // Error
            }
        }

    }

    private void initing(){

        MenuInteractor menuInteractor = new MenuInteractor(new SharedPref(context));

        Observable.just(menuInteractor.checVersion())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        myBalances();
                    }
                });

        //menuInteractor.checVersion();
        //myBalances();
    }

    //_________________________________Загрузка баланса___________________________________________

    private void myBalances(){

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

    private void setUserwithUserKey(User user){
        Initialization.userWithKeys.setId(user.getId());
        Initialization.userWithKeys.setLevel(user.getLevel());
        Initialization.userWithKeys.setRefererLink(user.getRefererLink());
        Initialization.userWithKeys.setName(user.getName());
        Initialization.userWithKeys.setPhone(user.getPhone());
        Initialization.userWithKeys.setFavorites(user.getFavorites());

        innitView.startMainActivity();
    }


}
