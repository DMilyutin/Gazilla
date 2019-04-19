package com.gazilla.mihail.gazillaj.kotlin.activites.Proms

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import kotlinx.android.synthetic.main.activity_stoak_play_station.*

class PromoPlayStationActivity : MvpAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stoak_play_station)

        val promo = intent.getParcelableExtra<PromoWithImg>("promo")

        imgPlayStationPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        tvNamePlayStationPromo.text = promo.name
        tvDescriptionPlayStationPromo.text = promo.description

        Log.i("Loog", "type - ${promo.promoType}")

    }
}