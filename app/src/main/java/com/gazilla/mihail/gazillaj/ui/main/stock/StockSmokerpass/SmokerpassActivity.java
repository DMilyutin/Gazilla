package com.gazilla.mihail.gazillaj.ui.main.stock.StockSmokerpass;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.utils.POJO.PromoSmokerpass;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.QRcode;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SmokerpassCallBack;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SmokerpassActivity extends AppCompatActivity {

    private AppDialogs appDialogs;
    private TextView tvDate;
    private TextView tvHelpDate;
    private Button btSmokerpass;

    private AlertDialog dialogWithQRcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smokerpass);

        appDialogs = new AppDialogs();
        tvDate = findViewById(R.id.tvDateStockSmokepass);
        tvHelpDate = findViewById(R.id.tvHelpSmokePress);
        btSmokerpass = findViewById(R.id.btByuSmokerpass);

        btSmokerpass.setOnClickListener(v -> {
            String txt = btSmokerpass.getText().toString();
            if (txt.equals("Предъявить")){
                dialogWithQRCode();
            }
            else
                appDialogs.warningDialog(this, "Для того чтобы приобрести дымный абонемент, обратитесь пожалуйста к нашему сотруднику", "SmokerpassActivity");

        });
        mySmokerpass();
    }

    private void mySmokerpass(){

        Initialization.repositoryApi.smokerpassing(Initialization.userWithKeys.getPublickey(),
                Initialization.signatur(Initialization.userWithKeys.getPrivatekey(), ""),
                new SmokerpassCallBack() {
                    @Override
                    public void mySmokerpass(PromoSmokerpass promoSmokerpass) {
                        String ex = promoSmokerpass.getExpire();
                        if(!ex.equals("")){
                            btSmokerpass.setText("Предъявить");
                            setInfo(ex, promoSmokerpass.getLastTake());
                        }
                        else{
                            tvDate.setVisibility(View.GONE);
                            tvHelpDate.setVisibility(View.GONE);
                            btSmokerpass.setText("Приобрести");
                        }
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
            @SuppressLint("SimpleDateFormat") String ex = new  SimpleDateFormat("dd MMMM yyyyг.").format(date);
            tvDate.setVisibility(View.VISIBLE);
            tvHelpDate.setVisibility(View.VISIBLE);
            tvDate.setText(ex);
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void dialogWithQRCode(){
        String text2 = "Дождитесь, пока сотрудник\n" +
                "считает Ваш QR-код";
        Bitmap bitmap;

        String dataForQrCode = String.valueOf(Initialization.userWithKeys.getId());

        try {
            bitmap = QRcode.encodeAsBitmap(dataForQrCode, BarcodeFormat.QR_CODE, 200, 200);

            View dialog = getLayoutInflater().inflate(R.layout.dialod_with_qr_code, null);

            ImageView imgQRcode = dialog.findViewById(R.id.imvQRcodeDetailPresentDialog);
            imgQRcode.setImageBitmap(bitmap);

            TextView tv = dialog.findViewById(R.id.tvTxtDialogByFirst);
            tv.setText(text2);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setPositiveButton("Готово", (dialog1, which) -> {
                dialogWithQRcode.dismiss();
            })
                    .setNegativeButton("Отмена", (dialog12, which) -> dialogWithQRcode.dismiss());
            builder.setView(dialog);

            dialogWithQRcode = builder.create();
            dialogWithQRcode.show();

            Button bt = dialogWithQRcode.getButton(DialogInterface.BUTTON_POSITIVE);
            Button bt1 = dialogWithQRcode.getButton(DialogInterface.BUTTON_NEGATIVE);
            bt.setTextColor(Color.rgb(254, 194, 15));
            bt1.setTextColor(Color.rgb(254, 194, 15));


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
