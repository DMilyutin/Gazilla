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

            p=checkPhone(p);

            sharedPref.saveName(n);
            sharedPref.saveMyPhone(p);
            sharedPref.saveMyEmail(e);

            Log.i("Loog", "udate phone - "+p);
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

        AppDialogs appDialogs = new AppDialogs();

        appDialogs.warningDialog(this, txt, "Готово");
    }

    public String checkPhone(String s) {
        if (s==null||s.equals("")) return "";

        if(s.charAt(0)=='8'&&s.length()==11){
            return ""+ s.charAt(1)+s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10);
        }
        else if (s.charAt(0)=='+'&&s.charAt(1)=='7'&&s.length()==12)
            return ""+ s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10)+s.charAt(11);
        else if (s.charAt(0)=='9'&&s.length()==10)
            return s;
        AppDialogs appDialogs = new AppDialogs();
        appDialogs.warningDialog(this, "Неверный формат номера", "Повторить");
        return "";
    }
}
