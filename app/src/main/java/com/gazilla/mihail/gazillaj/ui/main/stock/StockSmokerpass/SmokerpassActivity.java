package com.gazilla.mihail.gazillaj.ui.main.stock.StockSmokerpass;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.utils.POJO.PromoSmokerpass;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SmokerpassCallBack;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmokerpassActivity extends AppCompatActivity {

    private AppDialogs appDialogs;
    private TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smokerpass);

        appDialogs = new AppDialogs();
        tvDate = findViewById(R.id.tvDateStockSmokepass);

        mySmokerpass();
    }

    private void mySmokerpass(){



        Initialization.repositoryApi.smokerpassing(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                new SmokerpassCallBack() {
                    @Override
                    public void mySmokerpass(PromoSmokerpass promoSmokerpass) {
                        String ex = promoSmokerpass.getExpire();
                        if(!ex.equals(""))
                            setInfo(ex, promoSmokerpass.getLastTake());
                        else
                            appDialogs.errorDialog(getApplicationContext(), "Для приобретения абонимента обратитесь к работнику", "SmokerpassActivity");

                    }

                    @Override
                    public void errorTxt(String s) {
                        appDialogs.errorDialog(getApplicationContext(), s, "SmokerpassActivity");
                    }
                }, new FailCallBack() {
                    @Override
                    public void setError(Throwable throwable) {

                    }
                });
    }

    private void setInfo(String expire, String lastTake){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX");
        try {
            Date date = formatter.parse(expire);
            @SuppressLint("SimpleDateFormat") String ex = new  SimpleDateFormat("dd.MM.yy").format(date);
            tvDate.setText(ex);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
