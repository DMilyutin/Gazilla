package com.gazilla.mihail.gazillaj.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface AccountView : MvpView {

    fun setUserId(id: String?)
    fun setUserInfo(name: String?, phone: String?, email:String?)

    fun showLoadDialog()

    fun closeLoadDialog()
    fun showMessageDialog(message : String?)


}