package com.gazilla.mihail.gazillaj.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gazilla.mihail.gazillaj.pojo.SmartMenuItem

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ProductsFreeFragmentView: MvpView {

    fun setGiftsAdapter(menuItemList: List<SmartMenuItem>)
    fun showMessageDialog(message: String)
}