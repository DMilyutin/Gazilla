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

public class ErrorDialog {

    private Context context;

    private AlertDialog errorDialog;

    public ErrorDialog(Context context) {
        this.context = context;
    }

    public void detailTargetProgress(String error){



        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog = inflater.inflate(R.layout.dialog_error, null);

        TextView textView= dialog.findViewById(R.id.tvErrorDialogError);

        textView.setText(error);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setPositiveButton("Понятно!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                errorDialog.dismiss();

            }
        });
        builder.setView(dialog);
        errorDialog = builder.create();
        errorDialog.show();

        Button bt = errorDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

    }
}
