package com.gazilla.mihail.gazillaj.providers

import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.Success
import com.gazilla.mihail.gazillaj.presenters.AccountPresenter

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