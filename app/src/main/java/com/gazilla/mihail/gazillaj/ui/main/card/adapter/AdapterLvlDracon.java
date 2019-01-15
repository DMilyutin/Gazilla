package com.gazilla.mihail.gazillaj.ui.main.card.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.R;

import java.util.List;
import java.util.Map;

public class AdapterLvlDracon extends BaseAdapter {

    private int lvl;
    private Map<Integer, Integer> mapLvl;
    private Context context;
    private int key;

    public AdapterLvlDracon(Context context, int lvl, Map<Integer, Integer> mapLvl) {
        this.context = context;
        this.lvl = lvl;
        this.mapLvl = mapLvl;
    }

    @Override
    public int getCount() {
        return mapLvl.size();
    }

    @Override
    public Integer getItem(int position) {
        return mapLvl.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lvl;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.for_list_lvl_user, null);
        }
        ImageView imgDracon = convertView.findViewById(R.id.imgDragonLvl);
        TextView txtName = convertView.findViewById(R.id.tvNameLvlForList);
        TextView txtDeckription = convertView.findViewById(R.id.tvDicriptionLvlForList);

        Log.i("Loog", "posiz - " + mapLvl.get(position+1));

         int scor = mapLvl.get(position+1);
             key = 0;

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

        Log.i("Loog", "key pos - " + key);

        String names = getName(key);
        String deckr = getDescription(key);
        int rDrawable = getImage(key);


        txtName.setText(names);
        txtDeckription.setText(deckr);

        imgDracon.setImageResource(rDrawable);

        if(lvl!=key){
            imgDracon.setColorFilter(0x99000000);
            txtName.setTextColor(Color.rgb(151,151,151));
            txtDeckription.setTextColor(Color.rgb(151,151,151));
        }
        else{
            imgDracon.setColorFilter(Color.TRANSPARENT);
            txtName.setTextColor(Color.WHITE);
            txtDeckription.setTextColor(Color.WHITE);
        }
        return convertView;
    }

    private String getName(int lvl){
        switch (lvl){
            case 1: return "1-й уровень";
            case 2: return "2-й уровень";
            case 3: return "3-й уровень";
            case 4: return "4-й уровень";
            case 5: return "5-й уровень";

        }
        return "Нет уровня";
    }

    private String getDescription(int lvl){
        switch (lvl){
            case 1: return "Начальный";
            case 2: return "от " + mapLvl.get(2).toString()+" р";
            case 3: return "от " + mapLvl.get(3).toString()+"р ";
            case 4: return "от " + mapLvl.get(4).toString()+"р ";
            case 5: return "от " + mapLvl.get(5).toString()+"р ";

        }
        return "Нет описания";
    }

    private int getImage(int lvl){
        switch (lvl) {
            case 1:
                return R.drawable.dragon1;
            case 2:
                return R.drawable.dragon2;
            case 3:
                return R.drawable.dragon3;
            case 4:
                return R.drawable.dragon4;
            case 5:
                return R.drawable.dragon5;

        }
        return R.drawable.dragon1;
    }

    public int getKey() {
        return key;
    }
}
