package com.gazilla.mihail.gazillaj.ui.account;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.account.AccountPresentation;
import com.gazilla.mihail.gazillaj.presentation.account.AccountView;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;

/** Активити с управлением данных User */
public class AccountActivity extends AppCompatActivity implements AccountView {
    /** Пресентер данной активити*/
    private AccountPresentation accountPresentation;

    private EditText edName;
    private EditText edPhone;
    private EditText edEmail;

    private AppDialogs appDialogs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        if (accountPresentation==null)
            accountPresentation = new AccountPresentation(this, this);


        edName  = findViewById(R.id.etNameAccoun);
        edPhone = findViewById(R.id.etPhoneAccoun);
        edEmail = findViewById(R.id.etEmailAccoun);

        Button btSave = findViewById(R.id.btSaveAccoun);

        appDialogs = new AppDialogs();

        accountPresentation.checkUserInfo();

        btSave.setOnClickListener(v -> {
            String n="";
            String p="";
            String e="";
            if (edName.getText()!=null)
            n += edName.getText().toString();
            if (edPhone.getText()!=null)
            p += edPhone.getText().toString();
            if (edEmail.getText()!=null)
            e += edEmail.getText().toString();


            //Log.

            accountPresentation.newUserInfo(n,p,e);
        });

    }

    /** Установка сохраненных данных */
    @Override
    public void setUserInfo(String name, String phone, String email) {
        if(name!= null)
            edName.setText(name);
        if(phone!=null)
            edPhone.setText(phone);
        if(email!=null)
            edEmail.setText(email);
    }

    /** Показ диалога с ошибкой */
    @Override
    public void showWorningDialog(String txt) {
        appDialogs.warningDialog(this, txt);
    }
    /** Показ диалога с загрузкой  */
    @Override
    public void showLoadingDialog() {
        appDialogs.loadingDialog(this);
    }

    /** Закрытие диалога */
    @Override
    public void clouseAppDialog() {
        appDialogs.clouseDialog();
    }


}
