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

public class DialogDetailProgress {

    private Context context;

    private String lvlS;
    private String one;
    private String two;
    private String fri;
    private String fol;
    private String fif;

    private AlertDialog detailProgressDialog;

    public DialogDetailProgress(Context context) {
        this.context = context;

    }

    private void init(int lvl) {
        switch (lvl){
            case 1 :
                detailInfo1();
                break;
            case 2 :
                detailInfo2();
                break;
            case 3 :
                detailInfo3();
                break;
            case 4 :
                detailInfo4();
                break;
            case 5 :
                detailInfo5();
                break;
        }
    }

    public void detailTargetProgress(int lvl){

        init(lvl);

        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog = inflater.inflate(R.layout.dialog_detail_progress, null);

        TextView textView= dialog.findViewById(R.id.tvFifDetailProgress);
        if(lvl==1)
            textView.setText(fif);
        else
            textView.setVisibility(View.GONE);

        ((TextView) dialog.findViewById(R.id.tvLVLDetailProgress)).setText(lvlS);

        ((TextView) dialog.findViewById(R.id.tvOneDetailProgress)).setText(one);
        ((TextView) dialog.findViewById(R.id.tvTwoDetailProgress)).setText(two);
        ((TextView) dialog.findViewById(R.id.tvThreeDetailProgress)).setText(fri);
        ((TextView) dialog.findViewById(R.id.tvFourDetailProgress)).setText(fol);

        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.MyDialogTheme);
        builder.setPositiveButton("Понятно!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                detailProgressDialog.dismiss();

            }
        });
        builder.setView(dialog);
        detailProgressDialog = builder.create();
        detailProgressDialog.show();

        Button bt = detailProgressDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        bt.setTextColor(Color.rgb(254, 194, 15));

    }

    private void detailInfo1(){
        // 1 LVL
        lvlS="1-й дракон";
         one ="- Начисление 8% от суммы чека бонусными баллами";
         two ="- Возможность играть в колесо дракона 1 раз в день и за каждые 750 рублей в чеке";
         fri ="- Доступна акция дары дракона";
         fol ="- Приветственный подарок";
         fif ="- Возможность принимать участие в оффлайн розыгрышах от заведения";

    }

    private void detailInfo2(){
        // menu_id_2 LVL
        lvlS="menu_id_2-й дракон";
         one ="Начисление 11% от суммы чека бонусными баллами";
         two ="- Увеличенные призы в колесе дракона, повышенная вероятность выиграть подарок";
         fri ="- Бесплатный кальян в подарок при переходе на этот уровень";
         fol ="- Удваивается число реферальных баллов за приглашение друга (включая уже приглашенных)";
         fif ="";

    }

    private void detailInfo3(){
        // 3 LVL
        lvlS="3-й дракон";
         one ="- Начисление 14% от суммы чека бонусными баллами";
         two ="- Дополнительная попытка в колесе дракона начисляется за каждые 500 рублей в чеке";
         fri ="- Начисляется 1500 бонусных баллов при переходе на этот уровень";
         fol ="- Специальные акции для обладателей этого статуса";
         fif ="";

    }

    private void detailInfo4(){
        // 4 LVL
        lvlS="4-й дракон";
         one ="- Начисление 17% от суммы чека бонусными баллами";
         two ="- Большие призы в колесе дракона, возможность поучаствовать в розыгрыше специального приза";
         fri ="- 4000 бонусных баллов при переходе на этот уровень";
         fol ="- Специальные мероприятия для обладателей этого статуса";
         fif ="";

    }

    private void detailInfo5(){
        // 5 LVL
        lvlS="5-й дракон";
         one ="- Начисление 20% от суммы чека бонусными баллами";
         two ="- В колесо дракона можно играть дважды в день";
         fri ="- 10000 бонусных баллов при переходе на этот уровень и специальный подарок";
         fol ="- Возможность брать с собой гостей на специальные мероприятия";
         fif ="";

    }
}
