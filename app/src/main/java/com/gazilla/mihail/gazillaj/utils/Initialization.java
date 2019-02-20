package com.gazilla.mihail.gazillaj.utils;

import android.content.Context;

import com.gazilla.mihail.gazillaj.model.data.db.AppDatabase;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryDB;
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

public class Initialization {

    private Context context;

    public static RepositoryApi repositoryApi;
    public static RepositoryDB repositoryDB;
    public static AppDatabase appDatabase;
    public static Notificaton notificaton;

    public static UserWithKeys userWithKeys;

    public Initialization(Context context) {
        this.context = context;
        daggerInit();
    }

    private void daggerInit(){
        InitializationComponent daggerInitializationComponent = DaggerInitializationComponent
                .builder()
                .contextModule(new ContextModule(context))
                .build();

        repositoryApi = daggerInitializationComponent.repositoryApiComponent();
        appDatabase = daggerInitializationComponent.getAppDatabase();
        repositoryDB = new RepositoryDB();
    }

    public static void setUserWithKeys(UserWithKeys userWithKeys) {
        Initialization.userWithKeys = userWithKeys;
    }


    public static String signatur(String key, String data){
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
