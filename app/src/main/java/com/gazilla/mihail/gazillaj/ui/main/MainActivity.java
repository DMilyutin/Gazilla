package com.gazilla.mihail.gazillaj.ui.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.presentation.MainPresentation;
import com.gazilla.mihail.gazillaj.presentation.main.presentation.MainView;
import com.gazilla.mihail.gazillaj.ui.account.AccountActivity;
import com.gazilla.mihail.gazillaj.ui.main.card.CardFragment;
import com.gazilla.mihail.gazillaj.ui.main.contacts.ContactsFragment;
import com.gazilla.mihail.gazillaj.ui.main.presents.Presents;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockFragment;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.Initialization;

/**
 * Главная активити приложения с меню
 * */

public class MainActivity extends AppCompatActivity implements MainView {
    /**  */
    /** Пресентор данной активити */
    public static MainPresentation mainPresentation;

    private ImageView imgOpenAccount;
    private TextView nameFragment;
    private TextView tvScore;

    /**  Фрагменты для меню
     * CardFragment - меню с QR кодом, рулеткой
     * Presents - меню с подарками
     * StockFragment - меню с акциями
     * ContactsFragment - меню с контактами
     * */
    private CardFragment cardFragment;
    private Presents presentsF;
    private StockFragment stockFragment;
    private ContactsFragment contactsFragment;

    private FragmentTransaction fragmentTransaction;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Log.i("Loog", "MainActivity создана");

        if(mainPresentation==null)
            mainPresentation = new MainPresentation(this);

        /** Поле с баллами */
        tvScore = findViewById(R.id.tvScoreMainActivity);
        /** Картинка для входа в активити управления аккаунтом */
        imgOpenAccount = findViewById(R.id.imgAccount);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNavMainMenu);

        /** Поле для установки заголовка меню */
        nameFragment = findViewById(R.id.tvNameFragment);

        cardFragment = new CardFragment();
        presentsF = new Presents();
        stockFragment = new StockFragment();
        contactsFragment = new ContactsFragment();

        fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fragmentTransaction.add(R.id.fragmentLayoutMainMenu, cardFragment);
        fragmentTransaction.commit();

        /** Обновление всех полей User */
        mainPresentation.updateUserInfo();

        tvScore.setText(String.valueOf(Initialization.userWithKeys.getScore()));

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.menu_card:
                        /** Запуск меню с кодом и рулеткой */
                        fragmentTransaction.replace(R.id.fragmentLayoutMainMenu, cardFragment);
                        nameFragment.setText("");
                        imgOpenAccount.setVisibility(View.VISIBLE);
                        mainPresentation.updateUserInfo();
                        break;
                    case R.id.menu_presents:
                        /** Запуск меню с падарками  */
                        fragmentTransaction.replace(R.id.fragmentLayoutMainMenu, presentsF);
                        imgOpenAccount.setVisibility(View.GONE);
                        nameFragment.setText("Подарки");
                        mainPresentation.updateUserInfo();
                        break;
                    case R.id.menu_stock:
                        /** Запуск меню с акциями */
                        fragmentTransaction.replace(R.id.fragmentLayoutMainMenu, stockFragment);
                        imgOpenAccount.setVisibility(View.GONE);
                        nameFragment.setText("Акции и новости");
                        break;
                    case R.id.menu_contacts:
                        /**  Запуск меню с контактами */
                        fragmentTransaction.replace(R.id.fragmentLayoutMainMenu, contactsFragment);
                        imgOpenAccount.setVisibility(View.GONE);
                        nameFragment.setText("О нас");
                        break;
                }
                //fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                return true;
            }
        });
        imgOpenAccount.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AccountActivity.class);
            startActivity(intent);

        });

    }

    /** Метод обновления баллов */
    @Override
    public void updateInfo(int score) {
        Log.i("Loog", "обновление баллов");
        tvScore.setText(String.valueOf(score));

    }

    @Override
    public void showErrorDialog(String error) {
        new AppDialogs().warningDialog(this, error);
    }

}
