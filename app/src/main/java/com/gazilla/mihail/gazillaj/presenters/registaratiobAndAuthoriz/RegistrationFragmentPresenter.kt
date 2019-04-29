package com.gazilla.mihail.gazillaj.presenters.registaratiobAndAuthoriz

import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.saveUserInfoIntoSharedPreferences
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.UserWithKeys
import com.gazilla.mihail.gazillaj.providers.RegistrationFragmentProvider
import com.gazilla.mihail.gazillaj.views.registaratiobAndAuthoriz.RegistrationFrView
import javax.inject.Inject

@InjectViewState
class RegistrationFragmentPresenter(private val sharedPreferences: SharedPreferences) : MvpPresenter<RegistrationFrView>() {

    private val registrationFragmentProvider = RegistrationFragmentProvider(this)

    @Inject
    lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun getNewUserInfo(withPromo: Boolean, promo: String, myDeviceID: String){
        if (withPromo && promo==""){
            viewState.showMessageDialog("Промокод не может быть пустым")
            return
        }

        viewState.showLoadDialog()

        val typePromo = checkDataIsPromoOrRefer(promo)

        if (typePromo == "Refer")
            registrationNewUser(promo, "", myDeviceID)
        else
            registrationNewUser("", promo, myDeviceID)
    }

    private fun checkDataIsPromoOrRefer(data: String): String{
        return if (data.matches("[0-9]+".toRegex()))
            "Refer"
        else
            "Promo"
    }

    private fun registrationNewUser(referer: String, promo: String, myDeviceID: String){
        Log.i("Loog", "registrationNewUser")
        registrationFragmentProvider.registrationNewUser("", "", "","", referer, promo, myDeviceID)
        //registrationNewUser("", "", "","", referer, promo, myDeviceID)
    }

    fun responseeRegistrationNewUser(userWithKeys: UserWithKeys){
        Log.i("Loog", "responseRegistrationNewUser")
        viewState.closeLoadDialog()
        App.userWithKeys = userWithKeys
        saveUserInfoIntoSharedPreferences(sharedPreferences, userWithKeys)
        viewState.closeRegistration(true)
    }

    fun showErrorMessage(message: String){
        viewState.showMessageDialog(message)
        viewState.closeRegistration(false)
    }

    private fun test(userWithKeys: UserWithKeys){
        Log.i("Loog", "userWithKeys id - ${userWithKeys.id}")
        viewState.closeLoadDialog()
        App.userWithKeys = userWithKeys
        saveUserInfoIntoSharedPreferences(sharedPreferences, userWithKeys)
        viewState.closeRegistration(true)
    }
}


