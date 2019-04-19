package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.BuildConfig
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.MenuFromServer
import com.gazilla.mihail.gazillaj.kotlin.helps.PromoFromServer
import com.gazilla.mihail.gazillaj.kotlin.pojo.User
import com.gazilla.mihail.gazillaj.kotlin.providers.StartAppInitializationProviders
import com.gazilla.mihail.gazillaj.kotlin.views.StartAppInitializationView

@InjectViewState
class StartAppInitializationPresenter(private val sharedPreferences: SharedPreferences, private val isConnect: Boolean) :
        MvpPresenter<StartAppInitializationView>() {

    private val startAppInitializationProviders = StartAppInitializationProviders(this)

    fun checkMyVersionApp(){
        startAppInitializationProviders.actualVersionApp(App.appVersionGazilla)
    }

    fun checkUserData(){
        val sharedPrefData = sharedPreferences.contains("APP_PREF_MY_ID")
        val myId = sharedPreferences.getInt("APP_PREF_MY_ID",-1)

        var code = "00"

        if (isConnect && sharedPrefData) code = "11"
        else if (!isConnect && sharedPrefData) code = "01"
        else if (isConnect && !sharedPrefData) code = "10"

        Log.i("Loog", "checkUserData - $code")
        Log.i("Loog", "checkUserData my id - $myId")
        when(code){
            "11" -> {
                Log.i("Loog", "checkUserDate - есть интернет и прошлые данные")
                userData()
                startAppInitializationProviders.getUserDataFromServer()

            }
            "01" -> {
                Log.i("Loog", "checkUserDate - нет интернет и есть прошлые данные")
                userData()
                viewState.startMainActivity()
            }
            "10" -> {
                Log.i("Loog", "checkUserDate - есть интернет и нет прошлых данных")
                viewState.startRegistrationActivity()
            }
            else -> {
                Log.i("Loog", "checkUserDate - нет интернет и нет прошлых данных")
                showErrorMessage("Для первого запуска необходим доступ в интернет")
            }
        }
    }

    private fun userData(){
        App.userWithKeys.id = sharedPreferences.getInt("APP_PREF_MY_ID", -1)
        if (sharedPreferences.getString("APP_PREF_PUBLIC_KEY", "") != ""){
            App.userWithKeys.publickey = sharedPreferences.getString("APP_PREF_PUBLIC_KEY", "")!!
            App.userWithKeys.privatekey = sharedPreferences.getString("APP_PREF_PRIVATE_KEY", "")!!
        }else
            viewState.startRegistrationActivity()
    }

    fun setUserInfoFromServer(user: User){
        App.userWithKeys.level = user.level
        App.userWithKeys.refererLink = user.refererLink
        App.userWithKeys.name = user.name
        App.userWithKeys.phone = user.phone
        App.userWithKeys.favorites = user.favorites
        viewState.startMainActivity()

        initializationMenuFromServer()
        initilizationPromoFromServer()
    }

    fun initializationMenuFromServer(){
        App.menuFromServer = MenuFromServer()
        App.menuFromServer.updateMenu()
    }

    fun initilizationPromoFromServer(){
        App.promoFromServer = PromoFromServer()
        App.promoFromServer.donloadStocks()
    }

    fun showErrorMessage(error: String){
        viewState.showMessageDialog(error)
    }

    fun showUpdateMessege(){
        viewState.showUpdateAppDialog()
    }
}