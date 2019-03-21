package com.gazilla.mihail.gazillaj.ui.main.stock.StockDragonway;

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

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StoksDragonWay.DragonWayPresentation;
import com.gazilla.mihail.gazillaj.presentation.main.stock.StoksDragonWay.DragonWayView;
import com.gazilla.mihail.gazillaj.utils.QRcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class DragonwayActivity extends AppCompatActivity implements DragonWayView {

    private Button button;
    private AlertDialog dialogWithQRcode;

    private DragonWayPresentation dragonWayPresentation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dragonway);

        button = findViewById(R.id.btTakePromoDragonWay);

        if (dragonWayPresentation == null)
            dragonWayPresentation = new DragonWayPresentation(this);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dragonWayPresentation.myLvlDragonWay();
            }
        });
    }


    @Override
    public void myLevel(int lvl) {


        String text2 = "Дождитесь, пока сотрудник\n" +
                "считает Ваш QR-код";
        Bitmap bitmap;

        String dataForQrCode = String.valueOf(lvl);

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
            });

            builder.setView(dialog);
            dialogWithQRcode = builder.create();
            dialogWithQRcode.show();

            Button bt = dialogWithQRcode.getButton(DialogInterface.BUTTON_POSITIVE);
            bt.setTextColor(Color.rgb(254, 194, 15));



        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
