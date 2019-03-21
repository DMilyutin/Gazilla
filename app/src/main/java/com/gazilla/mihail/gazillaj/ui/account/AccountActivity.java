package com.gazilla.mihail.gazillaj.ui.account;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.account.AccountPresentation;
import com.gazilla.mihail.gazillaj.presentation.account.AccountView;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;

/** Активити с управлением данных User */
public class AccountActivity extends MvpAppCompatActivity implements AccountView {
    /** Пресентер данной активити*/

    @InjectPresenter
    AccountPresentation accountPresentation;

    private EditText edName;
    private EditText edPhone;
    private EditText edEmail;

    private TextView tvMyIdAccount;

    private AppDialogs appDialogs;

    @ProvidePresenter
    AccountPresentation provideAccountPresentation(){
        return new AccountPresentation(this.getPreferences(Context.MODE_PRIVATE));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        edName  = findViewById(R.id.etNameAccoun);
        edPhone = findViewById(R.id.etPhoneAccoun);
        edEmail = findViewById(R.id.etEmailAccoun);
        tvMyIdAccount = findViewById(R.id.tvMyIdAccount);


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

            accountPresentation.newUserInfo(n,p,e);
        });

    }

    /** Установка сохраненных данных */
    @Override
    public void setUserInfo(String name, String phone, String email, String id) {
        if(name!= null)
            edName.setText(name);
        if(phone!=null)
            edPhone.setText(phone);
        if(email!=null)
            edEmail.setText(email);
        if (id!=null)
            tvMyIdAccount.setText(id);
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

    @Override
    protected void onDestroy() {
        Log.i("Loog", this.getPackageName() + "уничтожено");
        super.onDestroy();
    }
}
