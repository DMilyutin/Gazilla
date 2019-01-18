package com.gazilla.mihail.gazillaj.ui.main.stock.StockNewFriend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.utils.Initialization;

public class StockNewFriendActivity extends AppCompatActivity {

    TextView tvReferer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_new_friend);

        tvReferer = findViewById(R.id.tvRefererNewFriend);

        if (Initialization.userWithKeys.getRefererLink()!=null)
            tvReferer.setText(Initialization.userWithKeys.getRefererLink());
        else
            tvReferer.setText("Отсутсвует");

    }
}
