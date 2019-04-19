package com.gazilla.mihail.gazillaj.kotlin.helps.BugReport

import android.util.Log
import com.gazilla.mihail.gazillaj.BuildConfig
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.signatur
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.Success
import javax.inject.Inject

class BugReport {

    @Inject lateinit var repositoryApy: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun sendBugInfo(exception: String, location: String){
        val message = createErrorMessage(exception, location)
        repositoryApy.sendBugReport(message, App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, "message=$message"), object : SuccessCallBack{
            override fun successResponse(success: Success?) {
                Log.i("Loog", "BugReport successResponse - ${success!!.success}")
            }

            override fun errorCallBack(error: String) {
                Log.i("Loog", "BugReport errorCallBack - $error")
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "BugReport throwableCallBack - ${throwable.message}")
            }
        })
    }

    fun sendUserInfoForReserve(message: String){
        repositoryApy.sendBugReport(message, App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, "message=$message"), object : SuccessCallBack{
            override fun successResponse(success: Success?) {
                Log.i("Loog", "BugReport successResponse - $success")
            }

            override fun errorCallBack(error: String) {
                Log.i("Loog", "BugReport errorCallBack - $error")
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "BugReport throwableCallBack - ${throwable.message}")
            }
        })
    }

    private fun createErrorMessage(exception: String, location: String): String{
        val appVersionCode = BuildConfig.VERSION_CODE
        return "Версия программы - $appVersionCode. Android bug: $exception. Location: $location"
    }
}