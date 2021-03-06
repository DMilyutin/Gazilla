package com.gazilla.mihail.gazillaj.ui.main.stock.StockNewFriend;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.utils.Initialization;

public class StockNewFriendActivity extends AppCompatActivity {

    private TextView tvReferer;
    private ImageView imgCopy;
    private Button btSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_new_friend);

        tvReferer = findViewById(R.id.tvRefererNewFriend);
        imgCopy = findViewById(R.id.imgCopyReferLink);
        btSend = findViewById(R.id.btSendPril);

        if (Initialization.userWithKeys.getRefererLink()!=null)
            tvReferer.setText(Initialization.userWithKeys.getRefererLink());
        else
            tvReferer.setText("Отсутсвует");

        imgCopy.setOnClickListener(v -> {
            ClipboardManager clipboardManager = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("", tvReferer.getText().toString());
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(this, "Код скоприрован", Toast.LENGTH_SHORT).show();
        });

        btSend.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "M.gazilla-lounge.ru \nПромокод: " + tvReferer.getText().toString());
            sendIntent.setType("text/plain");
            startActivity(Intent.createChooser(sendIntent,"Поделиться"));
        });
    }
}
