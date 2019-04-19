package com.gazilla.mihail.gazillaj.kotlin.helps

import android.app.Application
import com.gazilla.mihail.gazillaj.kotlin.di.AppComponent
import com.gazilla.mihail.gazillaj.kotlin.di.DaggerAppComponent
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.kotlin.pojo.UserWithKeys
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class App: Application() {

    companion object {
        val appVersionGazilla  = "1.0.0"

        lateinit var userWithKeys: UserWithKeys
        lateinit var appComponent: AppComponent
        lateinit var menuFromServer: MenuFromServer
        lateinit var promoFromServer: PromoFromServer
        lateinit var myId: String


    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
        userWithKeys = UserWithKeys()
        myId()
    }

    private fun initializeDagger(){
        appComponent = DaggerAppComponent.builder()
                .build()

    }

    private fun myId() {
        Observable.fromCallable {
            AdvertisingIdClient.getAdvertisingIdInfo(applicationContext).id // id гугл рекламы
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { s -> myId = s }
        //disposable.dispose()
    }
}