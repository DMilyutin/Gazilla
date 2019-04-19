package com.gazilla.mihail.gazillaj.kotlin.presenters.registaratiobAndAuthoriz

import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.checkFormatPhone
import com.gazilla.mihail.gazillaj.kotlin.helps.saveUserInfoIntoSharedPreferences
import com.gazilla.mihail.gazillaj.kotlin.pojo.UserWithKeys
import com.gazilla.mihail.gazillaj.kotlin.providers.AuthorizationFragmentProvider
import com.gazilla.mihail.gazillaj.kotlin.views.registaratiobAndAuthoriz.AuthorizationFrView

@InjectViewState
class AuthorizationFragmentPresenter(private val sharedPreferences: SharedPreferences) : MvpPresenter<AuthorizationFrView>() {

    private val authorizationFragmentProvider = AuthorizationFragmentProvider(this)

    fun checkInputData(data: String){
        if (data.contains("@", true))
            getCodeForAuthorization("", data)
        else{
            val phone = checkFormatPhone(data)
            if (phone!="")
                getCodeForAuthorization(phone, "")
            else
                showErrorMessage("Неправильный формат")
        }
    }

    private fun getCodeForAuthorization(phone: String, email: String){
        Log.i("Loog", "AuthorizationFragmentPresenter getCodeForAuthorization")
        viewState.showLoadDialog()
        authorizationFragmentProvider.getCode(phone,email)
    }

    fun responseGetCodeForAuthorization(success: Boolean){
        Log.i("Loog", "AuthorizationFragmentPresenter responseGetCodeForAuthorization - $success")
        viewState.closeLoadDialog()
        if (success)
            viewState.showInputFieldForCode()
    }

    fun sendMyCodeForAuthorization(code: String){
        viewState.showLoadDialog()
        authorizationFragmentProvider.sendCode(code)
    }

    fun responseSendMyCodeForAuthorization(userWithKeys: UserWithKeys){
        Log.i("Loog", "AuthorizationFragmentPresenter responseSendMyCodeForAuthorization")
        viewState.closeLoadDialog()
        App.userWithKeys = userWithKeys
        saveUserInfoIntoSharedPreferences(sharedPreferences, userWithKeys)
        viewState.closeAuthorization(true)
    }


    fun showErrorMessage(error: String){
        viewState.showMessageDialog(error)
    }
}