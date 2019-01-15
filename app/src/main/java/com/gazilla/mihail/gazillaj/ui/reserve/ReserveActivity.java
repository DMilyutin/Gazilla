package com.gazilla.mihail.gazillaj.ui.reserve;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.POJO.Reserve;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.ReserveInteractor;
import com.gazilla.mihail.gazillaj.presentation.reserve.ReservePresentation;
import com.gazilla.mihail.gazillaj.presentation.reserve.ReserveView;
import com.gazilla.mihail.gazillaj.utils.ErrorDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReserveActivity extends AppCompatActivity implements ReserveView {

    private ReservePresentation reservePresentation;

    private EditText edName;
    private EditText edPhone;
    private EditText pioples;
    //private EditText hourses;
    private CheckBox predzakaz;
    private TextView tvDate;
    private TextView tvTime;

    private ErrorDialog errorDialog;

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

        errorDialog = new ErrorDialog(this);
        dateAndTime = Calendar.getInstance();

        Button newReserve = findViewById(R.id.btNewReserve);

        edName =findViewById(R.id.edNameReserve);
        edPhone =findViewById(R.id.edPhoneReserve);
        pioples=findViewById(R.id.edPeoplesReserve);
        //hourses=findViewById(R.id.edHoursesReserve);
        tvDate=findViewById(R.id.tvDateReserve);
        tvTime=findViewById(R.id.tvTimeReserve);
        predzakaz=findViewById(R.id.cbPredzakazReserve);

        checkUserInfo();

        tvDate.setOnClickListener(v -> {



            alertDialog = new DatePickerDialog(ReserveActivity.this, R.style.TimePike, d,
                    dateAndTime.get(Calendar.YEAR),
                    dateAndTime.get(Calendar.MONTH),
                    dateAndTime.get(Calendar.DAY_OF_MONTH));

                    //alertDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(254, 194, 15));
                    //alertDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.rgb(254, 194, 15));

                    alertDialog.show();

        })
        ;

        tvTime.setOnClickListener(v -> {
            //TimePickerDialog timePickerDialog = TimePickerDialog.newInstance()

            new TimePickerDialog(ReserveActivity.this,R.style.TimePike,  t,
                    dateAndTime.get(Calendar.HOUR_OF_DAY),
                    dateAndTime.get(Calendar.MINUTE), true)
                    .show();

        });

        newReserve.setOnClickListener(v -> {

            if(Calendar.getInstance().getTimeInMillis()>=dateAndTime.getTimeInMillis()){
                errorDialog.detailTargetProgress("Дата указана неверно");
                return;
            }

            if (!pioples.getText().toString().equals("")&&!edPhone.getText().toString().equals("")&&!edName.getText().toString().equals("")) {

                int qty = Integer.parseInt(pioples.getText().toString());
                //int hours = Integer.parseInt(hourses.getText().toString());
                int hours = 1;
                @SuppressLint("SimpleDateFormat") String dateFroReserve = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX")
                        .format(dateAndTime.getTimeInMillis());
                String phon = checkPhone(edPhone.getText().toString());
                if (!phon.equals("")) {
                    String nam = edName.getText().toString();
                    String comment = "";
                    Boolean preorder = false;

                    if (predzakaz.isChecked()) preorder = true;
                    if (!predzakaz.isChecked()) preorder = false;
                    Reserve reserve = new Reserve(qty, hours, dateFroReserve, phon, nam, comment);
                    putReserve(reserve, preorder);
                } else
                    errorDialog.detailTargetProgress("Все поля должны быть заполнены");

            }

        });

    }

    private String checkPhone(String s) {
        if(s.charAt(0)=='8'&&s.length()==11){
            return "+7"+s.charAt(1)+s.charAt(2)+s.charAt(3)+s.charAt(4)+s.charAt(5)+s.charAt(6)+s.charAt(7)+s.charAt(8)+s.charAt(9)+s.charAt(10);
        }
        else if (s.charAt(0)=='+'&&s.charAt(1)=='7'&&s.length()==12)
            return s;

        return "";
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
            errorDialog.detailTargetProgress("В это время бронирование столов невозможно");
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
            edName.setText(name);
            edPhone.setText(phone);
    }

    @Override
    public void putReserve(Reserve reserve, Boolean preorder) {
        if(bTime&&bDate)
        reservePresentation.reservingPresenter(reserve, preorder);
        else
            errorDialog.detailTargetProgress("Укажите время и дату");
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
        errorDialog.detailTargetProgress(error);
    }
}
