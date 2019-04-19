package com.gazilla.mihail.gazillaj.kotlin.views.registaratiobAndAuthoriz

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


interface RegistrationFrView : MvpView {

    fun showLoadDialog()
    fun closeLoadDialog()

    fun closeRegistration(success: Boolean)

    fun showMessageDialog(message: String)
}