package com.gazilla.mihail.gazillaj.kotlin.providers

import android.util.Log

import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.signatur
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.Success
import com.gazilla.mihail.gazillaj.kotlin.presenters.AccountPresenter

import javax.inject.Inject

class AccountProvider(val accountPresenter: AccountPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun saveUserDataOnServer(name: String, phone: String, email: String){

        val data = "email=" + email + "&" +
                "name=" + name + "&" +
                "phone=" + phone

        val signature = signatur(App.userWithKeys.privatekey, data)
        repositoryApi.updateUserData(name, phone, email, App.userWithKeys.publickey, signature,
                object : SuccessCallBack{
                    override fun successResponse(success: Success?) {
                        if (success!!.success){
                            accountPresenter.saveSuccses()
                        }
                    }

                    override fun errorCallBack(error: String) {
                       accountPresenter.showError(error)
                    }
                }
                ,object : FailCallBack{
                    override fun throwableCallBack(throwable: Throwable) {
                        accountPresenter.showError(throwable.message.toString())
                        BugReport().sendBugInfo(throwable.message.toString(), "AccountProvider.saveUserDataOnServer.throwableCallBack")
                    }
        })



    }
}