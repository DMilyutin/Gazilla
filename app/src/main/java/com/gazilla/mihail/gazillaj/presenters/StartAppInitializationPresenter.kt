package com.gazilla.mihail.gazillaj.presenters

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.MenuFromServer
import com.gazilla.mihail.gazillaj.helps.PromoFromServer
import com.gazilla.mihail.gazillaj.pojo.User
import com.gazilla.mihail.gazillaj.providers.StartAppInitializationProviders
import com.gazilla.mihail.gazillaj.views.StartAppInitializationView

@InjectViewState
class StartAppInitializationPresenter(private val sharedPreferences: SharedPreferences) :
        MvpPresenter<StartAppInitializationView>() {

    private val startAppInitializationProviders = StartAppInitializationProviders(this)

    fun checkMyVersionApp(){
        startAppInitializationProviders.actualVersionApp(App.appVersionGazilla)
    }

    fun checkUserData(){
        val sharedPrefData = sharedPreferences.contains("myID")

        if (sharedPrefData){
            userData()
            startAppInitializationProviders.getUserDataFromServer()
        }
        else{
            viewState.startRegistrationActivity()
        }
    }

    private fun userData(){
        App.userWithKeys.id = sharedPreferences.getInt("myID", -1)
        if (sharedPreferences.getString("publicKey", "") != ""){
            App.userWithKeys.publickey = sharedPreferences.getString("publicKey", "")!!
            App.userWithKeys.privatekey = sharedPreferences.getString("privateKey", "")!!
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

   private fun initializationMenuFromServer(){
        App.menuFromServer = MenuFromServer()
        App.menuFromServer.updateMenu()
    }

    private fun initilizationPromoFromServer(){
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