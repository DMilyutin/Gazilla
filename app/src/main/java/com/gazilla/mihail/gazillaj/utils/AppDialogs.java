package com.gazilla.mihail.gazillaj.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;

public class AppDialogs {

    private AlertDialog alertDialog;

    public void errorDialog(Context context, String error, String location){

        LayoutInflater inflater = LayoutInflater.from(context);
        View viewDialog = inflater.inflate(R.layout.dialog_error, null);

        TextView textView= viewDialog.findViewById(R.id.tvErrorDialogError);
        error = error + "\n\nОтправить отчет об ошибке?";
        textView.setText(error);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setPositiveButton("Отпрвить", (dialog, which) -> {
           //отправка отчета об ошибке
            // Нужно отправить место ошибки, тест ошибки

        }).setNegativeButton("Закрыть", (dialog1, which) -> {
            alertDialog.dismiss();
            alertDialog=null;
        });

        builder.setView(viewDialog);
        alertDialog = builder.create();
        alertDialog.show();

        Button bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

    }

    public void warningDialog(Context context, String warningMess, String txtPosBt){
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

        Button bt = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

        Button bt2 = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        bt2.setTextColor(Color.rgb(254, 194, 15));
    }


}
