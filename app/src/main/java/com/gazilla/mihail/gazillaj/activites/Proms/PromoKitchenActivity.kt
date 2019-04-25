package com.gazilla.mihail.gazillaj.activites.Proms

import android.graphics.BitmapFactory
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.getPromoOnId
import kotlinx.android.synthetic.main.activity_promo_kitchen.*


class PromoKitchenActivity: MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_kitchen)

        //val promo = intent.getParcelableExtra<PromoWithImg>("promo")

        //imgKitchenPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        //imgKitchenPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)

        imgKitchenPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        tvNameKitchenPromo.text = promo.name
        tvDescriptionKitchenPromo.text = promo.description

    }
}