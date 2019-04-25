package com.gazilla.mihail.gazillaj.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.AuthorizCallB
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.UserWithKeys
import com.gazilla.mihail.gazillaj.presenters.registaratiobAndAuthoriz.RegistrationFragmentPresenter
import javax.inject.Inject

class RegistrationFragmentProvider(private val registrationFragmentPresenter: RegistrationFragmentPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun registrationNewUser(name: String, phone: String, email: String, pass: String,
                            referer: String, promo: String, myDeviceId: String) {

        repositoryApi.registration(name, phone, email, pass, referer, promo, myDeviceId, object : AuthorizCallB {
            override fun userWithKeyCallBack(userWithKeys: UserWithKeys) {
                if (userWithKeys.id!=0){
                    registrationFragmentPresenter.responseeRegistrationNewUser(userWithKeys)
                    Log.i("Loog", "RegistrationFragmentProvider reg - ${userWithKeys.id}")
                }
                else
                    Log.i("Loog", "RegistrationFragmentProvider reg - null")
            }

            override fun errorCallBack(error: String) {
                registrationFragmentPresenter.showErrorMessage(error)
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                registrationFragmentPresenter.showErrorMessage(throwable.message!!)
                BugReport().sendBugInfo(throwable.message.toString(), "RegistrationFragmentProvider.registrationNewUser.throwableCallBack")
            }
        })

    }


}