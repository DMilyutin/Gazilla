package com.gazilla.mihail.gazillaj.activites

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.gazilla.mihail.gazillaj.activites.registrationAndAuthorization.RegAndAuthorizActivity
import com.gazilla.mihail.gazillaj.helps.AppDialogs
import com.gazilla.mihail.gazillaj.presenters.StartAppInitializationPresenter
import com.gazilla.mihail.gazillaj.views.StartAppInitializationView
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException


class StartAppInitializationActivity : MvpAppCompatActivity(), StartAppInitializationView  {


    @InjectPresenter
    lateinit var startAppInitializationPresenter: StartAppInitializationPresenter

    @ProvidePresenter
    fun provideStartAppInitializationPresenter(): StartAppInitializationPresenter =
            StartAppInitializationPresenter(applicationContext.getSharedPreferences("myProf", Context.MODE_PRIVATE))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("Loog", "StartAppInitializationActivity - checkMyVersionApp")
        val isConnect : Boolean = try {
            isConnecting()
        }catch (e: IllegalStateException){
            false
        }catch (e: IllegalArgumentException){
            false
        }

        if(isConnect)
            startAppInitializationPresenter.checkMyVersionApp()
        else
            showMessageDialog("Для запуска необходим доступ в интернет")
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

        if (data!=null){
            val response = data.getBooleanExtra("REG", false)

            if (response){
                startAppInitializationPresenter.checkUserData()
                Log.i("Loog", "onActivityResult - $response")
            }
            else{
                Log.i("Loog", "Ошибка регистрации Перезапустите приложение 1 ")
                showMessageDialog("Ошибка регистрации\n" +
                        "Перезапустите приложение")
            }

        }
        else{
            Log.i("Loog", "Ошибка регистрации Перезапустите приложение 2")
            showMessageDialog("Ошибка регистрации\n" +
                    "Перезапустите приложение")
        }
    }

    private fun isConnecting(): Boolean{
        return try {
            val cm = applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = cm.activeNetworkInfo
            network.isConnected
        }catch (e: IllegalStateException){
            false
        }catch (e: IllegalArgumentException){
            false
        }


    }

    override fun showUpdateAppDialog(){
        AppDialogs().dialogUpdateApp(this, "Необходимо обновить приложение до актуальной версии", this)
    }

    override fun openPlayMarketForUpdate(){
        val appPackageName = packageName
        startActivity( Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
    }

    override fun showMessageToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    override fun dontUpdateApp() {
       finish()
    }

}
