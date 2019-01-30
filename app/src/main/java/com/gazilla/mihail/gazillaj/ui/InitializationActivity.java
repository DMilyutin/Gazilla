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
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.QRcode;

public class InitializationActivity extends AppCompatActivity implements InnitView {

    private InitPresentation initPresentation;
    private Context context;

    private Initialization init;

    private final int CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initialization);

        if(initPresentation==null)
            initPresentation = new InitPresentation(this,this);


        ((ImageView) findViewById(R.id.imageView4)).setImageResource(R.drawable.logo_startr_page1);

        context = getApplicationContext();
        init = new Initialization(context);
        new QRcode();

        initPresentation.checkUserDate();
    }


    @Override
    public void startMainActivity() {
        Intent intent = new Intent(InitializationActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void startRegistrationActivity() {
        Intent intent = new Intent(InitializationActivity.this, RegAndAutorizActivity.class);
        startActivityForResult(intent, CODE);
    }

    @Override
    public void startProgressBar() {

    }

    @Override
    public void stopProgressBar() {

    }

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
            new AppDialogs().warningDialog(this, "Ошибка регистрации\nПерезапустите приложение", "Ок");
        }
        //initPresentation.register(reg[0], reg[1], reg[menu_id_2], reg[3]);
    }

}
