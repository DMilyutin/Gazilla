package com.gazilla.mihail.gazillaj.presentation.Initialization;

import android.content.SharedPreferences;
import android.util.Log;


import com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor.InitInteractor;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;


public class InitPresentation {


    private InitInteractor initInteractor;
    private SharedPreferences sharedPref;
    //private String ver;
    private Boolean isConnect;
    private InnitView innitView;
    private InitializationAp initializationAp;


    public InitPresentation(SharedPreferences sharedPref, Boolean isCoonect, InnitView innitView) {
        initInteractor = new InitInteractor(sharedPref, innitView);
        this.sharedPref = sharedPref;
        this.isConnect = isCoonect;
        this.innitView = innitView;
        initializationAp = InitializationAp.getInstance();
        //ver = sharedPref.getString("versionMenuCategory", "");
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


    public boolean checkUserDat(){
        Log.i("Loog", "checkUserDate");



        //innitView.startProgressBar();
        Boolean sharedPrefData = sharedPref.contains("myID");

        String code = "00";
        if(isConnect && sharedPrefData) code = "11";
        if(!isConnect && sharedPrefData) code = "01";
        if(isConnect && !sharedPrefData) code = "10";

        switch (code){
            case "11" : {
                Log.i("Loog", "checkUserDate - есть интернет и прошлые данные");
                getUserOnline();
                /*Observable.just(initInteractor.checkVersionMenu(ver, innitView, context))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(aBoolean -> {}, throwable -> {});*/
                                //innitView.startMainActivity()

                //initInteractor.checkVersionMemu(ver);
                ;

                innitView.startMainActivity();
                break;
            }
            case "00" : {
                Log.i("Loog", "checkUserDate - нет интернет и нет прошлых данных");
                innitView.showErrorer("Для первого запуска необходим доступ в интернет");
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
            initializationAp.setUserWithKeys(new UserWithKeys(
                    sharedPref.getInt("myID", -1),
                    sharedPref.getString("publicKey", ""),
                    sharedPref.getString("privateKey", "")));
//        initInteractor.myBalances();

        Log.i("Loog", "getUserOnline - " + initializationAp.getUserWithKeys().getId());
        if (initializationAp.getRepositoryApi()==null)
            Log.i("Loog", "getRepositoryApi - null"  );
        else
            Log.i("Loog", "getRepositoryApi - not null"  );


    }

    private void getUserOffline() {
            InitializationAp.getInstance().setUserWithKeys(new UserWithKeys(
                    sharedPref.getInt("myID", -1),
                    sharedPref.getString("publicKey", ""),
                    sharedPref.getString("privateKey", "")
            ));
    }
}


