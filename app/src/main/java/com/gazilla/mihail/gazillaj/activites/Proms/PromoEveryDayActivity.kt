package com.gazilla.mihail.gazillaj.activites.Proms

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.getPromoOnId
import kotlinx.android.synthetic.main.activity_promo_every_day.*

class PromoEveryDayActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_every_day)

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)

        imgEveryDayPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
    }
}
