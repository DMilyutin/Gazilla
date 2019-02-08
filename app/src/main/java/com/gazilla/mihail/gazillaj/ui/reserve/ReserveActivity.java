package com.gazilla.mihail.gazillaj.ui.reserve;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.utils.POJO.Reserve;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.ReserveInteractor;
import com.gazilla.mihail.gazillaj.presentation.reserve.ReservePresentation;
import com.gazilla.mihail.gazillaj.presentation.reserve.ReserveView;
import com.gazilla.mihail.gazillaj.ui.account.AccountActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReserveActivity extends AppCompatActivity implements ReserveView {

    private ReservePresentation reservePresentation;

    private EditText pioples;
    private CheckBox predzakaz;
    private TextView tvDate;
    private TextView tvTime;
    private EditText etPhone;
    private EditText etName;

    private AppDialogs appDialogs;

    private Boolean bTime = false;
    private Boolean bDate = false;

    private Calendar dateAndTime;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        if(reservePresentation == null){
            reservePresentation = new ReservePresentation(this, new ReserveInteractor(), this);
        }

        appDialogs = new AppDialogs();
        dateAndTime = Calendar.getInstance();

        Button btNewReserve = findViewById(R.id.btNewReserve);

        pioples=findViewById(R.id.edPeoplesReserve);
        tvDate=findViewById(R.id.tvDateReserve);
        tvTime=findViewById(R.id.tvTimeReserve);
        predzakaz=findViewById(R.id.cbPredzakazReserve);
        etPhone=findViewById(R.id.edPhoneReserve);
        etName=findViewById(R.id.etNameReserve);

        reservePresentation.checkUserInfo();
        //checkUserInfo();

        tvDate.setOnClickListener(v -> {
            alertDialog = new DatePickerDialog(ReserveActivity.this, R.style.TimePike, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH));
                    alertDialog.show();

        })
        ;

        tvTime.setOnClickListener(v -> {
            new TimePickerDialog(ReserveActivity.this,R.style.TimePike,  t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();
        });

        btNewReserve.setOnClickListener(v -> {

            if(Calendar.getInstance().getTimeInMillis()>=dateAndTime.getTimeInMillis()){
                appDialogs.warningDialog(this, "Дата указана неверно");
                return;
            }

            appDialogs.loadingDialog(this);

            if (!pioples.getText().toString().equals("")) {

                int qty = Integer.parseInt(pioples.getText().toString());
                int hours = 1;
                @SuppressLint("SimpleDateFormat") String dateFroReserve = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                        .format(dateAndTime.getTimeInMillis());

                if (!etName.getText().toString().equals("")&&!etPhone.getText().toString().equals("")) {
                    String phone = etPhone.getText().toString();
                    String name = etName.getText().toString();
                    String comment = "";
                    Boolean preorder = false;

                    if (predzakaz.isChecked()) preorder = true;
                    if (!predzakaz.isChecked()) preorder = false;
                    Reserve reserve = new Reserve(qty, hours, dateFroReserve, phone, name, comment);
                    putReserve(reserve, preorder);
                } else{
                    clouseAppDialog();
                    appDialogs.warningDialog(this, "Все поля должны быть заполнен");
                }

            }
            else{
                clouseAppDialog();
                appDialogs.warningDialog(this, "Все поля должны быть заполнен");
            }
        });

    }


    DatePickerDialog.OnDateSetListener d = (view, year, monthOfYear, dayOfMonth) -> {
        dateAndTime.set(Calendar.YEAR, year);
        dateAndTime.set(Calendar.MONTH, monthOfYear);
        dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);


       @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("dd-MM-yyyy").format(dateAndTime.getTimeInMillis());
        tvDate.setText(date);
        bDate = true;
    };

    TimePickerDialog.OnTimeSetListener t= (view, hourOfDay, minute) -> {

        if (hourOfDay>=0&&hourOfDay<12) {
            appDialogs.warningDialog(this, "В это время бронирование столов невозможно");
            return;
        }

        minute = roundMinute(minute);

        dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        dateAndTime.set(Calendar.MINUTE, minute);

        @SuppressLint("SimpleDateFormat") String time = new SimpleDateFormat("HH:mm").format(dateAndTime.getTimeInMillis());
        tvTime.setText(time);
        bTime = true;
    };

    private int roundMinute(int minute) {
        if(minute>=53&&minute<60||minute>=0&&minute<8) return 0;
        if(minute>=8&&minute<23) return 15;
        if(minute>=23&&minute<38) return 30;
        if(minute>=38&&minute<53) return 45;

        return minute;
    }



    @Override
    public void inputUserInfo(String name, String phone) {
        if (name==null||name.equals(""))
            etName.setText("");
        else
            etName.setText(name);
        if (phone==null||phone.equals(""))
            etPhone.setText("");
        else
            etPhone.setText(phone);
    }

    @Override
    public void putReserve(Reserve reserve, Boolean preorder) {
        if(bTime&&bDate){

            reservePresentation.reservingPresenter(reserve, preorder);
        }
        else
        appDialogs.warningDialog(this, "Укажите время и дату");
    }

    @Override
    public void resultReserve(String result) {
        appDialogs.warningDialog(this, result);
        onBackPressed();
    }

    @Override
    public void showWorningDialog(String err) {
        appDialogs.warningDialog(this, err);
    }

    @Override
    public void showLoadingDialog() {
        appDialogs.loadingDialog(this);
    }

    @Override
    public void clouseAppDialog() {
        appDialogs.clouseDialog();
    }

    @Override
    public void showErrorDialog(String err, String locatoin) {
        appDialogs.errorDialog(this, err, locatoin);
    }

}
