package com.gazilla.mihail.gazillaj.kotlin.activites.registrationAndAuthorization

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.presenters.registaratiobAndAuthoriz.RegAndAuthorizActivityPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.registaratiobAndAuthoriz.RegAndAuthorizView
import kotlinx.android.synthetic.main.activity_reg_and_authoriz.*

class RegAndAuthorizActivity : MvpAppCompatActivity(), RegAndAuthorizView, RegistrationFragment.RegistrationResult,
        AuthorizationFragment.AuthorizationResult {

    @InjectPresenter
    lateinit var regAndAuthorizActivityPresenter : RegAndAuthorizActivityPresenter

    private lateinit var fTrans: FragmentTransaction

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg_and_authoriz)

        tvSwitchRegAndAuthorizFragment.text = "Воостановить баллы"

        tvSwitchRegAndAuthorizFragment.setOnClickListener {
            regAndAuthorizActivityPresenter.pressReplaseFragment(tvSwitchRegAndAuthorizFragment.text.toString())
        }
    }

    override fun initFragment(firstFragment: Fragment) {
        fTrans = supportFragmentManager.beginTransaction()
        fTrans.add(R.id.fragmentLayoutRegAndAuthoriz, firstFragment)
        fTrans.commit()
    }

    override fun replaceFragment(fragment: Fragment, newTextForBt: String) {
        fTrans = supportFragmentManager.beginTransaction()
        fTrans.replace(R.id.fragmentLayoutRegAndAuthoriz, fragment)
        fTrans.commit()

        tvSwitchRegAndAuthorizFragment.text=newTextForBt
    }

    override fun resultRegistration(success: Boolean) {
        Log.i("Loog","resultRegistration")
        val intent = Intent().putExtra("REG", success)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun resultAuthorization(success: Boolean) {
        val intent = Intent().putExtra("REG", success)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }


}

