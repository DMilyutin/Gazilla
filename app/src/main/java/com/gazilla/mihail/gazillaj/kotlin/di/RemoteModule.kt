package com.gazilla.mihail.gazillaj.kotlin.di

import com.gazilla.mihail.gazillaj.BuildConfig
import com.gazilla.mihail.gazillaj.kotlin.model.ServerApi
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RemoteModule {

    private val URL = "https://admin.gazilla-lounge.ru/"

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .readTimeout(100, TimeUnit.SECONDS)
                .build()


    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor()
                .setLevel(if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()


    @Provides
    @Singleton
    fun provideServerApi(retrofit: Retrofit): ServerApi = retrofit.create(ServerApi::class.java)

    @Provides
    @Singleton
    fun getRepositoryAPI(serverApi: ServerApi): RepositoryApi = RepositoryApi(serverApi)


}