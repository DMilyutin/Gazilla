package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.content.SharedPreferences
import android.graphics.Bitmap
import com.gazilla.mihail.gazillaj.kotlin.pojo.Notification
import com.gazilla.mihail.gazillaj.kotlin.providers.NotificationProvider
import com.gazilla.mihail.gazillaj.kotlin.views.MainView

class NotificationPresenter(private val sharedPreferences: SharedPreferences, private val mainView: MainView) {

    private val notificationProvider = NotificationProvider(this)

    init {
        checkNewNotification()
    }

    private fun checkNewNotification(){
        var lastDate = sharedPreferences.getString("versionNotification", "")
        if (lastDate== null)
            lastDate = "last"
        notificationProvider.lastVersionNotification(lastDate)
    }

    fun showNotificationFromServer(notification: Notification, bitmap: Bitmap?){
        mainView.showNotification(notification, bitmap)
    }

    fun updateLatestVersionNotification(lastVersionNotification: String){
        val edit =sharedPreferences.edit()
        edit.putString("versionNotification", lastVersionNotification)
        edit.apply()
    }

    fun sendAnswerNotification(alertId: Int,  commandId: Int){
        notificationProvider.sendAnswerNotification(alertId, commandId)
    }
}