package com.gazilla.mihail.gazillaj.utils.dagger2.Modules;

import com.gazilla.mihail.gazillaj.BuildConfig;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

@Module
public class OkHttpClientModule {

    @Provides
    public OkHttpClient getOkHttpClient(HttpLoggingInterceptor interceptor){
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    public HttpLoggingInterceptor getHttpLoggingInterceptor(){
        return new HttpLoggingInterceptor()
        .setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
    }
}
