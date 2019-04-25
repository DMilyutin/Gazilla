package com.gazilla.mihail.gazillaj.activites

import android.graphics.Bitmap
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.AppDialogs
import com.gazilla.mihail.gazillaj.pojo.MenuItem
import com.gazilla.mihail.gazillaj.presenters.ProductPresenter
import com.gazilla.mihail.gazillaj.views.ProductView
import kotlinx.android.synthetic.main.activity_product.*

class ProductActivity : MvpAppCompatActivity(), ProductView {

    @InjectPresenter
    lateinit var productPresenter: ProductPresenter

    lateinit var type: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val menuItem = intent.getParcelableExtra<MenuItem>("SelectedItem")
        type = intent.getStringExtra("Type")

        productPresenter.initItem(menuItem = menuItem, type = type)

        btBuyProductItem.setOnClickListener {
            productPresenter.buyProductItem()
        }
    }


    override fun initProductItem(name: String, description: String, weight: String, coast: String) {
        tvNameProductItem.text = name
        tvDescriptionProductItem.text = description
        tvWeightProductItem.text = weight
        tvCoastProductItem.text = coast
    }

    override fun setImgItem(bitmap: Bitmap?) {
        if (bitmap!=null)
            imgProductItem.setImageBitmap(bitmap)
        else
            imgProductItem.setImageResource(R.drawable.gaz)
    }

    override fun showDialogWithQRCode(bitmap: Bitmap) {
        AppDialogs().dialogWithQRCode(this, bitmap, productPresenter)
    }


    override fun showMessageDialog(message: String) {
        AppDialogs().messageDialog(this, message)
    }


}
