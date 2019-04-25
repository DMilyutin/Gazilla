package com.gazilla.mihail.gazillaj.activites.Proms

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.AppDialogs
import com.gazilla.mihail.gazillaj.helps.QRCode.QRCode
import com.gazilla.mihail.gazillaj.helps.getPromoOnId
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.SmokerpassCallBack
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.PromoSmokerpass

import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.activity_promo_smokerpass.*
import java.text.ParseException
import java.text.SimpleDateFormat
import javax.inject.Inject

class PromoSmokerpassActivity: MvpAppCompatActivity() {

    private lateinit var dialogWithQRcode: AlertDialog

    @Inject
    lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_promo_smokerpass)

        val promoId = intent.getIntExtra("id promo", 0)

        val promo = getPromoOnId(promoId)

        imgSmokerpassPromo.setImageBitmap(BitmapFactory.decodeByteArray(promo.img, 0, promo.img.size))
        tvNameSmokerpassPromo.text = promo.name
        tvDescriptionSmokerpassPromo.text = promo.description


        val appDialogs = AppDialogs()

        btByuSmokerpass.setOnClickListener {
            val txt = btByuSmokerpass.text.toString()
            if (txt == "Предъявить") {
                dialogWithQRCode()
            } else
                appDialogs.messageDialog(this, "Для того чтобы приобрести дымный абонемент, обратитесь пожалуйста к нашему сотруднику")
        }
        mySmokerpass()
    }

    private fun mySmokerpass() {
        repositoryApi.smokerpassing(App.userWithKeys.publickey,
                signatur(App.userWithKeys.privatekey, ""),
                object : SmokerpassCallBack{
                    override fun mySmokerpass(promoSmokerpass: PromoSmokerpass?) {
                        val ex = promoSmokerpass!!.expire
                        if (ex != "") {
                            btByuSmokerpass.text = "Предъявить"
                            setInfo(ex)
                        } else {
                            tvDateStockSmokepass.visibility = View.GONE
                            tvHelpSmokePress.visibility = View.GONE
                            btByuSmokerpass.text = "Приобрести"
                        }
                    }
                    override fun errorCallBack(error: String) {} },
                object : FailCallBack{ override fun throwableCallBack(throwable: Throwable) {} })
    }

    private fun setInfo(expire: String) {
        @SuppressLint("SimpleDateFormat") val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
        try {
            val date = formatter.parse(expire)
            @SuppressLint("SimpleDateFormat") val ex = SimpleDateFormat("dd MMMM yyyyг.").format(date)
            tvDateStockSmokepass.visibility = View.VISIBLE
            tvHelpSmokePress.visibility = View.VISIBLE
            tvDateStockSmokepass.text = ex
        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    private fun dialogWithQRCode() {

        val dataForQrCode = App.userWithKeys.id.toString()
        val bitmap = QRCode().encodeAsBitmap(dataForQrCode, BarcodeFormat.QR_CODE, 200, 200)

        val dialog = layoutInflater.inflate(R.layout.dialod_with_qr_code, null)
        dialog.findViewById<ImageView>(R.id.imvQRcodeProductDialog).setImageBitmap(bitmap)


        dialog.findViewById<TextView>(R.id.tvTxtDialogByFirst).text = "Дождитесь, пока сотрудник\n" + "считает Ваш QR-код"

        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setPositiveButton("Готово") { _, _ -> dialogWithQRcode.dismiss() }
                .setNegativeButton("Отмена") { _, _ -> dialogWithQRcode.dismiss() }

        builder.setView(dialog)
        dialogWithQRcode = builder.create()
        dialogWithQRcode.show()

        val bt = dialogWithQRcode.getButton(DialogInterface.BUTTON_POSITIVE)
        val bt1 = dialogWithQRcode.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
        bt1.setTextColor(Color.rgb(254, 194, 15))

    }
}

