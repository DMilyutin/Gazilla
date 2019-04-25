package com.gazilla.mihail.gazillaj.views.registaratiobAndAuthoriz

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface AuthorizationFrView :MvpView{

    fun showInputFieldForCode()

    fun showLoadDialog()
    fun closeLoadDialog()

    fun closeAuthorization(success: Boolean)

    fun showMessageDialog(message: String)

}