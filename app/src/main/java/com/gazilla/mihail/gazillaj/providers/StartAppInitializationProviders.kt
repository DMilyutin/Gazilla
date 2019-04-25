package com.gazilla.mihail.gazillaj.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.UserCallBack
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.Success
import com.gazilla.mihail.gazillaj.pojo.User
import com.gazilla.mihail.gazillaj.presenters.StartAppInitializationPresenter
import javax.inject.Inject

class StartAppInitializationProviders(private val startAppInitializationPresenter: StartAppInitializationPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun actualVersionApp(version: String){
        repositoryApi.getActualVersionApp(version, object : SuccessCallBack{
            override fun successResponse(success: Success?) {
                if (success!!.success){
                    startAppInitializationPresenter.checkUserData()
                    Log.i("Loog", "my version - поддерживается")
                }
                else{
                    startAppInitializationPresenter.showUpdateMessege()
                    Log.i("Loog", "my version - не поддерживается")
                }
            }

            override fun errorCallBack(error: String) {
                startAppInitializationPresenter.showErrorMessage(error)
                Log.i("Loog", "my version error- $error")
            }

        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "my version Throwable- ${throwable.message}")
                BugReport().sendBugInfo(throwable.message.toString(), "StartAppInitializationProviders.actualVersionApp.throwableCallBack")
                startAppInitializationPresenter.showErrorMessage(throwable.message.toString())
            }
        })
    }

    fun getUserDataFromServer(){
        repositoryApi.userData(App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, ""),
                object : UserCallBack{
                    override fun userCallBack(user: User?) {
                        startAppInitializationPresenter.setUserInfoFromServer(user!!)
                    }

                    override fun errorCallBack(error: String) {
                        startAppInitializationPresenter.showErrorMessage(error)
                    }
                },
                object : FailCallBack{
                    override fun throwableCallBack(throwable: Throwable) {
                        BugReport().sendBugInfo(throwable.message.toString(), "StartAppInitializationProviders.getUserDataFromServer.throwableCallBack")
                        startAppInitializationPresenter.showErrorMessage(throwable.message.toString())

                    }
        })
    }

}