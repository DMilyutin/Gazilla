package com.gazilla.mihail.gazillaj.activites.Proms

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.getPromoOnId
import kotlinx.android.synthetic.main.activity_promo_base.*

class PromoBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_base)

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)

        Log.i("Loog", "id promo - $promoId")

        imgBasePromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        tvNameBasePromo.text = promo.name
        tvDescriptionBasePromo.text = promo.description

        Toast.makeText(this, "Необходимо обновить приложение для корректного отображения акции", Toast.LENGTH_LONG).show()
    }
}
