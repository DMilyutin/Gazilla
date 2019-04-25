package com.gazilla.mihail.gazillaj.views

import android.graphics.Bitmap
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gazilla.mihail.gazillaj.pojo.Notification

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface MainView: MvpView {

    fun setScoreInfo(score: String)
    fun addFirstFragment(fragment: Fragment)
    fun replaceFragment(fragment: Fragment, nameMenu: String)
    fun showImgOpenAccount(show: Boolean)

    fun startReserveActivity()
    fun sendAnswerNotification(alertId: Int, commandId: Int)
    fun openMenuFragmentBeforeNotification(nameFragment: String)

    fun showNotification(notification: Notification, bitmap: Bitmap?)
}