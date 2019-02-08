package com.gazilla.mihail.gazillaj.presentation.Initialization;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor.InitInteractor;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.utils.QRcode;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;


import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class InitPresentation {

    private InitInteractor initInteractor;
    private Context context;
    private SharedPref sharedPref;
    private InnitView innitView;
    private String ver;


    public InitPresentation(InnitView innitView, Context context) {
        this.innitView = innitView;
        this.context = context;
        initInteractor = new InitInteractor();
        sharedPref = new SharedPref(context);
        ver = sharedPref.getVersionMenuCategory();
    }

    public void checkUserDate(){
        /*Observable.just(checkUserDat())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                });*/


        checkUserDat();

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



    public boolean checkUserDat(){
        Log.i("Loog", "checkUserDate");



        //innitView.startProgressBar();
        Boolean hasConnection = hasConnection(context);
        Boolean sharedPrefData = sharedPref.myPreff();

        String code = "11";
        if(hasConnection && sharedPrefData) code = "11";
        if(!hasConnection && sharedPrefData) code = "01";
        if(hasConnection && !sharedPrefData) code = "10";

        switch (code){
            case "11" : {
                Log.i("Loog", "checkUserDate - есть интернет и прошлые данные");
                getUserOnline();
                Observable.just(initInteractor.checkVersionMenu(ver, innitView, context))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {}, throwable -> {});
                                //innitView.startMainActivity()

                //initInteractor.checkVersionMemu(ver);
                ;
                break;
            }
            case "00" : {
                Log.i("Loog", "checkUserDate - нет интернет и нет прошлых данных");
                new AppDialogs().warningDialog(context, "Для первого запуска необходим доступ в интернет");
                break;
            }
            case "01" : {
                Log.i("Loog", "checkUserDate - нет интернет и есть прошлые данные");
                getUserOffline();
                innitView.startMainActivity();
                // запуск активити
                break;
            }
            case "10" : {
                Log.i("Loog", "checkUserDate - есть интернет и нет прошлых данных");
                innitView.startRegistrationActivity();
                // регистрация
                break;
            }

        }
        //innitView.stopProgressBar();
        return true;
    }


    private void getUserOnline() {
        if(Initialization.userWithKeys== null)  // Если нет User, создаем
            Initialization.setUserWithKeys(new UserWithKeys(
                    sharedPref.getIdFromPref(),
                    sharedPref.getPublickKeyFromPref(),
                    sharedPref.getPrivateKeyFromPref()));
        initInteractor.myBalances();



    }

    private void getUserOffline() {
        if(Initialization.userWithKeys== null)  // Если нет User, создаем
            Initialization.setUserWithKeys(new UserWithKeys(
                    sharedPref.getIdFromPref(),
                    sharedPref.getPublickKeyFromPref(),
                    sharedPref.getPrivateKeyFromPref()));
    }
}


