package com.gazilla.mihail.gazillaj.kotlin.activites.Proms

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import kotlinx.android.synthetic.main.activity_stock_kitchen.*


class PromoKitchenActivity: MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_kitchen)

        val promo = intent.getParcelableExtra<PromoWithImg>("promo")

        imgKitchenPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        tvNameKitchenPromo.text = promo.name
        tvDescriptionKitchenPromo.text = promo.description

        Log.i("Loog", "PromoKitchenActivity tvDescriptionKitchenPromo - ${promo.shortDescription}")

    }
}