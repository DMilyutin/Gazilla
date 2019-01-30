package com.gazilla.mihail.gazillaj.ui.registration;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.RegAndAutorizInteractor;
import com.gazilla.mihail.gazillaj.presentation.registration.RegAndAutorizPresenter;
import com.gazilla.mihail.gazillaj.presentation.registration.RegAndAutorizView;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;

public class RegAndAutorizActivity extends AppCompatActivity implements RegAndAutorizView {

    private RegAndAutorizPresenter presenter;

    //private ProgressBar pbSendCodeOnMail;

    // ------------------------- Восстановление аккаунта-----------------------------------
    private TextView tvTxtWithCode;
    private EditText code;
    private Button btLogin;
    private AlertDialog loginDialog;
    //-------------------------------------------------------------------------------------

    // ---------------------------Регистрация----------------------------------------------
    private EditText etPromocode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_and_autoriz);

        if (presenter == null)
            presenter = new RegAndAutorizPresenter(this, new RegAndAutorizInteractor(), this);

        TextView tvLogind = findViewById(R.id.tvLoginRegActivity);

        Button btRegWithPromo = findViewById(R.id.btRegWithPromo);
        TextView tvDontHavePromo = findViewById(R.id.tvDontHavePromo);
        etPromocode = findViewById(R.id.etPromoReg);

        btRegWithPromo.setOnClickListener(v -> {
            if (etPromocode.getText()!=null) {
                String type = poromoORrefer(etPromocode.getText().toString());
                if (type.equals("Refer"))
                    regNewUser(false, etPromocode.getText().toString());
                else if (type.equals("Promo"))
                    regNewUser(true, etPromocode.getText().toString());
            }
            else
                new AppDialogs().warningDialog(this, "Промокод не может быть пустым", "Повторить");
        });

        tvDontHavePromo.setOnClickListener(v -> {
            regNewUser(true, "");
        });


        tvLogind.setOnClickListener(v -> {
            loginDialog();
        });

    }

    public String poromoORrefer(String text){
        if (text.contains("0")||text.contains("1")||text.contains("2")||text.contains("3")||
                    text.contains("4")||text.contains("5")||text.contains("6")||text.contains("7")||
                    text.contains("8")||text.contains("9"))
            return "Refer";
        else
            return "Promo";

    }

    // Регистрация нового пользователя
    private void regNewUser(Boolean isPromo, String txtPromo){
        if (isPromo)
            presenter.registrationApi("", "", "", "", txtPromo);
        else
            presenter.registrationApi("", "", "",  txtPromo, "");

    }



    // -------------------------восстановление аккаунта-------------------------------------
    private void loginDialog(){
        View dialog = getLayoutInflater().inflate(R.layout.dialog_recover_account, null);

        EditText login = dialog.findViewById(R.id.etUserDataRecoverDialog);
        tvTxtWithCode = dialog.findViewById(R.id.textView42);
        code = dialog.findViewById(R.id.etCodeForLoginRecoverDialog);
        btLogin = dialog.findViewById(R.id.btLoging);


        tvTxtWithCode.setVisibility(View.GONE);
        code.setVisibility(View.GONE);

        btLogin.setOnClickListener(v -> {
            if (btLogin.getText().equals("Получить код")){
                btLogin.setClickable(false);

                // проверка на нал
                if (login.getText()!=null){
                    sendCodeOnMail(login.getText().toString());

                }
            }
            else {
                if(code.getText()!=null) {
                    btLogin.setClickable(false);
                    checkCodeFromServer(code.getText().toString());
                    loginDialog.dismiss();
                }


            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(dialog);

        loginDialog = builder.create();
        loginDialog.show();
        loginDialog.setCanceledOnTouchOutside(false);

    }

    //---------------------------------------------------------------------------------------------

    private void sendCodeOnMail(String s) {
        if (!s.equals("")){
            if (s.contains("@")){
                presenter.detCodeForLogin("", s);
            }
            else
                presenter.detCodeForLogin(s, "");
        }

    }

    private void checkCodeFromServer(String code){
        if(!code.equals(""))
            presenter.sendCodeForLogin(code);
        else {
            // неверный код
        }
    }


    @Override
    public void registrationRegActivity(String name, String phone, String email, String refererLink,  boolean save, String promo) {
        Log.i("Loog", "registrationRegActivity");

        if(save){
            if(name.equals("")||name.isEmpty()){
                selectEmptyText("name");
                return;}
            if(phone.equals("")||phone.isEmpty()){
                selectEmptyText("phone");
                return;
            }
        }

        phone = checkPhone(phone);

        presenter.registrationApi(name, phone, email, refererLink, promo);

    }

    public void selectEmptyText(String pole) {
        String error = "*Это поле обязательно для заполнения";

        switch (pole){
            case "name" :

            break;
            case "phone" :

                break;


        }
    }

    @Override
    public void showErrorr(String error) {
        AppDialogs appDialogs = new AppDialogs();
        appDialogs.warningDialog(this, error, "Ок");
        if (loginDialog!=null)
            loginDialog.dismiss();
    }


    @Override
    public void visibleETCode() {
        btLogin.setClickable(true);

        code.setVisibility(View.VISIBLE);
        tvTxtWithCode.setVisibility(View.VISIBLE);
        btLogin.setText("Войти");
    }

    @Override
    public void startProgramm(Boolean response) {
        Intent intent = new Intent();
        intent.putExtra("REG", response);
        setResult(RESULT_OK, intent);
        finish();
    }



    private String checkPhone(String s) {
        if (s==null||s.equals("")) return "";
        if(s.charAt(0)=='8'&&s.length()==11){
            return ""+ s.charAt(1)+s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10);
        }
        else if (s.charAt(0)=='+'&&s.charAt(1)=='7'&&s.length()==12)
            return ""+ s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10)+s.charAt(11);
        else if (s.charAt(0)=='9'&&s.length()==10)
            return s;
        selectEmptyText("phone");
        return "";
    }


}
