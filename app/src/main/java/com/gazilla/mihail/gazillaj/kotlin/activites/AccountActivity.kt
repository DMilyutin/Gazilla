package com.gazilla.mihail.gazillaj.kotlin.activites


import android.content.Context
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.AuthorizationCallBack
import com.gazilla.mihail.gazillaj.kotlin.pojo.LatestVersion
import com.gazilla.mihail.gazillaj.kotlin.pojo.UserWithKeys
import com.gazilla.mihail.gazillaj.kotlin.presenters.AccountPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.AccountView
import kotlinx.android.synthetic.main.activity_account.*

class AccountActivity : MvpAppCompatActivity(), AccountView {


    @InjectPresenter
    lateinit var accountPresenter: AccountPresenter


    @ProvidePresenter
    fun provideAccountPresentation(): AccountPresenter{
        return AccountPresenter(applicationContext.getSharedPreferences("myProf", Context.MODE_PRIVATE))
    }

    private var appDialogs = AppDialogs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        accountPresenter.getUserInfo()

        btSaveAccount.setOnClickListener {
            accountPresenter.saveNewUserInfo( etNameAccount.text.toString(), etPhoneAccount.text.toString(), etEmailAccount.text.toString())
        }

    }

    override fun setUserId(id: String?) {
        tvMyIdAccount.text = id
    }

    override fun setUserInfo(name: String?, phone: String?, email: String?) {
        etNameAccount.setText(name)
        etPhoneAccount.setText(phone)
        etEmailAccount.setText(email)
    }


    override fun showLoadDialog() {
       appDialogs.loadingDialog(this)
    }

    override fun showMessageDialog(message: String?) {
        appDialogs.messageDialog(this, message)
    }

    override fun closeLoadDialog() {
       appDialogs.closeDialog()
    }

}
