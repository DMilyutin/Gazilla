package com.gazilla.mihail.gazillaj.views

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ProductView : MvpView{

    fun initProductItem(name: String, description: String, weight:String, coast: String)
    fun setImgItem(bitmap: Bitmap?)

    fun showDialogWithQRCode(bitmap: Bitmap)

    fun showMessageDialog(message: String)
}