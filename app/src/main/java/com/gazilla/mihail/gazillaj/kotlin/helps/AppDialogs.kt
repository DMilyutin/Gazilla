package com.gazilla.mihail.gazillaj.kotlin.helps

import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.pojo.Notification
import com.gazilla.mihail.gazillaj.kotlin.presenters.ProductPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.CardView
import com.gazilla.mihail.gazillaj.kotlin.views.MainView
import com.gazilla.mihail.gazillaj.kotlin.views.ReserveView
import com.gazilla.mihail.gazillaj.kotlin.views.StartAppInitializationView
import com.squareup.picasso.Picasso

class AppDialogs {
    
    private lateinit var alertDialog: AlertDialog
    
    fun closeDialog(){
        if (alertDialog.isShowing)
            alertDialog.dismiss()
    }

    fun loadingDialog(context: Context) {
        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_loading, null)

        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()
    }

    fun messageDialog(context: Context, error: String?) {

        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_error, null)

        val textView = viewDialog.findViewById<TextView>(R.id.tvErrorDialogError)
        textView.text = error

        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        builder.setNegativeButton("Закрыть") { _, _ ->
            alertDialog.dismiss()
        }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        val bt = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogWithQRCode(context: Context, bitmap: Bitmap, productPresenter: ProductPresenter){
        val infleter = LayoutInflater.from(context)
        val viewDialog = infleter.inflate(R.layout.dialod_with_qr_code, null)

        val imvQRCode = viewDialog.findViewById<ImageView>(R.id.imvQRcodeProductDialog)
        imvQRCode.setImageBitmap(bitmap)


        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        builder.setPositiveButton("Готово") { _ ,_ ->
            productPresenter.accessBuyProduct()
            alertDialog.dismiss()
        }.setNegativeButton("Отмена") { _, _ ->
            alertDialog.dismiss()
        }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.rgb(254, 194, 15))
        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogWinWheel(context: Context, res: Int, description: String){
        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_win_wheel, null)

        val imgItemWin = viewDialog.findViewById<ImageView>(R.id.imgWinDialog)
        val descriptionInfo = viewDialog.findViewById<TextView>(R.id.tvWinDialog)

        Picasso.with(context).load(res).into(imgItemWin)

        descriptionInfo.text = description

        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        builder.setPositiveButton("Спасибо!"){ _, _ ->
            alertDialog.dismiss()
        }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogDetailLvlDracon(context: Context, myLvlDiscription: String, one: String, two: String, fri: String, four: String, fif : String){
        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_detail_progress, null)

        viewDialog.findViewById<TextView>(R.id.tvLVLDetailProgress).text = myLvlDiscription
        viewDialog.findViewById<TextView>(R.id.tvOneDetailProgress).text = one
        viewDialog.findViewById<TextView>(R.id.tvTwoDetailProgress).text = two
        viewDialog.findViewById<TextView>(R.id.tvThreeDetailProgress).text = fri
        viewDialog.findViewById<TextView>(R.id.tvFourDetailProgress).text = four
        viewDialog.findViewById<TextView>(R.id.tvFifDetailProgress).text = fif


        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        builder.setPositiveButton("Понятно!"){ _, _ ->
            alertDialog.dismiss()
        }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogUpdateApp(context: Context, error: String?, startAppInitializationView : StartAppInitializationView) {

        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_error, null)

        val textView = viewDialog.findViewById<TextView>(R.id.tvErrorDialogError)
        textView.text = error

        val builder = AlertDialog.Builder(context, R.style.MyDialogTheme)
        .setPositiveButton("Обновить"){ _, _ ->
            startAppInitializationView.openPlayMarketForUpdate()
        }.setNegativeButton("Закрыть"){ _, _ ->
                    startAppInitializationView.dontUpdateApp()
                }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.setCancelable(false)

        val bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
        val bt2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt2.setTextColor(Color.rgb(254, 194, 15))

    }

    fun dialogOpenReserve(context: Context, error: String?, cardView: CardView){
        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_error, null)

        val textView = viewDialog.findViewById<TextView>(R.id.tvErrorDialogError)
        textView.text = error

        val builder = AlertDialog.Builder(context, R.style.StackedAlertDialogStyle)
                .setNegativeButton("Закрыть"){ _, _ ->
                    alertDialog.dismiss()
                }
                .setPositiveButton("Забронировать стол"){ _, _ ->
                    cardView.openReserveActivity()
                }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.setCancelable(false)

        val bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
        val bt2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt2.setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogReserveSuccess(context: Context, message: String, reserveView: ReserveView){
        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_error, null)

        val textView = viewDialog.findViewById<TextView>(R.id.tvErrorDialogError)
        textView.text = message

        val builder = AlertDialog.Builder(context, R.style.StackedAlertDialogStyle)
                .setNegativeButton("Спасибо"){ _, _ ->
                    alertDialog.dismiss()
                    reserveView.closeReserveActivity()
                }


        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.setCancelable(false)

        val bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
        val bt2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt2.setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogDetailMyReserve(context: Context, message: String, reserveView: ReserveView ){
        val inflater = LayoutInflater.from(context)
        val viewDialog = inflater.inflate(R.layout.dialog_error, null)

        val textView = viewDialog.findViewById<TextView>(R.id.tvErrorDialogError)
        textView.text = message

        val builder = AlertDialog.Builder(context, R.style.StackedAlertDialogStyle)
                .setNegativeButton("Удалить"){ _, _ ->
                    reserveView.deleteMyReserve()
                    alertDialog.dismiss()
                }.setPositiveButton("Спасибо"){  _, _ ->
                    alertDialog.dismiss()
                }

        builder.setView(viewDialog)
        alertDialog = builder.create()
        alertDialog.show()

        val bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
        val bt2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt2.setTextColor(Color.rgb(254, 194, 15))
    }

    fun dialogNotification(context: Context, notification: Notification, mainView: MainView, bitmap: Bitmap?) {
        val inflater = LayoutInflater.from(context)
        val dialog = inflater.inflate(R.layout.dialog_notification, null)

        val tvMessNotification = dialog.findViewById<TextView>(R.id.tvMessNotification)
        tvMessNotification.text = notification.message
        val imgNotification = dialog.findViewById<ImageView>(R.id.imgNotification)

        if (bitmap != null)
            imgNotification.setImageBitmap(bitmap)
        else
            imgNotification.visibility = View.GONE

        val builder = AlertDialog.Builder(context, R.style.StackedAlertDialogStyle)
        builder.setNegativeButton("Пропустить") { _, _ ->
            mainView.sendAnswerNotification(notification.id, -1)
            alertDialog.dismiss()
        }

        if (notification.promoId != 0) {
            builder.setPositiveButton("Акции") { _, _ ->
                mainView.openMenuFragmentBeforeNotification("StocksFragment")
            }
        }else{
            when{
                notification.commands[0] == 1 || notification.commands[0] == 0-> {
                    builder.setPositiveButton("Меню") { _, _ ->
                        mainView.openMenuFragmentBeforeNotification("ProductsMenuFragment")
                        mainView.sendAnswerNotification(notification.id, 0)
                    }
                }
                notification.commands[0] == 2 -> {
                    builder.setPositiveButton("Забронировать") { _, _ ->
                        mainView.startReserveActivity()
                        mainView.sendAnswerNotification(notification.id, 2)
                    }
                }
                notification.commands[0] == 3 -> {
                    builder.setPositiveButton("К колесу") { _, _ ->
                        mainView.sendAnswerNotification(notification.id, 3)
                    }
                }
            }
        }

        builder.setView(dialog)
        alertDialog = builder.create()
        alertDialog.show()

        alertDialog.setCanceledOnTouchOutside(false)

        val bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        bt.setTextColor(Color.rgb(254, 194, 15))
        val bt1 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        bt1.setTextColor(Color.rgb(254, 194, 15))
    }
    
}