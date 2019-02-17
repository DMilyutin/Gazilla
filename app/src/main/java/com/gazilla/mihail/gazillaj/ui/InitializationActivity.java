package com.gazilla.mihail.gazillaj.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InitPresentation;
import com.gazilla.mihail.gazillaj.presentation.Initialization.InnitView;
import com.gazilla.mihail.gazillaj.ui.main.MainActivity;
import com.gazilla.mihail.gazillaj.ui.registration.RegAndAutorizActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.BugReport;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.QRcode;

/**
 *  Активити, запускаемая при старте программы
 * @author Dmitry Milyutin
 */

public class InitializationActivity extends AppCompatActivity implements InnitView {



    /** Поле пресентора для данной активити */
    private InitPresentation initPresentation;

    /** Поле статического класса для работы с приложением {@link Initialization#Initialization(Context)}*/
    private Initialization init;

    /** Код для возрата с Активити регистрации */
    private final int CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(initPresentation==null)
            initPresentation = new InitPresentation(this,this);

        init = new Initialization(this);
        /** Статический класс создания QR кодов во всем приложении*/
        new QRcode();

        initPresentation.checkUserDate();
    }

    /** Метод запуска главного экрана приложения {@link InitPresentation#checkUserDat()} */
    @Override
    public void startMainActivity() {
        Intent intent = new Intent(InitializationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /** Метод запуска активити авторизации и регистрации {@link InitPresentation#checkUserDat()}*/
    @Override
    public void startRegistrationActivity() {
        Intent intent = new Intent(InitializationActivity.this, RegAndAutorizActivity.class);
        startActivityForResult(intent, CODE);
    }


    /** Метод получения результата после регистрации или авторизации*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data==null) return;
        Boolean response = data.getBooleanExtra("REG", false);
        Log.i("Loog" ,"respon bool - " + response);
        if (response){
            initPresentation.checkUserDate();
            // продолжение инициализации
        }
        else {
            new AppDialogs().warningDialog(this, "Ошибка регистрации\nПерезапустите приложение");
        }

    }

}
