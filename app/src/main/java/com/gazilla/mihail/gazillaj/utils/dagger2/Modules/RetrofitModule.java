package com.gazilla.mihail.gazillaj.utils.dagger2.Modules;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module(includes = OkHttpClientModule.class)
public class RetrofitModule {

    private final String URL = "https://admin.gazilla-lounge.ru/";

    @Provides
    public Retrofit getRetrofit(OkHttpClient client){
        return new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }
}
