package com.gazilla.mihail.gazillaj.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface PromoCocktailView: MvpView {

    fun accessDeleteOldCoctail()
    fun showDialogAccessDeleteOldCoctail(message: String)

    fun setMyStars(stars: Int) // 20, 40, 60, 80, 100
    fun showMessage(message: String)

    fun accessSendCode()
}