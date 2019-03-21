package com.gazilla.mihail.gazillaj.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.Notificaton;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.utils.dagger2.DaggerInitializationComponent;
import com.gazilla.mihail.gazillaj.utils.dagger2.InitializationComponent;
import com.gazilla.mihail.gazillaj.utils.dagger2.Modules.ContextModule;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class InitializationAp extends Application {

    private static InitializationAp myInitializationAp;

    private static RepositoryApi repositoryApi;

    private static Notificaton notificaton;
    private static LatestVersion latestVersion;

    private UserWithKeys userWithKeys;

    @Override
    public void onCreate() {
        super.onCreate();

        daggerInit();
        myInitializationAp = new InitializationAp();
    }

    public static InitializationAp getInstance(){
            return myInitializationAp;
        }



    private void daggerInit(){
        InitializationComponent daggerInitializationComponent = DaggerInitializationComponent
                .builder()
                //.contextModule(new ContextModule(context))
                .build();

        repositoryApi = daggerInitializationComponent.repositoryApiComponent();
    }

    public void setUserWithKeys(UserWithKeys userWithKeys) {
        this.userWithKeys = userWithKeys;
    }


    public RepositoryApi getRepositoryApi() {
        return repositoryApi;
    }

    public void setNotificaton(Notificaton notificaton) {
        this.notificaton = notificaton;
    }

    public Notificaton getNotificaton() {
        return notificaton;
    }

    public void setLatestVersion(LatestVersion latestVersion) {
        this.latestVersion = latestVersion;
    }

    public LatestVersion getLatestVersion() {
        return latestVersion;
    }

    public UserWithKeys getUserWithKeys() {
        return userWithKeys;
    }

    private void getUserOffline(InitializationAp initializationAp) {
        SharedPreferences sharedPref = this.getSharedPreferences("myProf", Context.MODE_PRIVATE);
        //SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MyApplication.getAppContext());

        initializationAp.setUserWithKeys(new UserWithKeys(
                sharedPref.getInt("myID", -1),
                sharedPref.getString("publicKey", ""),
                sharedPref.getString("privateKey", "")
        ));
    }


    public  String signatur(String key, String data){
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            return new String(Hex.encodeHex(sha256_HMAC.doFinal(data.getBytes("UTF-8"))));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }
}
