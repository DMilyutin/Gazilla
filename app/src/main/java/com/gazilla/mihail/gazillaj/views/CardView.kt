package com.gazilla.mihail.gazillaj.views

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface CardView:MvpView {

    fun setValueProgressBar(maxValue: Int, userValue: Int)
    fun setQrCode(bitmap: Bitmap)
    fun setSpins(qty: Int)
    fun showWhiteRoundDrakonTip(show: Boolean)
    fun setLvlUserDraconAdapter(levels: Map<Int, Int>)

    fun initWheelLvl(res: Int)

    fun startWheeling()
    fun showMyWin(res: Int, description: String)
    fun showDetailLvlDracon(myLvlDiscription: String, one: String, two: String, fri: String, four: String, fif : String)

    fun updateBalance()

    fun openReserveActivity()

    fun openMessageWithReserve(message: String)
    fun showMessageDialog(message: String)
}