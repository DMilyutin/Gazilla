package com.gazilla.mihail.gazillaj.presenters

import android.content.SharedPreferences
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.checkFormatPhone
import com.gazilla.mihail.gazillaj.providers.AccountProvider
import com.gazilla.mihail.gazillaj.views.AccountView

@InjectViewState
class AccountPresenter(private val sharedPreferences: SharedPreferences): MvpPresenter<AccountView>() {

    private val accountProvider = AccountProvider(this)

    fun getUserInfo(){

        val id = App.userWithKeys.id.toString()
        val name = sharedPreferences.getString("myName","")
        val phone = sharedPreferences.getString("myPhone","")
        val email = sharedPreferences.getString("myEmail","")
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
            editor.putString("myName", name)
            editor.putString("myPhone", phone)
            editor.putString("myEmail", email)
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
