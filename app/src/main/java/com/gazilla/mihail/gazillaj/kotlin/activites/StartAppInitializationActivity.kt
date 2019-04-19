package com.gazilla.mihail.gazillaj.kotlin.activites

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gazilla.mihail.gazillaj.kotlin.activites.registrationAndAuthorization.RegAndAuthorizActivity
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.presenters.StartAppInitializationPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.StartAppInitializationView


class StartAppInitializationActivity : MvpAppCompatActivity(), StartAppInitializationView  {


    @InjectPresenter
    lateinit var startAppInitializationPresenter: StartAppInitializationPresenter

    @ProvidePresenter
    fun provideStartAppInitializationPresenter(): StartAppInitializationPresenter =
            StartAppInitializationPresenter(applicationContext.getSharedPreferences("myProf", Context.MODE_PRIVATE), isConnecting())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("Loog", "StartAppInitializationActivity - checkMyVersionApp")
        startAppInitializationPresenter.checkMyVersionApp()
    }

    override fun startMainActivity() {
        intent = Intent(this@StartAppInitializationActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun startRegistrationActivity() {
        intent = Intent(this@StartAppInitializationActivity, RegAndAuthorizActivity::class.java)
        startActivityForResult(intent, 5)
    }

    override fun showMessageDialog(message: String) {
        AppDialogs().messageDialog(this@StartAppInitializationActivity, message)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            val response = data!!.getBooleanExtra("REG", false)

            if (response){
                startAppInitializationPresenter.checkUserData()
                Log.i("Loog", "onActivityResult - $response")
            }

            else
                showMessageDialog("Ошибка регистрации\n" +
                        "Перезапустите приложение")

    }

    fun isConnecting(): Boolean{
        val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = cm.activeNetworkInfo
        return network.isConnected
    }

    override fun showUpdateAppDialog(){
        AppDialogs().dialogUpdateApp(this, "Необходимо обновить приложение до актуальной версии", this)
    }

    override fun openPlayMarketForUpdate(){
        val appPackageName = packageName
        startActivity( Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
    }

    override fun dontUpdateApp() {
       finish()
    }

}
