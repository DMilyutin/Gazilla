package com.gazilla.mihail.gazillaj.activites.Proms

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.getPromoOnId

class PromoTouchTimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_touch_time)

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)
    }
}
