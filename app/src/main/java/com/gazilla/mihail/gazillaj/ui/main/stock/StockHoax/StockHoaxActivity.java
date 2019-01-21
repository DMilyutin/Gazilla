package com.gazilla.mihail.gazillaj.ui.main.stock.StockHoax;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Chronometer;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StockHoaxActivity extends AppCompatActivity {

    TextView tvDate;
    TextView tvHour;
    TextView tvMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_hoax);

        tvDate = findViewById(R.id.tvDaysStockHoax);
        tvHour = findViewById(R.id.tvHourStockHoax);
        tvMin = findViewById(R.id.tvMinStockHoax);
        t();

    }


    private void t(){


        Calendar calendarFriday = Calendar.getInstance();
        calendarFriday.set(Calendar.DAY_OF_WEEK, 6);
        calendarFriday.set(Calendar.HOUR_OF_DAY, 20);
        calendarFriday.set(Calendar.MINUTE, 00);
        calendarFriday.set(Calendar.SECOND, 00);

        Calendar calendarNow = Calendar.getInstance();

        long dFraiday = calendarFriday.getTimeInMillis();
        long dNow = calendarNow.getTimeInMillis();

        if (dFraiday>dNow){
            long dayY = dFraiday-dNow;
            long day = dayY/(86400000);
            dayY = dayY - day*86400000;
            long hh = dayY/(3600000);
            dayY = dayY - hh*3600000;
            long mm = dayY/60000;
            Log.i("Loog", "DD: " + day + " HH: " + hh + " mm: " + mm);
            //String dat = day+"д. "+hh+":"+mm;
            tvDate.setText(String.valueOf(day));
            tvHour.setText(String.valueOf(hh));
            tvMin.setText(String.valueOf(mm));
        }
        else {
            int weekNow = calendarNow.get(Calendar.WEEK_OF_MONTH);
            calendarFriday.set(Calendar.WEEK_OF_MONTH, weekNow+1);
            dFraiday = calendarFriday.getTimeInMillis();
            long dayY = dFraiday-dNow;
            long z = dayY;

            long day = dayY/(86400000);  // нашли дни
            z= z - day*86400000;
            dayY = dayY%(86400000); // остаток от дней( часы)
            long hh = dayY/(3600000); // нашли кол-во часов
            z=z-hh*3600000;
            Log.i("Loog", "z: " + z);
            long mm = z/60000;
            Log.i("Loog", "DD: " + day + " HH: " + hh + " mm: " + mm);
            //String dat = day+" : "+hh+" : "+mm;
            tvDate.setText(String.valueOf(day));
            tvHour.setText(String.valueOf(hh));
            tvMin.setText(String.valueOf(mm));
        }

    }
}
