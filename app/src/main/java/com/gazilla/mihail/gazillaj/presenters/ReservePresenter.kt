package com.gazilla.mihail.gazillaj.presenters

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.helps.checkFormatPhone
import com.gazilla.mihail.gazillaj.pojo.Reserve
import com.gazilla.mihail.gazillaj.providers.ReserveProvider
import com.gazilla.mihail.gazillaj.views.ReserveView
import java.text.SimpleDateFormat
import java.util.*

@InjectViewState
class ReservePresenter(private val sharedPreferences: SharedPreferences) : MvpPresenter<ReserveView>() {

    private val reserveProvider = ReserveProvider(this)
    private var dateAndTimeReserve : Calendar = Calendar.getInstance()

    private lateinit var dateForActivity: String
    private lateinit var timeForActivity: String

    private lateinit var lastReserve: Reserve

    init {
       checkMyReserve()
    }

    fun checkUserData(){
        val name = sharedPreferences.getString("myName", "")
        val phone = sharedPreferences.getString("myPhone", "")
        val s = checkFormatPhone(phone)
        viewState.setUserInfo(name!!, s)
    }

    fun checkMyReserve(){
        reserveProvider.myReserve()
    }

    fun myReserves(reserves: List<Reserve>){
        if (reserves.isNotEmpty()){
            viewState.showClWithMyReserve(true)
            lastReserve = reserves[reserves.size-1]
        }
        else
            viewState.showClWithMyReserve(false)
    }

    @SuppressLint("SimpleDateFormat")
    fun showMyReserve(){
        if(lastReserve!=null){
            val qty = when {
                lastReserve.qty == 1 -> "место"
                lastReserve.qty in 2 .. 4 -> "места"
                else -> "мест"
            }
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US)
            val date = dateFormat.parse(lastReserve.startDate)
            val dateForMess = SimpleDateFormat("dd-MM-yyyy HH:mm").format(date)

            //val message = "Вы забронировали столик ${lastReserve.startDate} на ${lastReserve.qty} $qty"
            val message = "Вы забронировали столик $dateForMess на ${lastReserve.qty} $qty"
            viewState.showDeleteMyReserveDialog(message)
        }

    }

    fun delMyReserve(){
        reserveProvider.delReserve(lastReserve.id)
    }


    @SuppressLint("SimpleDateFormat")
    fun checkSpecifiedDate(year: Int, month: Int, day: Int){
        val c = Calendar.getInstance()
        c.set(Calendar.YEAR, year)
        c.set(Calendar.MONTH, month)
        c.set(Calendar.DAY_OF_MONTH, day)

        val specifiedDay = c.get(Calendar.DAY_OF_YEAR)

        if (specifiedDay < Calendar.getInstance().get(Calendar.DAY_OF_YEAR)){
            viewState.showMessageDialog("Дата указана неверно")
            return
        }

        dateAndTimeReserve.set(Calendar.YEAR, year)
        dateAndTimeReserve.set(Calendar.MONTH, month)
        dateAndTimeReserve.set(Calendar.DAY_OF_MONTH, day)

        dateForActivity = SimpleDateFormat("dd-MM-yyyy").format(dateAndTimeReserve.timeInMillis)
        viewState.setDateReserve(dateForActivity)
    }

    @SuppressLint("SimpleDateFormat")
    fun checkSpecifiedTime(hours: Int, minute: Int){
        if (hours in 0..11){
            viewState.showMessageDialog("В это время бронирование столов невозможно")
            return
        }
        val roundingMinute = roundingMinute(minute)
        dateAndTimeReserve.set(Calendar.HOUR_OF_DAY, hours)
        dateAndTimeReserve.set(Calendar.MINUTE, roundingMinute)

        timeForActivity = SimpleDateFormat("HH:mm").format(dateAndTimeReserve.timeInMillis)
        viewState.setTimeReserve(timeForActivity)
    }

    private fun roundingMinute(minute: Int) : Int{
        if (minute in 53..60|| minute in 0..8) return 0
        if (minute in 8..22) return 15
        if (minute in 23..37) return 30
        if (minute in 38..52) return 45
        return  minute
    }


    @SuppressLint("SimpleDateFormat")
    fun getDataAboutReserve(name: String, phone: String, qty: Int, data: String,
                            preorder: Boolean, comment: String ){

        val phoneFormat = checkFormatPhone(phone)

        if (name == "" || phoneFormat =="" || qty==0 || data ==""){
            viewState.showMessageDialog("Неверно заполнены поля")
            return
        }

        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZZZZZ").format(dateAndTimeReserve.timeInMillis)
        reserveProvider.sendNewReserve(Reserve(name, phone, qty, date, 4, comment), preorder)
    }

    fun responseReserve(isSuccess : Boolean){
        if (isSuccess){
            val mess = "Стол успешно забронирован на $dateForActivity в $timeForActivity!\nЖдем Вас в назначенное время"
            viewState.showReserveSuccessDialog(mess)
        }
        else
            viewState.showMessageDialog("Произошло недорозумение :(\\nПозвоните нам пожалуйста")
    }


    fun showErrorMessage(error: String){
        viewState.showMessageDialog(error)
    }

}