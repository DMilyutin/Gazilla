package com.gazilla.mihail.gazillaj.kotlin.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ReserveView : MvpView {

    fun setUserInfo(name: String, phone: String)
    fun showClWithMyReserve(show: Boolean)

    fun setTimeReserve(time: String)
    fun setDateReserve(date: String)

    fun deleteMyReserve()

    fun showMessageDialog(message: String)
    fun showDeleteMyReserveDialog(message: String)

    fun showReserveSuccessDialog(message: String)
    fun closeReserveActivity()
}