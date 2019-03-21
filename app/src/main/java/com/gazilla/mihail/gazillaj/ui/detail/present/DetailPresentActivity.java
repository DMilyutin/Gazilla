package com.gazilla.mihail.gazillaj.ui.detail.present;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.InitializationAp;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.detail.present.DetailPresentPresenter;
import com.gazilla.mihail.gazillaj.presentation.detail.present.DetailPresentView;
import com.gazilla.mihail.gazillaj.utils.QRcode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

/** Активити с детальным показом выбранного продукта из меню */
public class DetailPresentActivity extends MvpAppCompatActivity implements DetailPresentView {

    private AlertDialog firstDialogWithQRcode;
    private AlertDialog secondDialogWithAcsess;
    private Button btBuy;

    /** Пресентер данного класса */
    @InjectPresenter
    DetailPresentPresenter presentPresenter;

    /** POJO класс для меню */
    private MenuItem item;
    private String typeBuy;
    private ImageView imageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_present);

        Intent intent = getIntent();
        item = intent.getParcelableExtra("SelectedItem");
        typeBuy = intent.getStringExtra("Type");


        presentPresenter.getImgItem(String.valueOf(item.getId()));

        imageView = findViewById(R.id.imgDetailItem);
        TextView namePresent = findViewById(R.id.tvNameDetailPresent);
        TextView descriptionPresent = findViewById(R.id.tvDescriptionDetailPresent);
        TextView coastPresent = findViewById(R.id.tvCoastDetailPresent);
        TextView weightPresent = findViewById(R.id.tvWeightDetailPresent);


        btBuy = findViewById(R.id.btBuyDetailPresent);


        namePresent.setText(item.getName());
        descriptionPresent.setText(item.getDescription());
        weightPresent.setText(item.getWeight());
        if(typeBuy.equals("buy"))
            coastPresent.setText(String.valueOf(item.getPrice()));
        else
            coastPresent.setText("0");

        Log.i("Loog" , "id item -" + item.getId());

        btBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btBuy.getText().equals("Получить")) presentPresenter.pressBtNext();
                else onBackPressed();
            }
        });

    }


    @Override
    public void openFirstDialog() {
        dialogWithQRCode();
    }

    @Override
    public void openSecondDialog() {
        acsessDialog();
    }


    @Override
    public void acsessBuy() {
        onBackPressed();
    }

    /** Метод установки картинки товара */
    @Override
    public void setImgItem(Bitmap bitmap) {

        if (bitmap!=null)
            imageView.setImageBitmap(bitmap);
        else
            imageView.setImageResource(R.drawable.gaz);
    }

    @Override
    public void errorDialog(String error) {
        new AppDialogs().warningDialog(DetailPresentActivity.this, error);
    }


    private void dialogWithQRCode(){
        String text2 = "Дождитесь, пока сотрудник\n" +
                "считает Ваш QR-код";
        Bitmap bitmap;

        String dataForQrCode = "";
        if(typeBuy.equals("buy"))
            dataForQrCode = String.valueOf(InitializationAp.getInstance().getUserWithKeys().getId()) +"/" + item.getId();
        else
            dataForQrCode = String.valueOf(InitializationAp.getInstance().getUserWithKeys().getId()) +"/" + item.getId() + "/free";


        try {
            bitmap = QRcode.encodeAsBitmap(dataForQrCode, BarcodeFormat.QR_CODE, 200, 200);

            View dialog = getLayoutInflater().inflate(R.layout.dialod_with_qr_code, null);

            ImageView imgQRcode = dialog.findViewById(R.id.imvQRcodeDetailPresentDialog);
            imgQRcode.setImageBitmap(bitmap);

            TextView tv = dialog.findViewById(R.id.tvTxtDialogByFirst);
            tv.setText(text2);

            AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
            builder.setPositiveButton("Готово", (dialog1, which) -> {
                presentPresenter.byPresent();
                firstDialogWithQRcode.dismiss();
            })
                    .setNegativeButton("Отмена", (dialog12, which) -> firstDialogWithQRcode.dismiss());
            builder.setView(dialog);
            firstDialogWithQRcode = builder.create();
            firstDialogWithQRcode.show();

            Button bt = firstDialogWithQRcode.getButton(DialogInterface.BUTTON_POSITIVE);
            Button bt1 = firstDialogWithQRcode.getButton(DialogInterface.BUTTON_NEGATIVE);
            bt.setTextColor(Color.rgb(254, 194, 15));
            bt1.setTextColor(Color.rgb(254, 194, 15));


        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void acsessDialog(){
        String acsess = "";
        if(typeBuy.equals("buy")){
            acsess = "С Вашего счета будет списано " + item.getPrice() + " баллов после завершения операции.\n Приятного вечера";
        }
        else
            acsess = "Подарок будет списан после завершения операции.\n Приятного вечера";


        View dialog = getLayoutInflater().inflate(R.layout.dialog_second_detail_present, null);

        TextView textView = dialog.findViewById(R.id.tvSecondDialog);
        textView.setText(acsess);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setPositiveButton("ОК", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                secondDialogWithAcsess.dismiss();
                presentPresenter.cloceDetailPresent();
            }
        });
        builder.setView(dialog);
        secondDialogWithAcsess = builder.create();
        secondDialogWithAcsess.show();

        Button bt = secondDialogWithAcsess.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

    }
}
