package com.gazilla.mihail.gazillaj.kotlin.activites.Proms

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle

import android.widget.ImageView

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import kotlinx.android.synthetic.main.activity_stock_new_friend.*

class PromoNewFriendActivity : MvpAppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_new_friend)

        val promo = intent.getParcelableExtra<PromoWithImg>("promo")

        imgNewFriendStock.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        imgNewFriendStock.scaleType = ImageView.ScaleType.CENTER_CROP

        tvRefererNewFriend.text = App.userWithKeys.refererLink
        tvDescriptionNewFriendPromo.text = promo.description
        tvNameNewFriendPromo.text = promo.name


        imgCopyReferLink!!.setOnClickListener {
            val clipboardManager = this.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("", tvRefererNewFriend!!.text.toString())
            clipboardManager.primaryClip = clipData
            Toast.makeText(this, "Код скоприрован", Toast.LENGTH_SHORT).show()
        }

        btSendPril!!.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "M.gazilla-lounge.ru \nПромокод: " + tvRefererNewFriend!!.text.toString())
            sendIntent.type = "text/plain"
            startActivity(Intent.createChooser(sendIntent, "Поделиться"))
        }
    }
}