package com.gazilla.mihail.gazillaj.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.AuthorizationCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.Success
import com.gazilla.mihail.gazillaj.pojo.UserWithKeys
import com.gazilla.mihail.gazillaj.presenters.registaratiobAndAuthoriz.AuthorizationFragmentPresenter
import javax.inject.Inject

class AuthorizationFragmentProvider(private val authorizationFragmentPresenter: AuthorizationFragmentPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun getCode(phone: String, email: String){
        Log.i("Loog", "Отправка запроса на получение кода")
        repositoryApi.authorizationCodeForAccount(phone, email, object : SuccessCallBack{
            override fun successResponse(success: Success?) {
                Log.i("Loog", "Получение кода - ${success!!.success}")
                authorizationFragmentPresenter.responseGetCodeForAuthorization(success.success)
            }

            override fun errorCallBack(error: String) {
                authorizationFragmentPresenter.showErrorMessage(error)
            }

        }, failCallBack = object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                authorizationFragmentPresenter.showErrorMessage(throwable.message!!)
                BugReport().sendBugInfo(throwable.message.toString(), "AuthorizationFragmentProvider.getCode.throwableCallBack")
            }
        })
    }

    fun sendCode(code: String){
        repositoryApi.sendCodeForLoging(code, object : AuthorizationCallBack {
            override fun userWithKeyCallBack(userWithKeys: UserWithKeys) {
                authorizationFragmentPresenter.responseSendMyCodeForAuthorization(userWithKeys)
            }

            override fun errorCallBack(error: String) {
                authorizationFragmentPresenter.showErrorMessage(error)
            }

        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                authorizationFragmentPresenter.showErrorMessage(throwable.message!!)
                BugReport().sendBugInfo(throwable.message.toString(), "AuthorizationFragmentProvider.sendCode.throwableCallBack")
            }
        })
    }
}