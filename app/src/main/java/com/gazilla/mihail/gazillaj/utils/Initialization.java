package com.gazilla.mihail.gazillaj.utils;

import android.arch.persistence.room.Room;
import android.content.Context;


import android.util.Base64;
import android.util.Log;

import com.gazilla.mihail.gazillaj.BuildConfig;
import com.gazilla.mihail.gazillaj.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.data.api.ServerApi;
import com.gazilla.mihail.gazillaj.model.data.db.AppDatabase;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryDB;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;


import org.apache.commons.codec.binary.Hex;
import org.apache.http.params.HttpParams;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Initialization {

    private static final String URL = "https://admin.gazilla-lounge.ru/";

    private static ServerApi serverApi;
    public  static SharedPref sharedPref;
    public static RepositoryApi repositoryApi;
    public static RepositoryDB repositoryDB;

    public static UserWithKeys userWithKeys;

    public static AppDatabase appDatabase;
    private Context instance;

    public Initialization(Context instance) {
        this.instance = instance;

        sharedPref = new SharedPref(instance);

        init();
    }

    private void init(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        serverApi = retrofit.create(ServerApi.class);

        repositoryApi = new RepositoryApi(serverApi);
        appDatabase = Room.databaseBuilder(instance, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();

        repositoryDB = new RepositoryDB();

        if(appDatabase == null)
            Log.i("Loog", "appDatabase - null");



    }

    public static void setUserWithKeys(UserWithKeys userWithKeys) {
        Initialization.userWithKeys = userWithKeys;
    }


    public static String signatur(String key, String data){
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secret_key);


            //String hash1 = Base64.encodeToString(sha256_HMAC.doFinal(data.getBytes("UTF-8")), 0);

            //String hash2 = String.valueOf(Base64.encode(data.getBytes("UTF-8"), Base64.NO_WRAP));
            //return hash2;

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



    public static String signatur2( String secret, String str){
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256");
            sha256_HMAC.init(secretKey);
            String hash1 = Base64.encodeToString(sha256_HMAC.doFinal(str.getBytes("UTF-8")), Base64.NO_WRAP);
            return hash1;

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
