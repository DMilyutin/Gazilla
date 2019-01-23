package com.gazilla.mihail.gazillaj.ui.main.presents.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.R;

import java.util.List;

public class GifsAdapter extends BaseAdapter {



    private Context context;
    private List<MenuItem> menuItems;

    public GifsAdapter(Context context, List<MenuItem> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public MenuItem getItem(int position) {
        return menuItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return menuItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.for_list_my_gifts, null);
        }
        MenuItem menuItem = getPromo(position);

        ((TextView) convertView.findViewById(R.id.tvNameGift)).setText(menuItem.getName());
        ((TextView) convertView.findViewById(R.id.tvDecriptionGift)).setText(menuItem.getDescription());
        return convertView;
    }

    private MenuItem getPromo(int poss){
        return menuItems.get(poss);
    }
}
