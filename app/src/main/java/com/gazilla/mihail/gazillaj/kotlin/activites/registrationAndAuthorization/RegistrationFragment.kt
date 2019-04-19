package com.gazilla.mihail.gazillaj.kotlin.activites.registrationAndAuthorization

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.presenters.registaratiobAndAuthoriz.RegistrationFragmentPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.registaratiobAndAuthoriz.RegistrationFrView
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment: MvpAppCompatFragment(), RegistrationFrView {

    private lateinit var regAndAuthorizResult : RegistrationResult

    private lateinit var mContext : Context
    private val appDialogs = AppDialogs()

    @InjectPresenter
    lateinit var registrationFragmentPresenter: RegistrationFragmentPresenter

    @ProvidePresenter
    fun provideRegistrationFragmentPresenter(): RegistrationFragmentPresenter =
            RegistrationFragmentPresenter(mContext.getSharedPreferences("myProf", Context.MODE_PRIVATE))


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registration, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvDontHavePromo.setOnClickListener {
            registrationFragmentPresenter.getNewUserInfo(false, "", App.myId)
        }

        btRegWithPromo.setOnClickListener {
            registrationFragmentPresenter.getNewUserInfo(true, etPromoReg.text.toString(), App.myId)

        }
    }

    override fun showLoadDialog() {
        appDialogs.loadingDialog(mContext)
    }

    override fun closeLoadDialog() {
       appDialogs.closeDialog()
    }

    override fun showMessageDialog(message: String) {
        appDialogs.messageDialog(mContext, message)
    }

    override fun closeRegistration(success: Boolean) {
        Log.i("Loog","closeRegistration")
        regAndAuthorizResult.resultRegistration(success)
    }

    interface RegistrationResult {
        fun resultRegistration(success: Boolean)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        regAndAuthorizResult = context as RegistrationResult
        mContext = context
    }

}

