package com.gazilla.mihail.gazillaj.kotlin.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.AuthorizCallB
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.AuthorizationCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.UserWithKeys
import com.gazilla.mihail.gazillaj.kotlin.presenters.registaratiobAndAuthoriz.RegistrationFragmentPresenter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
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