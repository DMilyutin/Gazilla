package com.gazilla.mihail.gazillaj.model.repository;


import android.util.Log;

import com.gazilla.mihail.gazillaj.utils.POJO.Balances;
import com.gazilla.mihail.gazillaj.model.data.api.ServerApi;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.callBacks.AutorizationCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.BalanceCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.DragonWeyCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBMenuItemCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LVersionDBPromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.LevelsCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.MenuItemCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.PromoCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.QTYCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SmokerpassCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.UserCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.WheelCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class RepositoryApi {

    private ServerApi serverApi;

    public RepositoryApi(ServerApi serverApi) {
        this.serverApi = serverApi;
    }

    public void getCodeForLogin(String phone, String email, SuccessCallBack successCallBack, FailCallBack failCallBack) {
        serverApi.getCodeForLoging(phone, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    if (successResponse.isSuccessful()) {
                        successCallBack.reservResponse(successResponse.body());
                    } else
                        successCallBack.errorResponse(successResponse.errorBody().string());
                }, failCallBack::setError);
    }

    public void sendCodeForLoging(String code, AutorizationCallBack autorizationCallBack, FailCallBack failCallBack) {
        serverApi.sendCodeForLoging(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userWithKeysResponse -> {
                    if (userWithKeysResponse.isSuccessful()) {
                        autorizationCallBack.AutorizCallBack(userWithKeysResponse.body());
                    } else
                        autorizationCallBack.showError(userWithKeysResponse.errorBody().string());
                }, failCallBack::setError);
    }

    public void registration(String name, String phone, String email, String password, String referer, String promo,
                             AutorizationCallBack autorizationCallBack, FailCallBack failCallBack) {
        serverApi.registration(name, phone, email, password, referer, promo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userWithKeysResponse -> {
                    if (userWithKeysResponse.isSuccessful())
                        autorizationCallBack.AutorizCallBack(userWithKeysResponse.body());
                    else autorizationCallBack.showError(userWithKeysResponse.errorBody().string());
                }, failCallBack::setError);


    }

    public void updateUserData(String name, String phone, String email, String signature, SuccessCallBack succsesCallback,
                               FailCallBack failCallBack) {
        serverApi.updateUserData(email, name, phone, Initialization.userWithKeys.getPublickey(), signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    if (successResponse.isSuccessful()) {
                        succsesCallback.reservResponse(successResponse.body());
                    } else
                        succsesCallback.errorResponse(successResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void userData(UserCallBack userCallBack, FailCallBack failCallBack) {
        String publKey = Initialization.userWithKeys.getPublickey();
        String emptySignatur = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), "");
        serverApi.getDataUser(publKey, emptySignatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userResponse -> {
                    if (userResponse.isSuccessful()) {
                        userCallBack.userCallBack(userResponse.body());
                    } else
                        userCallBack.errorUser(userResponse.errorBody().string());

                }, failCallBack::setError);
    }

    public void myBalances(String publickey, String signature, BalanceCallBack balanceCallBack,
                           FailCallBack failCallBack) {
        serverApi.getBalances(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(balancesResponse -> {
                    if (balancesResponse.isSuccessful())
                        balanceCallBack.myBalance(balancesResponse.body());
                    else {
                        Log.i("Loog", "myBalances err -" + balancesResponse.errorBody().byteStream());
                        balanceCallBack.showError(balancesResponse.errorBody().string());
                    }
                }, failCallBack::setError);


    }

    public void levels(LevelsCallBack levelsCallBack, FailCallBack failCallBack) {
        String publKey = Initialization.userWithKeys.getPublickey();
        String emptySignatur = Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), "");
        serverApi.getLevels(publKey, emptySignatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mapResponse -> {
                    if (mapResponse.isSuccessful()) {
                        levelsCallBack.levelsFromSerser(mapResponse.body());
                    } else
                        levelsCallBack.errorLevels(mapResponse.errorBody().string());
                }, failCallBack::setError);
    }

    public void ollMenu(String publickey, String signature, MenuCallBack menuCallBack,
                        FailCallBack failCallBack) {
        serverApi.getOllMenu(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponse -> {
                    if (listResponse.isSuccessful())
                        menuCallBack.ollMenu(listResponse.body());
                    else menuCallBack.showError(listResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void addFavoriteItem(String publickey, int id, String signature, SuccessCallBack successCallBack,
                                FailCallBack failCallBack) {
        serverApi.addFavoritItem(publickey, id, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    if (successResponse.isSuccessful()) {
                        successCallBack.reservResponse(successResponse.body());
                    } else
                        successCallBack.errorResponse(successResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void delFavoritItem(String publickey, int id, String signature, SuccessCallBack successCallBack,
                               FailCallBack failCallBack) {
        serverApi.deleteFavoritItem(publickey, id, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    if (successResponse.isSuccessful()) {
                        successCallBack.reservResponse(successResponse.body());
                    } else
                        successCallBack.errorResponse(successResponse.errorBody().string());
                }, failCallBack::setError);
    }


    public void lastVersionMenu(String publickey, String signature, LVersionDBMenuCallBack menuCallBack,
                                FailCallBack failCallBack) {
        serverApi.lastVersionDBMenu(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestVersionResponse -> {
                    if (latestVersionResponse.isSuccessful())
                        menuCallBack.versionDBMenu(latestVersionResponse.body());
                    else
                        menuCallBack.showError(latestVersionResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void giftsOnServer(String publickey, String signature, MenuItemCallBack itemCallBack,
                              FailCallBack failCallBack) {
        serverApi.getGifts(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponse -> {
                    if (listResponse.isSuccessful())
                        itemCallBack.menuItem(listResponse.body());
                    else
                        itemCallBack.showError(listResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void lastVersionMenuItem(String publickey, String signature, LVersionDBMenuItemCallBack itemCallBack,
                                    FailCallBack failCallBack) {
        serverApi.lastVersionDBGifts(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestVersionResponse -> {
                    if (latestVersionResponse.isSuccessful())
                        itemCallBack.versionDBMenuItem(latestVersionResponse.body());
                    else
                        itemCallBack.showError(latestVersionResponse.code());
                }, failCallBack::setError);

    }

    public void ollPromo(String publickey, String signature, PromoCallBack promoCallBack,
                         FailCallBack failCallBack) {

        serverApi.getOllPromo(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listResponse -> {
                    if (listResponse.isSuccessful())
                        promoCallBack.myPromo(listResponse.body());
                    else
                        promoCallBack.showError(listResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void lastVersionPromo(String publickey, String signature, LVersionDBPromoCallBack promoCallBack,
                                 FailCallBack failCallBack) {
        serverApi.lastVersionDBPromo(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(latestVersionResponse -> {
                    if (latestVersionResponse.isSuccessful())
                        promoCallBack.versionDBPromo(latestVersionResponse.body());
                    else
                        promoCallBack.showError(latestVersionResponse.code());
                }, failCallBack::setError);

    }

    public void getStaticFromServer(String folder, String id, StaticCallBack staticCallBack,
                                    FailCallBack failCallBack) {
        serverApi.getStaticFromServer(folder, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(objectResponse -> {
                    if (objectResponse.isSuccessful())
                        staticCallBack.myStatic(objectResponse.body());
                    else staticCallBack.showError(objectResponse.errorBody().string());
                }, failCallBack::setError);

    }

    public void mySpins(String publickey, String signature, QTYCallBack qtyCallBack,
                        FailCallBack failCallBack) {
        serverApi.mySpins(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(qtyResponse -> {
                    if (qtyResponse.isSuccessful())
                        qtyCallBack.myQTY(qtyResponse.body());
                    Log.i("Loog", "qty -" + qtyResponse.body().getQty());
                }, failCallBack::setError);

    }

    public void wheeling(String publickey, String signature, WheelCallBack wheelCallBack,
                         FailCallBack failCallBack) {
        serverApi.wheeling(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dragonWheelResponse -> {
                    if (dragonWheelResponse.isSuccessful()) {
                        wheelCallBack.myWin(dragonWheelResponse.body());

                        myBalances(Initialization.userWithKeys.getPublickey(),
                                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                                new BalanceCallBack() {
                                    @Override
                                    public void myBalance(Balances balances) {
                                        Initialization.userWithKeys.setScore(balances.getScore());
                                    }

                                    @Override
                                    public void showError(String error) {

                                    }
                                }, new FailCallBack() {
                                    @Override
                                    public void setError(Throwable throwable) {

                                    }
                                });
                    } else
                        wheelCallBack.winError(dragonWheelResponse.errorBody().string());

                }, failCallBack::setError);
    }

    public void reserving(int qty, int hours, String date,
                          String phone, String name, String comment, Boolean preorder, String publickey,
                          String signatur, SuccessCallBack successCallBack, FailCallBack failCallBack) {
        serverApi.reserving(comment, date, hours, name, phone, preorder, publickey, qty,
                signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    if (successResponse.isSuccessful()) {
                        successCallBack.reservResponse(successResponse.body());
                        Log.i("Loog", "success mes " + successResponse.code());
                    } else {
                        Log.i("Loog", "success mes " + successResponse.code());
                        Log.i("Loog", "error reserving repository API-" + successResponse.errorBody().byteStream());
                        successCallBack.errorResponse(successResponse.errorBody().string());
                    }
                }, failCallBack::setError);

    }

    public void dragonwing(String publickey, String signatur, DragonWeyCallBack dragonWeyCallBack,
                           FailCallBack failCallBack) {
        serverApi.getLvlDragonway(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(levelsResponse -> {
                    if (levelsResponse.isSuccessful()) {
                        dragonWeyCallBack.myLvl(levelsResponse.body());
                    } else
                        dragonWeyCallBack.errorTxt(levelsResponse.errorBody().string());
                }, failCallBack::setError);
    }

    public void smokerpassing(String publickey, String signatur, SmokerpassCallBack smokerpassCallBack,
                              FailCallBack failCallBack) {
        serverApi.getPromoSmokerpass(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(promoSmokerpassResponse -> {
                    if (promoSmokerpassResponse.isSuccessful()) {
                        smokerpassCallBack.mySmokerpass(promoSmokerpassResponse.body());
                    } else
                        smokerpassCallBack.errorTxt(promoSmokerpassResponse.errorBody().string());
                }, failCallBack::setError);
    }

    public void sendBugReport(String message, String publickey, String signatur, SuccessCallBack successCallBack,
                              FailCallBack failCallBack) {

        serverApi.sendBugReport(message, publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(successResponse -> {
                    if (successResponse.isSuccessful()){
                        successCallBack.reservResponse(successResponse.body());
                    }
                }, failCallBack::setError);

    }
}
