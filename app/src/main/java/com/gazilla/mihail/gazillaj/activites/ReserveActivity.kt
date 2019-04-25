package com.gazilla.mihail.gazillaj.activites


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.AppDialogs
import com.gazilla.mihail.gazillaj.presenters.ReservePresenter
import com.gazilla.mihail.gazillaj.views.ReserveView
import kotlinx.android.synthetic.main.activity_reserve.*
import java.util.*

class ReserveActivity : MvpAppCompatActivity(), ReserveView {


    @InjectPresenter
    lateinit var reservePresenter: ReservePresenter

    @ProvidePresenter
    fun provideReservePresentation(): ReservePresenter
            = ReservePresenter(applicationContext.getSharedPreferences("myProf", Context.MODE_PRIVATE))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve)

        reservePresenter.checkUserData()

        val c = Calendar.getInstance()

        val year1 = c.get(Calendar.YEAR)
        val monthOfYear1 = c.get(Calendar.MONTH)
        val dayOfMonth1 = c.get(Calendar.DAY_OF_MONTH)

        val hourOfDay = c.get(Calendar.HOUR_OF_DAY)
        val minuteOfHour = c.get(Calendar.MINUTE)

        tvDateReserve.setOnClickListener {
            val dpd = DatePickerDialog(this, R.style.TimePike, DatePickerDialog.OnDateSetListener{_, year, month, dayOfMonth ->
                reservePresenter.checkSpecifiedDate(year, month,dayOfMonth)
            }, year1, monthOfYear1, dayOfMonth1)
            dpd.show()
        }

        tvTimeReserve.setOnClickListener {
            val tpd = TimePickerDialog(this,R.style.TimePike, TimePickerDialog.OnTimeSetListener{_, hourOfDay, minute ->
                reservePresenter.checkSpecifiedTime(hourOfDay, minute)
            }, hourOfDay, minuteOfHour, true)
            tpd.show()
        }


        btNewReserve.setOnClickListener {
            if (edPeoplesReserve.text.toString()!=""){

            reservePresenter.getDataAboutReserve(
                    etNameReserve.text.toString(),
                    edPhoneReserve.text.toString(),
                    edPeoplesReserve.text.toString().toInt(),
                    tvDateReserve.text.toString()+ tvTimeReserve.text.toString(),
                    cbPreorderReserve.isChecked,
                    etCommentForReserve.text.toString()
            )}

            else
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
        }

        cl_reserve_my_reserve.setOnClickListener {
            reservePresenter.showMyReserve()
        }
    }

    override fun setUserInfo(name: String, phone: String) {
        etNameReserve.setText(name)
        edPhoneReserve.setText(phone)
    }

    override fun showClWithMyReserve(show: Boolean) {
        if (show)
            cl_reserve_my_reserve.visibility = View.VISIBLE
        else
            cl_reserve_my_reserve.visibility = View.GONE
    }

    override fun showDeleteMyReserveDialog(message: String) {
        AppDialogs().dialogDetailMyReserve(this, message, this)
    }

    override fun deleteMyReserve() {
       reservePresenter.delMyReserve()
    }

    override fun setDateReserve(date: String) {
        tvDateReserve.text = date
    }

    override fun setTimeReserve(time: String) {
        tvTimeReserve.text = time
    }

    override fun showMessageDialog(message: String) {
        AppDialogs().messageDialog(this, message)
    }

    override fun showReserveSuccessDialog(message: String){
        AppDialogs().dialogReserveSuccess(this, message, this)
    }

    override fun closeReserveActivity() {
        finish()
    }

}
