package com.gazilla.mihail.gazillaj.model.interactor.InitilizationInteractor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.gazilla.mihail.gazillaj.POJO.Balances;
import com.gazilla.mihail.gazillaj.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.POJO.User;
import com.gazilla.mihail.gazillaj.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.model.repository.MenuAdapter.MenuAdapterApiDb;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.BalanceCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBPromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class InitInteractor {

    private PresentsInteractor presentsInteractor;
    private MenuAdapterApiDb adapterApiDb;

    private InnitView innitView;
    private SharedPref sharedPref;

    private Context context;

    //MenuInteractor menuInteractor;

    public InitInteractor(Context context, InnitView innitView) {
        this.context=context;
        adapterApiDb = new MenuAdapterApiDb();
        presentsInteractor = new PresentsInteractor();
        this.innitView = innitView;
        this.sharedPref = new SharedPref(context);
        //menuInteractor = new MenuInteractor();
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

                checkVersionMenuServerDB();
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


    //_____________________________-Сохранение ключей при новой регистрации или авторизации________

    private void saveKey(){
        Log.i("Loog", "saveKey");
        sharedPref.saveNewPrivateKey(Initialization.userWithKeys.getPrivatekey());
        sharedPref.saveNewPublicKey(Initialization.userWithKeys.getPublickey());
        sharedPref.saveNewId(Initialization.userWithKeys.getId());
        sharedPref.saveName(Initialization.userWithKeys.getName());
        sharedPref.saveMyPhone(Initialization.userWithKeys.getPhone());
        sharedPref.saveMyEmail(Initialization.userWithKeys.getEmail());
    }


    private void checkVersionMenuServerDB(){
        Log.i("Loog", "checkVersionMenuServerDB");
        checkVersionMenu(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""));

    }

    private void checkVersionServerPromoDB(){
        Log.i("Loog", "checkVersionServerPromoDB");
        checkVersionPromo(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""));
    }

    // ___________________________________- Авторизация при новом устройстве_________________________





    //______________________________________Регистрация нового пользователя__________________________




    // _______________________________________-Проверка версии БД Меню на сервере __________________________

    private void checkVersionMenu(String publickey, String signature){
        Log.i("Loog", "checkVersionMenu");
        Initialization.repositoryApi.lastVersionMenu(publickey, signature, new LVersionDBMenuCallBack() {
            @Override
            public boolean versionDBMenu(LatestVersion latestVersion) {
                if(!(sharedPref.getVersionMenuCategory().equals(latestVersion.getDate()))){
                    clearMenuTable();
                    loadMenuFromServer(latestVersion);
                }
                else checkVersionServerPromoDB();
                return false;
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

    //_________________Загрузка меню с сервера в базу данных_______________________________

    private List<MenuCategory> testLoadMenu(){
        List<MenuCategory> categories = new ArrayList<>();
        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(0, "Test", 330, "Testing", "200"));

        MenuCategory category = new MenuCategory("rfgrg", items);
        categories.add(category);
        return categories;
    }



    private void loadMenuFromServer(LatestVersion latestVersion){
        Log.i("Loog", "loadMenuFromServer");
        presentsInteractor.menuServer(new MenuCallBack() {
            @Override
            public void ollMenu(List<MenuCategory> menuCategoryList) {
                //addImgMenu(menuCategoryList, latestVersion);
                loadMenuInDB(menuCategoryList, latestVersion);
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

    private void clearMenuTable(){
        Log.i("Loog", "clearMenuTable");
        Initialization.repositoryDB.clearMenuTable();
    }



    private void loadMenuInDB(List<MenuCategory> menuCategoryList, LatestVersion latestVersion){
        Log.i("Loog", "loadMenuInDB");

        Initialization.repositoryDB.newMenuFromServer(adapterApiDb.fromMenuCategory(menuCategoryList));
        sharedPref.saveVersionMenuCategory(latestVersion.getDate());
        checkVersionServerPromoDB();

        //prepealoadImgFromServer(Initialization.userWithKeys.getPublickey(),
        //        Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""));

    }



    //__________________________-Проверка версии БД Промо на сервере ________________________________


    private void checkVersionPromo(String publickey, String signature){
        Log.i("Loog", "checkVersionPromo");
        Initialization.repositoryApi.lastVersionPromo(publickey, signature, new LVersionDBPromoCallBack() {
            @Override
            public void versionDBPromo(LatestVersion latestVersion) {
                if(!sharedPref.getVersionPromo().equals(latestVersion.getDate())){
                    claerPromoTable();
                    loadPromoFromServer(publickey, signature, latestVersion);
                }
                else myBalances(Initialization.userWithKeys.getPublickey(),
                        Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""));

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

    //______________________________Загрузка промо с сервера в базу данных__________________________

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
        myBalances(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""));
    }

    // _________________________________Загрузка картинок_______________________________

    /*private void prepealoadImgFromServer(String publickey, String signature){
        Log.i("Loog", "clearImgTable");
        //Initialization.repositoryDB.clearTableImg();

        Log.i("Loog", "loadMenuItem");
        Initialization.repositoryApi.ollMemuItem(publickey, signature, new MenuItemCallBack() {
            @Override
            public void menuItem(List<MenuItem> menuItemList) {
                if(menuItemList!=null){
                    loadImgFromServer(menuItemList);
                }



            }

            @Override
            public void showError(int error) {
                Log.i("Loog", "menuItemList showError - " + error );
            }
        }, new FailCallBack() {
            @Override
            public void setError(Throwable throwable) {
                Log.i("Loog", "menuItemList showErrorТ - " + throwable.getMessage() );
            }
        });
    }

    private void loadImgFromServer(List<MenuItem> menuItemList){
        Log.i("Loog", "loadImgFromServer");
        List<ImgGazilla> imgGazillaList = new ArrayList<>();
        //Log.i("Loog", "loadImgFromServer         " + menuItemList.size());
        for(int i = 0; i< menuItemList.size(); i++){
        //for(int i = 0; i< 10; i++){
            int finalI = i;
            Log.i("Loog", "finalI " + finalI);
            String id = String.valueOf(menuItemList.get(i).getId());
            Log.i("Loog", "id " + id);
            Initialization.repositoryApi.getStaticFromServer("menu", id, new StaticCallBack() {
                @Override
                public void myStatic(ResponseBody responseBody) {

                    if (responseBody!=null){
                        boolean b = true;
                        Log.i("Loog", "loadImgFromServer response not null " + finalI);
                    //Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                   //Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                   //ByteArrayOutputStream stream = new ByteArrayOutputStream();
                   //bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
                   //byte[] byteArray = stream.toByteArray();

                  ImgGazilla imgGazilla = null;
                   try {
                       imgGazilla = new ImgGazilla((menuItemList.get(finalI).getId()), "menu" , responseBody.bytes());
                    } catch (IOException e) {
                        Log.i("Loog", "loadImgFromServer IOException - " + e.getMessage());
                        e.printStackTrace();
                    }
                    imgGazillaList.add(imgGazilla);
                    }
                    else
                        Log.i("Loog", "loadImgFromServer response - null");
                }

                @Override
                public void showError(int error) {
                    Log.i("Loog", "loadImgFromServer error -" + error);
                    return;
                }
            }, new FailCallBack() {
                @Override
                public void setError(Throwable throwable) {
                    Log.i("Loog", "loadImgFromServer errorT -" + throwable.getMessage());
                    return;
                }
            });
        }

        loadImgInDB(imgGazillaList);
    }

    private void loadImgInDB(List<ImgGazilla> imgGazillaList){
        Log.i("Loog", "loadImgInDB");
        Initialization.repositoryDB.loadImgOnDB(imgGazillaList);
        checkVersionServerPromoDB();


    }*/

    //_________________________________Загрузка баланса___________________________________________

    private void myBalances(String publickey, String signature){

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

//______________________________ Мусор____________________________________

    private void addImgMenu(List<MenuCategory> menuCategoryList, LatestVersion latestVersion){
        Log.i("Loog", "загрузка картинок с сервера");
        List<MenuCategory> newMenuCategoryList = new ArrayList<>();


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        for(int iCategory = 0; iCategory<menuCategoryList.size(); iCategory++){
            List<MenuItem> items = new ArrayList<>();
            for (int iItem = 0; iItem < menuCategoryList.get(iCategory).getItems().size(); iItem++){

                int finalICategory = iCategory;
                int finalIItem = iItem;

                Log.i("Loog", "finalICategory - " + finalICategory +" finalIItem" + finalIItem);
                Initialization.repositoryApi.getStaticFromServer("menu",
                        String.valueOf(menuCategoryList.get(iCategory).getItems().get(iItem).getId()),
                        new StaticCallBack() {
                            @Override
                            public void myStatic(ResponseBody responseBody) {

                                Bitmap bmp = BitmapFactory.decodeStream(responseBody.byteStream());
                                bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
                                byte[] byteArray = stream.toByteArray();

                                boolean b;
                                if (byteArray==null) b=true;
                                else b = false;
                                Log.i("Loog", "new item array byte isEmpty" + b);

                                //MenuItem newItem = new MenuItem(menuCategoryList.get(finalICategory).getItems().get(finalIItem).getId(),
                                //        menuCategoryList.get(finalICategory).getItems().get(finalIItem).getName(),
                                //        menuCategoryList.get(finalICategory).getItems().get(finalIItem).getPrice(),
                                //        menuCategoryList.get(finalICategory).getItems().get(finalIItem).getDescription());
//
//
                                //items.add(newItem);





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
            MenuCategory category = new MenuCategory(menuCategoryList.get(iCategory).getId(),
                    menuCategoryList.get(iCategory).getName(), items);
            newMenuCategoryList.add(category);

        }

        //loadMenuInDB(newMenuCategoryList, latestVersion);
    }


}
