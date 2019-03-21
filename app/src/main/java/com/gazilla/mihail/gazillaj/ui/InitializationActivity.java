package com.gazilla.mihail.gazillaj.ui;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InitPresentation;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.ui.main.MainActivity;
import com.gazilla.mihail.gazillaj.ui.registration.RegAndAutorizActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.QRcode;

/**
 *  Активити, запускаемая при старте программы
 * @author Dmitry Milyutin
 */

public class InitializationActivity extends AppCompatActivity implements InnitView {

    /** Поле пресентора для данной активити */
    private InitPresentation initPresentation;



    /** Код для возрата с Активити регистрации */
    private final int CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        initPresentation = new InitPresentation(this.getSharedPreferences("myProf",Context.MODE_PRIVATE), wifiInfo.isConnected(), this);


        /** Статический класс создания QR кодов во всем приложении*/
        new QRcode();

        initPresentation.checkUserDate();
    }

    /** Метод запуска главного экрана приложения {@link InitPresentation#checkUserDat()} */
    @Override
    public void startMainActivity() {
        Intent intent = new Intent(InitializationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    /** Метод запуска активити авторизации и регистрации {@link InitPresentation#checkUserDat()}*/
    @Override
    public void startRegistrationActivity() {
        Intent intent = new Intent(InitializationActivity.this, RegAndAutorizActivity.class);
        startActivityForResult(intent, CODE);
    }

    @Override
    public void showErrorer(String error) {
        new AppDialogs().warningDialog(this, error);
    }


    /** Метод получения результата после регистрации или авторизации*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.i("Loog", "onActivityResult");
        if(data==null){
            Log.i("Loog", "резалт нал");
            return;
        }
        Boolean response = data.getBooleanExtra("REG", false);
        Log.i("Loog" ,"respon bool - " + response);
        if (response){
            initPresentation.checkUserDate();
        }
        else {
            new AppDialogs().warningDialog(this, "Ошибка регистрации\nПерезапустите приложение");
        }
    }

}
