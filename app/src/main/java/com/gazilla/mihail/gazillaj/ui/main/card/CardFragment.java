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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.CardInteractor;

import com.gazilla.mihail.gazillaj.model.repository.SharedPref;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardView;
import com.gazilla.mihail.gazillaj.ui.main.MainActivity;
import com.gazilla.mihail.gazillaj.ui.main.card.adapter.AdapterLvlDracon;
import com.gazilla.mihail.gazillaj.ui.reserve.ReserveActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.DialogDetailProgress;
import com.gazilla.mihail.gazillaj.utils.MenuImg;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;


import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.Map;

import static com.gazilla.mihail.gazillaj.ui.main.MainActivity.mainPresentation;


public class CardFragment extends Fragment implements CardView {

    private String winType;
    private int winIdImg;
    private String winBody;
    private Boolean onAnimationEndd;

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

    private SwipeRefreshLayout swipeRefreshLayout;


    private SharedPref sharedPref;

    private ScrollView scCard;

    //------------------------ Подсказки----------------------------------------------
    private Boolean first;
    private ConstraintLayout clWheelTip;
    private ConstraintLayout clBalanceTip;
    private ConstraintLayout clNacopTip;
    private ConstraintLayout clShowTipLvlDracon;
    private ConstraintLayout clRegistrTip;
    private ConstraintLayout clReserveTip;

    private TextView tvTipNext;
    private TextView tvRegistrTipNext;
    private TextView tvReserveTipNext;

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

        scCard = view.findViewById(R.id.scCard);
        swipeRefreshLayout = view.findViewById(R.id.refresh);

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



        clShowTipLvlDracon = view.findViewById(R.id.clShowTipLvlDracon);

        sharedPref = new SharedPref(getContext());


        cardPresenter.idClientForQRcode();
        cardPresenter.myProgress();

        // -------------------------------------подсказки -------------------------------
        first = sharedPref.getFirstStart();
        //first = false;

        clWheelTip = view.findViewById(R.id.cl_wheel_tip);
        clBalanceTip = view.findViewById(R.id.cl_balanse_tip);
        clNacopTip = view.findViewById(R.id.cl_nacop_tip);
        clRegistrTip = view.findViewById(R.id.clRegistrTip);
        clReserveTip = view.findViewById(R.id.clReserveTip);

        tvTipNext = view.findViewById(R.id.btTipNext);
        tvRegistrTipNext = view.findViewById(R.id.tvRegistrTipNext);
        tvReserveTipNext = view.findViewById(R.id.tvReserveTipNext);

        //---------------------------------------------------------------------------------

        upDateSpins();
        Log.i("Loog", "Tip - " + first);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        try {
            upDateSpins();

            if(!first)
                openFirstDialog();



            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    Log.i("Loog", "Старт обновления");
                    swipeRefreshLayout.setRefreshing(true);
                    mainPresentation.updateUserInfo();
                    cardPresenter.myProgress();
                    swipeRefreshLayout.setRefreshing(false);
                }
            });

            ruletka.setOnClickListener(v -> {
                if(Integer.valueOf(spins.getText().toString())>0){
                    if (clWheelTip.getVisibility()==View.VISIBLE)
                        clWhillTip(false);
                    ruletka.setColorFilter(Color.TRANSPARENT);
                    tvPresentCard.setVisibility(View.GONE);
                    imgWhiteCircle.setVisibility(View.GONE);
                    ruletka.setClickable(false);
                    startWheeling();
                }

            });

            miniProgress.setOnClickListener(v -> {
                if (!first){
                    clNacopTip(false);
                }

                int i = lvLvlDracon.getVisibility();
                if (i==8){
                    lvLvlDracon.setVisibility(View.VISIBLE);
                    if (!first)
                        clShowTipLvlDracon(true);

                }
                else
                    lvLvlDracon.setVisibility(View.GONE);
            });

            lvLvlDracon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if(!first){
                        clShowTipLvlDracon(false);
                        registTip(true);

                    }

                    int key = (int) adapterLvlDracon.getItemId(position);
                    scCard.scrollBy(0,-2000);
                    dialogDetailProgress.detailTargetProgress(key);
                }
            });


            btReserve.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), ReserveActivity.class);
                startActivity(intent);
            });

            tvTipNext.setOnClickListener(v -> {
                clBalanceTip(false);
                clNacopTip(true);
            });

            tvRegistrTipNext.setOnClickListener(v -> {
                registTip(false);
                reserveTip(true);
                scCard.scrollBy(0,2000);
            });

            tvReserveTipNext.setOnClickListener(v -> {
                reserveTip(false);
            });


        }catch (NullPointerException ex){
            new AppDialogs().errorDialog(getContext(), ex.getMessage(), "CardFragment.onActivityCreated");
        }



    }

    private void startWheeling(){
        if(getContext()!=null){
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate_ruletka);

            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    onAnimationEndd = false;
                    cardPresenter.wheeling();
                    //cardPresenter.testShowWin();
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    if(onAnimationEndd){
                        showWin(winType, winBody);
                        mainPresentation.updateUserInfo();
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
        Log.i("Loog", "Обновление прогресс бара");
        String progress = userValue + "/" + maxValue;
        tvSum.setText(progress);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pbCardFragment.setMin(0);
        }
        pbCardFragment.setMax(maxValue);
        pbCardFragment.setProgress(userValue);

        upDateSpins();
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
    public void myWin(String type, String win, int id, Bitmap bitmap) {
        winIdImg = id;
        winType = type;
        winBody = win;
        onAnimationEndd = true;
    }

    @Override
    public void initLvlForKoleso(int res) {
        ruletka.setImageResource(res);
    }

    private void showWin(String type, String win){
         ImageLoader imageLoader ;
        imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(getContext()));

         MenuImg menuImg = new MenuImg();

           LayoutInflater inflater = LayoutInflater.from(getContext());
           View dialog = inflater.inflate(R.layout.dialog_win_wheel, null);

           ImageView winImg = dialog.findViewById(R.id.imgWinDialog);
           TextView tvWin= dialog.findViewById(R.id.tvWinDialog);

           if (type.equals("point")){
               winImg.setImageResource(R.drawable.gaz);
               tvWin.setText(win);
           }
           else{
               if(winIdImg!=0){
                   String res = "drawable://" + menuImg.getImg(winIdImg);

                   //((ImageView) finalConvertView1.findViewById(R.id.imgMiniItemMemu)).setImageResource(res);
                   imageLoader.displayImage(res, winImg);

               }

               else{
                   String res = "drawable://" + R.drawable.gaz;
                   imageLoader.displayImage(res, winImg);
               }

               tvWin.setText(win);
           }




           AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);

           builder.setPositiveButton("Спасибо!", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   winType = "";
                   winBody = "";
                   winWheelDialog.dismiss();
                   if (!first){
                       clBalanceTip(true);
                   }
               }
           });

           builder.setView(dialog);
           winWheelDialog = builder.create();
           winWheelDialog.show();

           Button bt = winWheelDialog.getButton(DialogInterface.BUTTON_POSITIVE);
           bt.setTextColor(Color.rgb(254, 194, 15));

           upDateSpins();
       }

       private void openFirstDialog(){

           LayoutInflater inflater = LayoutInflater.from(getContext());
           View dialog = inflater.inflate(R.layout.dialog_first_opennig, null);

           AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.MyDialogTheme);

           builder.setPositiveButton("Продолжить!", new DialogInterface.OnClickListener() {
               @Override
               public void onClick(DialogInterface dialog, int which) {
                   winWheelDialog.dismiss();
                   winWheelDialog = null;
                   firstStart();
               }
           });

           builder.setView(dialog);
           winWheelDialog = builder.create();
           winWheelDialog.show();

           MainActivity.cal++;

           Button bt = winWheelDialog.getButton(DialogInterface.BUTTON_POSITIVE);
           bt.setTextColor(Color.rgb(254, 194, 15));
       }

       private void firstStart(){
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
        if (show)
            clRegistrTip.setVisibility(View.VISIBLE);
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
    }
}
