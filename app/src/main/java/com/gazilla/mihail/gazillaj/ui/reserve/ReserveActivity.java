package com.gazilla.mihail.gazillaj.ui.reserve;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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


    private String name;
    private String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve);

        if(reservePresentation == null){
            reservePresentation = new ReservePresentation(this, new ReserveInteractor(), this);
        }

        reservePresentation.checkUserInfo();

        appDialogs = new AppDialogs();
        dateAndTime = Calendar.getInstance();

        Button newReserve = findViewById(R.id.btNewReserve);

        pioples=findViewById(R.id.edPeoplesReserve);
        tvDate=findViewById(R.id.tvDateReserve);
        tvTime=findViewById(R.id.tvTimeReserve);
        predzakaz=findViewById(R.id.cbPredzakazReserve);
        etPhone=findViewById(R.id.edPhoneReserve);
        etName=findViewById(R.id.etNameReserve);

        if (name!=null&&!name.equals(""))
            etName.setText(name);

        if (phone!=null&&!phone.equals(""))
            etPhone.setText(phone);

        checkUserInfo();

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

        newReserve.setOnClickListener(v -> {

            if(Calendar.getInstance().getTimeInMillis()>=dateAndTime.getTimeInMillis()){
                appDialogs.warningDialog(this, "Дата указана неверно", "repeat");
                return;
            }

            if (!pioples.getText().toString().equals("")) {

                int qty = Integer.parseInt(pioples.getText().toString());
                //int hours = Integer.parseInt(hourses.getText().toString());
                int hours = 1;
                @SuppressLint("SimpleDateFormat") String dateFroReserve = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                        .format(dateAndTime.getTimeInMillis());

                if (!etName.getText().toString().equals("")&&!etPhone.getText().toString().equals("")) {
                    phone = etPhone.getText().toString();
                    name = etName.getText().toString();
                    String comment = "";
                    Boolean preorder = false;

                    if (predzakaz.isChecked()) preorder = true;
                    if (!predzakaz.isChecked()) preorder = false;
                    Reserve reserve = new Reserve(qty, hours, dateFroReserve, phone, name, comment);
                    putReserve(reserve, preorder);
                } else
                appDialogs.warningDialog(this, "Все поля должны быть заполнен", "Повторить");

            }
            else
                appDialogs.warningDialog(this, "Все поля должны быть заполнен", "Повторить");
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
            appDialogs.warningDialog(this, "В это время бронирование столов невозможно", "repeat");
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
    public void checkUserInfo() {
        reservePresentation.checkUserInfoOnDB();
    }

    @Override
    public void inputUserInfo(String name, String phone) {
           this.name = name;
           this.phone = phone;
    }

    @Override
    public void putReserve(Reserve reserve, Boolean preorder) {
        if(bTime&&bDate)
        reservePresentation.reservingPresenter(reserve, preorder);
        else
        appDialogs.warningDialog(this, "Укажите время и дату", "Повторить");
    }

    @Override
    public void resultReserve(String result) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(result);
        builder.setNegativeButton("Ок", (dialog, which) -> {
            // закрытие
            onBackPressed();
        });
        builder.create().show();
    }

    @Override
    public void showErrorr(String error) {

        appDialogs.warningDialog(this, error, "Повторить");
    }

    @Override
    public void unRegUser() {
        android.support.v7.app.AlertDialog errorDialog;

        // предложить зарегестрироваться или позвонить
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog = inflater.inflate(R.layout.dialog_un_reg_user, null);

       Button btCall = dialog.findViewById(R.id.btCallDialogUnRegUser);


       btCall.setOnClickListener(v ->  callInCofe());

        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                onBackPressed();
            }
        }).setPositiveButton("Регистрация", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                startActivity(intent);
            }
        });

        builder.setView(dialog);
        errorDialog = builder.create();
        errorDialog.show();

        Button bt = errorDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

        Button bt2 = errorDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt2.setTextColor(Color.rgb(254, 194, 15));
    }

    private void callInCofe(){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:89652662222"));
        startActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        reservePresentation.checkUserInfo();
    }
}
