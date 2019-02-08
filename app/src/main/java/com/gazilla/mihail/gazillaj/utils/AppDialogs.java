package com.gazilla.mihail.gazillaj.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardView;
import com.gazilla.mihail.gazillaj.ui.main.MainActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class AppDialogs {

    private AlertDialog alertDialog;

    public void errorDialog(Context context, String error, String location){

        LayoutInflater inflater = LayoutInflater.from(context);
        View viewDialog = inflater.inflate(R.layout.dialog_error, null);

        TextView textView= viewDialog.findViewById(R.id.tvErrorDialogError);
        textView.setText(error);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setNegativeButton("Закрыть", (dialog1, which) -> {
            alertDialog.dismiss();
            alertDialog=null;
        });

        builder.setView(viewDialog);
        alertDialog = builder.create();
        alertDialog.show();

        Button bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

    }

    public void warningDialog(Context context, String warningMess){
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewDialog = inflater.inflate(R.layout.dialog_error, null);

        TextView textView= viewDialog.findViewById(R.id.tvErrorDialogError);
        textView.setText(warningMess);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setNegativeButton("Закрыть", (dialog1, which) -> {
            alertDialog.dismiss();
            alertDialog=null;
        });

        builder.setView(viewDialog);
        alertDialog = builder.create();
        alertDialog.show();

        Button bt2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        bt2.setTextColor(Color.rgb(254, 194, 15));
    }

    public void loadingDialog(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View viewDialog = inflater.inflate(R.layout.dialog_loading, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);

        builder.setView(viewDialog);
        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dialogWithQRCode(Context context, String dataForQrCode){
        String text = "Дождитесь, пока сотрудник\n" +
                "считает Ваш QR-код";
        try {
            LayoutInflater inflater = LayoutInflater.from(context);

            Bitmap bitmap = QRcode.encodeAsBitmap(dataForQrCode, BarcodeFormat.QR_CODE, 200, 200);

            View dialog = inflater.inflate(R.layout.dialod_with_qr_code, null);

            ImageView imgQRcode = dialog.findViewById(R.id.imvQRcodeDetailPresentDialog);
            imgQRcode.setImageBitmap(bitmap);

            TextView tv = dialog.findViewById(R.id.tvTxtDialogByFirst);
            tv.setText(text);

            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
            builder.setPositiveButton("Готово", (dialog1, which) -> {
                //presentPresenter.byPresent();
                //firstDialogWithQRcode.dismiss();
            })
                    .setNegativeButton("Отмена", (dialog12, which) -> alertDialog.dismiss());
            builder.setView(dialog);
            alertDialog = builder.create();
            alertDialog.show();

            Button bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
            Button bt1 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
            bt.setTextColor(Color.rgb(254, 194, 15));
            bt1.setTextColor(Color.rgb(254, 194, 15));

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    public void dialogWinWheel(Context context, String win, String res, Boolean showBalanceTip, CardView cardView){

        ImageLoader imageLoader ;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(context));

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog = inflater.inflate(R.layout.dialog_win_wheel, null);

        ImageView winImg = dialog.findViewById(R.id.imgWinDialog);
        TextView tvWin= dialog.findViewById(R.id.tvWinDialog);

        imageLoader.displayImage(res, winImg);
        tvWin.setText(win);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);

        builder.setPositiveButton("Спасибо!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (showBalanceTip)
                    cardView.nextTip();
                alertDialog.dismiss();
                alertDialog=null;


            }
        });

        builder.setView(dialog);
        alertDialog = builder.create();
        alertDialog.show();

        Button bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));
        //String res = "drawable://" + R.drawable.gaz;
    }

    public void dialogFirstStart(Context context){
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog = inflater.inflate(R.layout.dialog_first_opennig, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);

        builder.setPositiveButton("Продолжить!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
                alertDialog=null;
            }
        });

        builder.setView(dialog);
        alertDialog = builder.create();
        alertDialog.show();

        Button bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));
    }

    public void clouseDialog(){
        if (alertDialog!=null)
            alertDialog.dismiss();
    }


    //----------------------------------------------------

   /* private void firstStart(){
        if (!first){

            clWhillTip(true);
        }
    }

    private void clWhillTip(Boolean show){
        if (show){
            clWheelTip.setVisibility(View.VISIBLE);

        }


        else
            clWheelTip.setVisibility(View.GONE);
    }

    private void clBalanceTip(Boolean show){
        if (show)
            clBalanceTip.setVisibility(View.VISIBLE);
        else
            clBalanceTip.setVisibility(View.GONE);
    }

    private void clNacopTip(Boolean show){
        if (show){
            int[] location = new int[2];
            clNacopTip.setVisibility(View.VISIBLE);
            clNacopTip.getLocationOnScreen(location);
            scCard.scrollBy(0, location[1]);
        }
        else{
            clNacopTip.setVisibility(View.GONE);
        }
    }

    private void clShowTipLvlDracon(Boolean show){
        if (show)
            clShowTipLvlDracon.setVisibility(View.VISIBLE);
        else{
            clShowTipLvlDracon.setVisibility(View.GONE);
            sharedPref.saveFirstStart(true);
        }
    }

    private void registTip(Boolean show){
        if (show){
            scCard.scrollBy(0,-2000);
            clRegistrTip.setVisibility(View.VISIBLE);
        }
        else{
            clRegistrTip.setVisibility(View.GONE);
            scCard.scrollBy(0, 2000);
        }

    }

    private void reserveTip(Boolean show){
        if (show)
            clReserveTip.setVisibility(View.VISIBLE);
        else{
            clReserveTip.setVisibility(View.GONE);
            first=true;
            sharedPref.saveFirstStart(true);
        }
    }*/

}
