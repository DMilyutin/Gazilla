package com.gazilla.mihail.gazillaj.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface StartAppInitializationView: MvpView {

    fun startMainActivity()
    fun startRegistrationActivity()

    fun showUpdateAppDialog()
    fun openPlayMarketForUpdate()

    fun dontUpdateApp()

    fun showMessageDialog(message: String)
    fun showMessageToast(message: String)

}