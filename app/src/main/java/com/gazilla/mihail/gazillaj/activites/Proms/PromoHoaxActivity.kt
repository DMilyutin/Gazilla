package com.gazilla.mihail.gazillaj.activites.Proms

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log


import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.activites.ReserveActivity
import com.gazilla.mihail.gazillaj.helps.getPromoOnId

import kotlinx.android.synthetic.main.activity_promo_hoax.*
import java.util.*

class PromoHoaxActivity: MvpAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_hoax)
        t()

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)

        tvNameHuaxPromo.text = promo.name
        tvDescriptionHuaxPromo.text = promo.description
        imgHuaxPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))


        btReserveFromHuax.setOnClickListener {
            val intent = Intent(this, ReserveActivity::class.java)
            startActivity(intent)
        }
    }


    private fun t() {
        val calendarFriday = Calendar.getInstance()
        calendarFriday.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
        calendarFriday.set(Calendar.HOUR_OF_DAY, 20)
        calendarFriday.set(Calendar.MINUTE, 0)
        calendarFriday.set(Calendar.SECOND, 0)

        val calendarNow = Calendar.getInstance()

        var dFraiday = calendarFriday.timeInMillis
        val dNow = calendarNow.timeInMillis

        if (dFraiday > dNow) {
            var dayY = dFraiday - dNow
            val day = dayY / 86400000
            dayY -= day * 86400000
            val hh = dayY / 3600000
            dayY -= hh * 3600000
            val mm = dayY / 60000
            Log.i("Loog", "DD: $day HH: $hh mm: $mm")

            tvDaysStockHoax.text = day.toString()
            tvHourStockHoax.text = hh.toString()
            tvMinStockHoax.text = mm.toString()
        } else {
            val weekNow = calendarNow.get(Calendar.WEEK_OF_MONTH)
            calendarFriday.set(Calendar.WEEK_OF_MONTH, weekNow + 1)
            dFraiday = calendarFriday.timeInMillis
            var dayY = dFraiday - dNow
            var z = dayY

            val day = dayY / 86400000  // нашли дни
            z -= day * 86400000
            dayY %= 86400000 // остаток от дней( часы)
            val hh = dayY / 3600000 // нашли кол-во часов
            z -= hh * 3600000
            Log.i("Loog", "z: $z")
            val mm = z / 60000
            Log.i("Loog", "DD: $day HH: $hh mm: $mm")
            //String dat = day+" : "+hh+" : "+mm;
            tvDaysStockHoax.text = day.toString()
            tvHourStockHoax.text = hh.toString()
            tvMinStockHoax.text = mm.toString()
        }

    }
}