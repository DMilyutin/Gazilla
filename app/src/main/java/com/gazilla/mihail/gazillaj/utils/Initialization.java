package com.gazilla.mihail.gazillaj.utils;

import android.arch.persistence.room.Room;
import android.content.Context;


import android.media.Image;
import android.util.Log;

import com.gazilla.mihail.gazillaj.BuildConfig;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;
import com.gazilla.mihail.gazillaj.model.data.api.ServerApi;
import com.gazilla.mihail.gazillaj.model.data.db.AppDatabase;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi;
import com.gazilla.mihail.gazillaj.model.repository.RepositoryDB;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.StaticCallBack;
import com.gazilla.mihail.gazillaj.utils.dagger2.DaggerInitializationComponent;
import com.gazilla.mihail.gazillaj.utils.dagger2.InitializationComponent;
import com.gazilla.mihail.gazillaj.utils.dagger2.Modules.ContextModule;
import com.gazilla.mihail.gazillaj.utils.dagger2.Modules.RepositoriApiModule;


import org.apache.commons.codec.binary.Hex;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Initialization {

    private Context context;

    public static RepositoryApi repositoryApi;
    public static RepositoryDB repositoryDB;
    public static AppDatabase appDatabase;

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









    /*private void init(){
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
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();

        repositoryDB = new RepositoryDB();

        if(appDatabase == null)
            Log.i("Loog", "appDatabase - null");



    }*/





}
