package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.checkFormatPhone
import com.gazilla.mihail.gazillaj.kotlin.providers.AccountProvider
import com.gazilla.mihail.gazillaj.kotlin.views.AccountView

@InjectViewState
class AccountPresenter(private val sharedPreferences: SharedPreferences): MvpPresenter<AccountView>() {

    private val accountProvider = AccountProvider(this)

    fun getUserInfo(){

        val id = App.userWithKeys.id.toString()
        val name = sharedPreferences.getString("APP_PREF_MY_NAME","")
        val phone = sharedPreferences.getString("APP_PREF_MY_PHONE","")
        val email = sharedPreferences.getString("APP_PREF_MY_EMAIL","")
        val s = checkFormatPhone(phone)
        viewState.run {
            setUserId(id)
            setUserInfo(name, s, email)
        }
    }

    fun saveNewUserInfo(name:String, phone:String, email:String){
        viewState.showLoadDialog()
        val s = checkFormatPhone(phone)
        if (s!=""){
            accountProvider.saveUserDataOnServer(name, s, email)
            saveNewUserDataInSharedPreferences(name, s, email)
        }
        else
            showError("Неверный формат номера")
    }

    private fun saveNewUserDataInSharedPreferences(name: String?, phone: String?, email: String?){
        Log.i("Loog", "kotlin сохранение данных в преф")
            val editor = sharedPreferences.edit()
            editor.putString("APP_PREF_MY_NAME", name)
            editor.putString("APP_PREF_MY_PHONE", phone)
            editor.putString("APP_PREF_MY_EMAIL", email)
            editor.apply()
        }

    fun saveSuccses(){
        viewState.run {
            closeLoadDialog()
            showMessageDialog(message = "Ваши данные успешно сохранены")
        }
    }

    fun showError(error: String?){
        viewState.run {
            closeLoadDialog()
            showMessageDialog(error)
        }
    }

}
