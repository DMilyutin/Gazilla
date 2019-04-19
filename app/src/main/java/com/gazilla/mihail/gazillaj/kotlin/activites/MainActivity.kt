package com.gazilla.mihail.gazillaj.kotlin.activites

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.pojo.Notification
import com.gazilla.mihail.gazillaj.kotlin.presenters.MainPresenter
import com.gazilla.mihail.gazillaj.kotlin.presenters.NotificationPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : MvpAppCompatActivity(), MainView, CardFragment.UpdateBalance {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private lateinit var fTrans: FragmentTransaction
    private lateinit var notificationPresenter: NotificationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationPresenter = NotificationPresenter(applicationContext.getSharedPreferences("myProf", Context.MODE_PRIVATE), this)

        imgAccount.setOnClickListener {
            intent = Intent(this@MainActivity, AccountActivity::class.java)
            startActivity(intent)
        }

        Log.i("Loog", "маин активити создана")

        bottomNavMainMenu.setOnNavigationItemSelectedListener {
           when (it.itemId){
               R.id.menu_card -> mainPresenter.openCardFragment()
               R.id.menu_product-> mainPresenter.openProductFragment()
               R.id.menu_stock->mainPresenter.openStockFragment()
               else -> {
                   mainPresenter.openContactsFragment()
               }

           }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun setScoreInfo(score: String) {
        tvScoreMainActivity.text = score
    }

    override fun addFirstFragment(fragment: Fragment) {
        fTrans = supportFragmentManager.beginTransaction()
        fTrans.add(R.id.fragmentLayoutMainMenu, fragment)
        fTrans.commit()


    }

    override fun replaceFragment(fragment: Fragment, nameMenu: String) {
        fTrans = supportFragmentManager.beginTransaction()
        fTrans.replace(R.id.fragmentLayoutMainMenu, fragment)
        fTrans.commit()

        tvNameFragment.text = nameMenu
    }

    override fun showImgOpenAccount(show: Boolean) {
        if (show)
            imgAccount.visibility = View.VISIBLE
        else
            imgAccount.visibility = View.GONE
    }

    override fun updateMyBalance() {
        mainPresenter.updateScore()
    }

    override fun showNotification(notification: Notification, bitmap: Bitmap?) {
        AppDialogs().dialogNotification(this, notification, this, bitmap)
    }

    override fun startReserveActivity() {
        val intent = Intent(this, ReserveActivity::class.java)
        startActivity(intent)
    }

    override fun sendAnswerNotification(alertId: Int, commandId: Int) {
        notificationPresenter.sendAnswerNotification(alertId, commandId)
    }

    override fun openMenuFragmentBeforeNotification(nameFragment: String) {
       mainPresenter.openFragmentBeforNotificatoin(nameFragment)
    }


}
