package com.gazilla.mihail.gazillaj.ui.main.card;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.card.CardView;
import com.gazilla.mihail.gazillaj.ui.main.card.adapter.AdapterLvlDracon;
import com.gazilla.mihail.gazillaj.ui.reserve.ReserveActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.DialogDetailProgress;

import java.util.Map;

import static com.gazilla.mihail.gazillaj.ui.main.MainActivity.mainPresentation;

/** Фрагмент с рулеткой и кодом */

public class CardFragment extends Fragment implements CardView, View.OnClickListener {

    private Context mContext;
    /** Пресентор данной фрагмента */
    private CardPresenter cardPresenter;
    /** Адаптер для списка уровней системы лояльности */
    private AdapterLvlDracon adapterLvlDracon;

    /** Класс для вызова диалогов */
    private AppDialogs appDialogs;

    /** Картинка с рулеткой */
    private ImageView ruletka;
    /** Поле с указанием кол-ва вращений */
    private TextView spins;
    /** Text view с надписью "Подарок на рулетке " */
    private TextView tvPresentCard;
    /** Картинка белого обода (Подсказка) */
    private ImageView imgWhiteCircle;

    /** указание на шкале потраченных денег */
    private TextView tvSum;
    private ProgressBar pbCardFragment;
    /** картинка с кодом */
    private ImageView imvQRcode;

    private ListView lvLvlDracon;

    private DialogDetailProgress dialogDetailProgress;
    /** Обновление по тапу вниз */
    private SwipeRefreshLayout swipeRefreshLayout;

    private ScrollView scCard;
    /** Класс с подсказками */
    private Tips cardTips;


    //------------------------ Подсказки----------------------------------------------
    /** Поля с подсказками, скрытые методом setVisibli(GONE) */
    private ConstraintLayout clWheelTip;
    private ConstraintLayout clBalanceTip;
    private ConstraintLayout clNacopTip;
    private ConstraintLayout clShowTipLvlDracon;
    private ConstraintLayout clRegistrTip;
    private ConstraintLayout clReserveTip;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(cardPresenter==null)
            cardPresenter = new CardPresenter(this, mContext);
        dialogDetailProgress = new DialogDetailProgress(mContext);
        appDialogs = new AppDialogs();
        cardTips = new Tips(this, mContext);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_fragment2, null);

        scCard = view.findViewById(R.id.scCard);
        swipeRefreshLayout = view.findViewById(R.id.refresh);
        ruletka = view.findViewById(R.id.imgRuletka);
        ruletka.setOnClickListener(this);
        imgWhiteCircle = view.findViewById(R.id.imgWhiteCircle);

        lvLvlDracon = view.findViewById(R.id.lvLvlDrakonProgressCardFragment);
        ConstraintLayout miniProgress = view.findViewById(R.id.miniProgressLayout);
        miniProgress.setOnClickListener(this);

        tvSum = view.findViewById(R.id.tvSum);
        spins = view.findViewById(R.id.tvSpins);
        tvPresentCard = view.findViewById(R.id.tvPresentCard);
        pbCardFragment = view.findViewById(R.id.pbCardFragment);
        imvQRcode = view.findViewById(R.id.imvIDclient);

        Button btReserve = view.findViewById(R.id.btOpenReserve);
        btReserve.setOnClickListener(this);

        // -------------------------------------подсказки -------------------------------

        clWheelTip = view.findViewById(R.id.cl_wheel_tip);
        clBalanceTip = view.findViewById(R.id.cl_balanse_tip);
        clNacopTip = view.findViewById(R.id.cl_nacop_tip);
        clRegistrTip = view.findViewById(R.id.clRegistrTip);
        clReserveTip = view.findViewById(R.id.clReserveTip);
        clShowTipLvlDracon = view.findViewById(R.id.clShowTipLvlDracon);
        TextView tvTipNext = view.findViewById(R.id.btTipNext);
        TextView tvRegistrTipNext = view.findViewById(R.id.tvRegistrTipNext);
        TextView tvReserveTipNext = view.findViewById(R.id.tvReserveTipNext);
        TextView tvStopTips = view.findViewById(R.id.tvStopTips);

        tvStopTips.setOnClickListener(this);
        tvTipNext.setOnClickListener(this);
        tvRegistrTipNext.setOnClickListener(this);
        tvReserveTipNext.setOnClickListener(this);

        //---------------------------------------------------------------------------------


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /** Запуск подсказок */
        cardTips.initTips();

        /** ЗАпуск первого окна с возможностями приложения */
        cardPresenter.initCardFragment();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i("Loog", "Старт обновления");
                swipeRefreshLayout.setRefreshing(true);
                mainPresentation.updateUserInfo();
                cardPresenter.initCardFragment();
                swipeRefreshLayout.setRefreshing(false);
                }});

        lvLvlDracon.setOnItemClickListener((parent, view, position, id) -> {
            /** Открытие детальной информации из списка уровней лояльности */
            /** Определние нажатого уровня */
            int key = (int) adapterLvlDracon.getItemId(position);
            /** {@link AdapterLvlDracon#AdapterLvlDracon(Context, int, Map)} */
            dialogDetailProgress.detailTargetProgress(key, cardTips.getFirstStartApp(), this); });
    }

    /** обработчик нажатий - фрагмент */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgRuletka: {
                /** Нажатие на рудетку */
                cardPresenter.prepearWheel();
                ruletka.setClickable(false);
                break;
            }
            case R.id.miniProgressLayout:{
                /** Нажатие на поле с прогрессом потраченных денег для открытия уровней лояльности */
                int i = lvLvlDracon.getVisibility();
                if (i==8){
                    lvLvlDracon.setVisibility(View.VISIBLE);
                    cardTips.nextTips();
                }
                else
                    lvLvlDracon.setVisibility(View.GONE);
                break;
            }
            case R.id.tvStopTips:{
                /** Нажатие на подсказке  */
                wheelTip(false);
                break;
            }
            case R.id.btOpenReserve:{
                /** Нажатие кнопки резерва  */
                Intent intent = new Intent(mContext, ReserveActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btTipNext:{
                /** Нажатие на подсказке  */
                cardTips.nextTips();
                break;
            }
            case R.id.tvRegistrTipNext:{
                /** Нажатие на подсказке  */
                cardTips.nextTips();
                scCard.scrollBy(0,2000);
                break;
            }
            case R.id.tvReserveTipNext:{
                /** Нажатие на подсказке  */
                cardTips.nextTips();
                break;
            }
        }
    }

    /** Метод запуска вращения рулетки*/
    @Override
    public void startWheeling(){
        ruletka.setColorFilter(Color.TRANSPARENT);
        tvPresentCard.setVisibility(View.GONE);
        imgWhiteCircle.setVisibility(View.GONE);

        Animation animation = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_ruletka);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                wheelTip(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                /** При завершении вращений рулетки обновление информации о User и показ выигрыша */
                cardPresenter.myWinn();
                mainPresentation.updateUserInfo();
                ruletka.setClickable(true);

            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        ruletka.startAnimation(animation);

        }


    /** Метод установки параметов в прогресе потраченных денег */
    @Override
    public void setValueProgressBar(int maxValue, int userValue) {
        tvSum.setText(String.valueOf(userValue) + "/" + String.valueOf(maxValue));
        pbCardFragment.setMax(maxValue);
        pbCardFragment.setProgress(userValue);
    }

    @Override
    public void initListWithLvl(int myLvl, Map<Integer, Integer> mapLvl) {
        adapterLvlDracon = new AdapterLvlDracon(mContext, myLvl, mapLvl);
        lvLvlDracon.setAdapter(adapterLvlDracon);
    }


    @Override
    public void setQRcode(Bitmap bitmap) {
        imvQRcode.setImageBitmap(bitmap);

    }

    /** Установка кол-ва вращение рулетки и внешний вид рулетки при доступных вращениях */
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

    /** метод показа выигрыша */
    @Override
    public void myWin(String win, String res) {
        appDialogs.dialogWinWheel(mContext, win, res, cardTips.getFirstStartApp(), this);
        cardPresenter.initCardFragment();

    }

    /** Метод установки колеса взависимости от уровня User */
    @Override
    public void initLvlForKoleso(int res) {
        ruletka.setImageResource(res);
    }

    /** Методы показа подсказок */
    @Override
    public void firstDialogTip() {
        appDialogs.dialogFirstStart(mContext);

    }

    @Override
    public void wheelTip(Boolean show) {
        if (show){
            clWheelTip.setVisibility(View.VISIBLE);
        }
        else {
            clWheelTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void balanceTip(Boolean show) {
        if (show){
            clBalanceTip.setVisibility(View.VISIBLE);
        }
        else {
            clBalanceTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void nacopTip(Boolean show) {
        if (show){
            clNacopTip.setVisibility(View.VISIBLE);
        }
        else {
            clNacopTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void lvlDraconTip(Boolean show) {
        if (show){
            clShowTipLvlDracon.setVisibility(View.VISIBLE);
        }
        else {
            clShowTipLvlDracon.setVisibility(View.GONE);
        }
    }

    @Override
    public void registrTip(Boolean show) {
        if (show){
            scCard.scrollBy(0,-2000);
            clRegistrTip.setVisibility(View.VISIBLE);
        }
        else {
            clRegistrTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void reserveTip(Boolean show) {
        if (show){
            clReserveTip.setVisibility(View.VISIBLE);
        }
        else {
            clReserveTip.setVisibility(View.GONE);
        }
    }

    @Override
    public void nextTip() {
        cardTips.nextTips();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}