package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.graphics.Bitmap
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.QRCode.QRCode
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuItem
import com.gazilla.mihail.gazillaj.kotlin.providers.ProductProvider
import com.gazilla.mihail.gazillaj.kotlin.views.ProductView
import com.google.zxing.BarcodeFormat

@InjectViewState
class ProductPresenter: MvpPresenter<ProductView>(){

    private val productProvider = ProductProvider(this)

    private lateinit var menuItem: MenuItem
    private lateinit var productType: String

    fun initItem(menuItem: MenuItem, type: String){
        this.menuItem = menuItem
        productType = type

        val price = if (productType!="free")
            menuItem.price.toString()
        else
            "0"

        viewState.initProductItem(menuItem.name,
                menuItem.description, menuItem.weight, price)

        productProvider.getItemImageFromServer(menuItem.id.toString())
    }

    fun setItemImg(bitmap: Bitmap){
        viewState.setImgItem(bitmap)
    }

    fun buyProductItem(){

        val dataForQRCode = if (productType!="free")
            "${App.userWithKeys.id}/${menuItem.id}"
        else
            "${App.userWithKeys.id}/${menuItem.id}/free"

        Log.i("Loog", "buyProductItem  -  $dataForQRCode")

        val bitmap = QRCode().encodeAsBitmap(dataForQRCode, BarcodeFormat.QR_CODE, 200, 200)
        if (bitmap!=null)
            viewState.showDialogWithQRCode(bitmap)
        else
            viewState.showMessageDialog("Ошибка формирования QR кода")
    }

    fun accessBuyProduct(){
        val textAccess  = if (productType!="free")
            "С Вашего счета будет списано ${menuItem.price} баллов после завершения операции.\nПриятного вечера!"
        else
            "Подарок будет списан после завершения операции.\nПриятного вечера!"

        viewState.showMessageDialog(message = textAccess)
    }

    fun showErrorMessage(error: String) {
        viewState.showMessageDialog(error)
    }
}