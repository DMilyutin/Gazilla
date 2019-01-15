package com.gazilla.mihail.gazillaj.ui.account;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.POJO.Success;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.account.AccountPresentation;
import com.gazilla.mihail.gazillaj.presentation.account.AccountView;
import com.gazilla.mihail.gazillaj.utils.ErrorDialog;
import com.gazilla.mihail.gazillaj.utils.Initialization;

public class AccountActivity extends AppCompatActivity implements AccountView {

    AccountPresentation accountPresentation;

    private EditText edName;
    private EditText edPhone;
    private EditText edEmail;
    private Button btSave;

   //

    private SharedPref sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (accountPresentation==null)
            accountPresentation = new AccountPresentation(this);

        sharedPref = new SharedPref(this);

        edName  = findViewById(R.id.etNameAccoun);
        edPhone = findViewById(R.id.etPhoneAccoun);
        edEmail = findViewById(R.id.etEmailAccoun);
        //
        btSave    = findViewById(R.id.btSaveAccoun);

        checkUserInfo();



        btSave.setOnClickListener(v -> {
            String n="";
            String p="";
            String e="";

            n = edName.getText().toString();
            p = edPhone.getText().toString();
            e = edEmail.getText().toString();

            sharedPref.saveName(n);
            sharedPref.saveMyPhone(p);
            sharedPref.saveMyEmail(e);

            accountPresentation.updateUserInfo(n,p,e);
        });

    }

    private void checkUserInfo(){

        if(sharedPref.myPreff()){
            String n = sharedPref.getNameFromPref();
            String p = sharedPref.getPhoneFromPref();
           String e = sharedPref.getEmailFromPref();
            if(n!= null)
                edName.setText(n);
            if(p!=null)
                edPhone.setText(p);
           if(e!=null)
                edEmail.setText(e);
        }
    }

    @Override
    public void responseUpdate(Success success) {
        String txt = "";
        if(success.isSuccess())
            txt = "Ваши данные успешно сохранены";
        else{

            Log.i("Loog", "Success account " +success.isSuccess() +" mess "+ success.getMessage());
            txt = "Произошла ошибка записи Ваших даннвх на сервер";
        }

        ErrorDialog errorDialog = new ErrorDialog(this);

        errorDialog.detailTargetProgress(txt);
    }
}
