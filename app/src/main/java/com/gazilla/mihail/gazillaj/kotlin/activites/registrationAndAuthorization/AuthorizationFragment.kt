package com.gazilla.mihail.gazillaj.kotlin.activites.registrationAndAuthorization

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter

import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.presenters.registaratiobAndAuthoriz.AuthorizationFragmentPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.registaratiobAndAuthoriz.AuthorizationFrView
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.android.synthetic.main.fragment_authorization.*

class AuthorizationFragment: MvpAppCompatFragment(), AuthorizationFrView {

    private lateinit var authorizationResult: AuthorizationResult

    @InjectPresenter
    lateinit var authorizationFragmentPresenter : AuthorizationFragmentPresenter

    @ProvidePresenter
    fun provideAuthorizationFragmentPresenter() : AuthorizationFragmentPresenter =
            AuthorizationFragmentPresenter(mContext.getSharedPreferences("myProf", Context.MODE_PRIVATE))

    private lateinit var mContext: Context

    private var appDialogs = AppDialogs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_authorization, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        btLoging.setOnClickListener {
            if (btLoging.text == "Получить код")
                authorizationFragmentPresenter.checkInputData(etUserDataRecoverDialog.text.toString())
            else
                authorizationFragmentPresenter.sendMyCodeForAuthorization(etCodeForLoginRecoverDialog.text.toString())
        }
    }

    override fun showInputFieldForCode() {
        textView42.visibility = View.VISIBLE
        etCodeForLoginRecoverDialog.visibility = View.VISIBLE
        btLoging.text = "Отправить код"
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

    override fun closeAuthorization(success: Boolean) {
        authorizationResult.resultAuthorization(success)
    }

    interface AuthorizationResult {
        fun resultAuthorization(success: Boolean)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        authorizationResult = context as AuthorizationResult
        mContext = context
    }


}