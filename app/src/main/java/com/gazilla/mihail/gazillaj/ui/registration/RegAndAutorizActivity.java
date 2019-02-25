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
import com.gazilla.mihail.gazillaj.ui.InitializationActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
/** Активити регстрации */
public class RegAndAutorizActivity extends AppCompatActivity implements RegAndAutorizView {
    /** Пресентер данной активити */
    private RegAndAutorizPresenter presenter;

   private AppDialogs appDialogs;

    // ---------------------------Регистрация----------------------------------------------
    private TextView tvTxtWithCode;
    private EditText code;
    private Button btLogin;
    private AlertDialog loginDialog;
    //-------------------------------------------------------------------------------------


    private EditText etPromocode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_and_autoriz);

        if (presenter == null)
            presenter = new RegAndAutorizPresenter(this, new RegAndAutorizInteractor(), this);

        appDialogs = new AppDialogs();

        TextView tvLogind = findViewById(R.id.tvLoginRegActivity);

        Button btRegWithPromo = findViewById(R.id.btRegWithPromo);
        TextView tvDontHavePromo = findViewById(R.id.tvDontHavePromo);
        etPromocode = findViewById(R.id.etPromoReg);

        btRegWithPromo.setOnClickListener(v -> {
            if (etPromocode.getText()!=null) {
                presenter.regNewUser(true, etPromocode.getText().toString());
            }
        });

        tvDontHavePromo.setOnClickListener(v -> {
            presenter.regNewUser(false, "");
        });


        tvLogind.setOnClickListener(v -> {
            loginDialog();
        });

    }


    // -------------------------восстановление аккаунта-------------------------------------
    /**  Метод открытия диалого для восстановления аккаунта */
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);

        builder.setView(dialog);

        loginDialog = builder.create();
        loginDialog.show();
        loginDialog.setCanceledOnTouchOutside(false);

    }

    //---------------------------------------------------------------------------------------------

    /** запрос кода на маил */
    private void sendCodeOnMail(String s) {
        if (!s.equals("")){
            if (s.contains("@")){
                presenter.getCodeForLogin("", s);
            }
            else
                presenter.getCodeForLogin(s, "");
        }

    }
    /** отправка кода восстановления на сервер */
    private void checkCodeFromServer(String code){
        if(!code.equals(""))
            presenter.sendCodeForLogin(code);
        else {
            new AppDialogs().warningDialog(this, "Неверный код активации");
        }
    }


    /** метод включения полей при восстановлении аккаунта */
    @Override
    public void visibleETCode() {
        btLogin.setClickable(true);

        code.setVisibility(View.VISIBLE);
        tvTxtWithCode.setVisibility(View.VISIBLE);
        btLogin.setText("Войти");
    }
    /** Метод возращения на первую активити для запуска приложения {@link InitializationActivity onActivityResult} */
    @Override
    public void startProgramm(Boolean response) {
        Intent intent = new Intent();
        intent.putExtra("REG", response);
        setResult(RESULT_OK, intent);
        //finish();
    }

    @Override
    public void showLoadingDialog() {
        appDialogs.loadingDialog(this);
    }

    @Override
    public void showWarningDialog(String err) {
        appDialogs.warningDialog(this, err);
    }

    @Override
    public void showErrorDialog(String err, String location) {
        appDialogs.errorDialog(this, err, location);
    }

    @Override
    public void clouaeAppDialog() {
        appDialogs.clouseDialog();
    }


}
