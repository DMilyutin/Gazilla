package com.gazilla.mihail.gazillaj.ui.main.card;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.CardInteractor;

import com.gazilla.mihail.gazillaj.presentation.main.card.CardPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardView;
import com.gazilla.mihail.gazillaj.ui.main.MainActivity;
import com.gazilla.mihail.gazillaj.ui.main.card.adapter.AdapterLvlDracon;
import com.gazilla.mihail.gazillaj.ui.reserve.ReserveActivity;
import com.gazilla.mihail.gazillaj.utils.DialogDetailProgress;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Map;


public class CardFragment extends Fragment implements CardView {

    String winType;
    String winBody;
    Boolean onAnimationEndd;

    private ImageView ruletka;

    private TextView spins;
    private TextView tvPresentCard;

    private ImageView imgWhiteCircle;


    private AlertDialog winWheelDialog;

    private TextView tvSum;
    private ProgressBar pbCardFragment;
    private ImageView imvQRcode;

    private ListView lvLvlDracon;
    private ConstraintLayout miniProgress;
    private AdapterLvlDracon adapterLvlDracon;

    private CardPresenter cardPresenter;
    private DialogDetailProgress dialogDetailProgress;

    private Button btReserve;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(cardPresenter==null)
            cardPresenter = new CardPresenter(this, new CardInteractor(getContext()));
        if(getContext()!=null)
        dialogDetailProgress = new DialogDetailProgress(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment2, null);

        ruletka = view.findViewById(R.id.imgRuletka);
        imgWhiteCircle = view.findViewById(R.id.imgWhiteCircle);
        cardPresenter.initRuletca();
        lvLvlDracon = view.findViewById(R.id.lvLvlDrakonProgressCardFragment);
        miniProgress = view.findViewById(R.id.miniProgressLayout);


        tvSum = view.findViewById(R.id.tvSum);
        spins = view.findViewById(R.id.tvSpins);
        tvPresentCard = view.findViewById(R.id.tvPresentCard);
        pbCardFragment = view.findViewById(R.id.pbCardFragment);
        imvQRcode = view.findViewById(R.id.imvIDclient);

        btReserve = view.findViewById(R.id.btOpenReserve);

        cardPresenter.idClientForQRcode();
        cardPresenter.myProgress();

        upDateSpins();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        upDateSpins();


        ruletka.setOnClickListener(v -> {
            if(Integer.valueOf(spins.getText().toString())>0){
                ruletka.setColorFilter(Color.TRANSPARENT);
                tvPresentCard.setVisibility(View.GONE);
                imgWhiteCircle.setVisibility(View.GONE);
                ruletka.setClickable(false);
                startWheeling();
            }

        });

        miniProgress.setOnClickListener(v -> {
            int i = lvLvlDracon.getVisibility();
            if (i==8)
                lvLvlDracon.setVisibility(View.VISIBLE);
            else
                lvLvlDracon.setVisibility(View.GONE);
        });

        lvLvlDracon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int scor = adapterLvlDracon.getItem(position+1);

                int key = 0;

                if(scor == 0) {
                    key = 1;

                }
                if(scor == 10000) {
                    key = 2;
                }
                if(scor == 30000) {
                    key = 3;
                }
                if(scor == 100000) {
                    key = 4;
                }
                if(scor == 300000) {
                    key = 5;
                }

                dialogDetailProgress.detailTargetProgress(key);
            }
        });


        btReserve.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ReserveActivity.class);
            startActivity(intent);
        });
    }

    private void startWheeling(){
        if(getContext()!=null){
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_ruletka);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    onAnimationEndd = false;
                    cardPresenter.wheeling();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(onAnimationEndd){
                        showWin(winType, winBody);
                        MainActivity.mainPresentation.updateUserInfo();
                        cardPresenter.myProgress();
                        upDateSpins();
                        ruletka.setClickable(true);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            ruletka.startAnimation(animation);

        }


        }





    private void upDateSpins(){
        cardPresenter.mySpins();
    }

    @Override
    public void setValueProgressBar(int maxValue, int userValue) {
        String progress = userValue + "/" + maxValue;
        tvSum.setText(progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pbCardFragment.setMin(0);
        }
        pbCardFragment.setMax(maxValue);
        pbCardFragment.setProgress(userValue);


    }

    @Override
    public void initListWithLvl(int myLvl, Map<Integer, Integer> mapLvl) {
        adapterLvlDracon = new AdapterLvlDracon(getContext(), myLvl, mapLvl);
        lvLvlDracon.setAdapter(adapterLvlDracon);
    }


    @Override
    public void setQRcode(Bitmap bitmap) {
        imvQRcode.setImageBitmap(bitmap);

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setSpins(int qty) {
        spins.setText(String.valueOf(qty));
        if (qty>0){
            tvPresentCard.setVisibility(View.VISIBLE);
            imgWhiteCircle.setVisibility(View.VISIBLE);
            ruletka.setColorFilter(0x50000000);
        }
        else{
            tvPresentCard.setVisibility(View.GONE);
            imgWhiteCircle.setVisibility(View.GONE);
        }
    }

    @Override
    public void myWin(String type, String win, Bitmap bitmap) {

        winType = type;
        winBody = win;
        onAnimationEndd = true;
    }

    @Override
    public void initLvlForKoleso(int res) {
        ruletka.setImageResource(res);
    }

    private void showWin(String type, String win){


           LayoutInflater inflater = LayoutInflater.from(getContext());
           View dialog = inflater.inflate(R.layout.dialog_win_wheel, null);

           ImageView winImg = dialog.findViewById(R.id.imgWinDialog);
           TextView tvWin= dialog.findViewById(R.id.tvWinDialog);

           if (type.equals("point"))
               winImg.setImageResource(R.drawable.gaz);
           // else
           // winImg.setImageBitmap(bitmap);


           tvWin.setText(win);

           AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);

           builder.setPositiveButton("Спасибо!", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   winType = "";
                   winBody = "";
                   winWheelDialog.dismiss();
               }
           });

           builder.setView(dialog);
           winWheelDialog = builder.create();
           winWheelDialog.show();

           Button bt = winWheelDialog.getButton(DialogInterface.BUTTON_POSITIVE);
           bt.setTextColor(Color.rgb(254, 194, 15));

           upDateSpins();
       }

}
