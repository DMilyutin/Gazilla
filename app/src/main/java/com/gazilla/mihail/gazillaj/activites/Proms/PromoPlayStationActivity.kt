package com.gazilla.mihail.gazillaj.activites.Proms

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.getPromoOnId
import kotlinx.android.synthetic.main.activity_promo_play_station.*

class PromoPlayStationActivity : MvpAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_play_station)

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)

        imgPlayStationPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        tvNamePlayStationPromo.text = promo.name
        tvDescriptionPlayStationPromo.text = promo.description

        Log.i("Loog", "type - ${promo.promoType}")

    }
}