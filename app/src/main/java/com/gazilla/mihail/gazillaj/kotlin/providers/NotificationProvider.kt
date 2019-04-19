package com.gazilla.mihail.gazillaj.kotlin.providers

import android.graphics.BitmapFactory
import android.util.Log
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.*
import com.gazilla.mihail.gazillaj.kotlin.helps.signatur
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.LatestVersion
import com.gazilla.mihail.gazillaj.kotlin.pojo.Notification
import com.gazilla.mihail.gazillaj.kotlin.pojo.Success
import com.gazilla.mihail.gazillaj.kotlin.presenters.NotificationPresenter
import okhttp3.ResponseBody
import javax.inject.Inject

class NotificationProvider(private val notificationPresenter: NotificationPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi
    private val userWithKeys = App.userWithKeys

    init {
        App.appComponent.inject(this)
    }

    fun lastVersionNotification(lastVersionNotification: String){
     repositoryApi.getLastVersionNotification(userWithKeys.publickey, signatur(userWithKeys.privatekey, ""),
             object : LatestVersionCallBack{
                 override fun versionDB(latestVersion: LatestVersion?) {
                     if(latestVersion?.date != null){
                         if (latestVersion.date != lastVersionNotification){
                             Log.i("Loog", "Notification даты не совпадают")
                             notificationFromServer()
                             notificationPresenter.updateLatestVersionNotification(latestVersion.date)
                         }else
                             Log.i("Loog", "Notification даты совпадают")
                     }else
                         Log.i("Loog", "latestVersion - null")

                 }

                 override fun errorCallBack(error: String) {
                     Log.i("Loog", "NotificationProvider.lastVersionNotification.errorCallBack - $error")
                     BugReport().sendBugInfo(error, "NotificationProvider.lastVersionNotification.errorCallBack")
                 }

             }, object : FailCallBack{
         override fun throwableCallBack(throwable: Throwable) {
             Log.i("Loog", "NotificationProvider.lastVersionNotification.throwableCallBack - ${throwable.message}")
             BugReport().sendBugInfo(throwable.message.toString(), "NotificationProvider.lastVersionNotification.throwableCallBack")
         }
        })
    }

    fun notificationFromServer(){
        repositoryApi.getOllNotification(userWithKeys.publickey, signatur(userWithKeys.privatekey, ""),
                object : NotificationCallBack{
                    override fun ollNotification(notificationList: List<Notification>?) {
                        if (notificationList!!.size!=0)
                            getImageNotification(notificationList[notificationList.size-1])
                        else
                            Log.i("Loog", "notificationFromServer пустой")
                    }

                    override fun errorCallBack(error: String) {
                        Log.i("Loog", "NotificationProvider.notificationFromServer.errorCallBack - $error")
                        BugReport().sendBugInfo(error, "NotificationProvider.notificationFromServer.errorCallBack")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "NotificationProvider.notificationFromServer.throwableCallBack - ${throwable.message}")
                BugReport().sendBugInfo(throwable.message.toString(), "NotificationProvider.notificationFromServer.throwableCallBack")
            }
        })
    }


    fun getImageNotification(notification: Notification){
        repositoryApi.getStaticFromServer("alerts", notification.id.toString(),
                object : StaticCallBack{
                    override fun myStatic(responseBody: ResponseBody?) {
                        val b = responseBody!!.bytes()
                        val bitmap = BitmapFactory.decodeByteArray(b, 0, b.size)
                        notificationPresenter.showNotificationFromServer(notification, bitmap)
                    }

                    override fun errorCallBack(error: String) {
                        notificationPresenter.showNotificationFromServer(notification, null)
                        Log.i("Loog", "NotificationProvider.getImageNotification.errorCallBack - $error")
                        BugReport().sendBugInfo(error, "NotificationProvider.getImageNotification.errorCallBack")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "NotificationProvider.getImageNotification.throwableCallBack - ${throwable.message}")
                BugReport().sendBugInfo(throwable.message.toString(), "NotificationProvider.getImageNotification.throwableCallBack")
                notificationPresenter.showNotificationFromServer(notification, null)
            }
        })
    }

    fun sendAnswerNotification(alertId: Int, commandId: Int){
        val dat = "alertId=$alertId&commandId=$commandId"

        repositoryApi.sendAnswerUserAboutNotification(userWithKeys.publickey,alertId, commandId, signatur(userWithKeys.privatekey, dat),
                object : SuccessCallBack{
                    override fun successResponse(success: Success?) {

                    }

                    override fun errorCallBack(error: String) {
                        BugReport().sendBugInfo(error, "NotificationProvider.sendAnswerNotification.errorCallBack")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "NotificationProvider.sendAnswerNotification.throwableCallBack")
            }
        })
    }

}