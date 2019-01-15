package com.gazilla.mihail.gazillaj.ui.main.stock.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.R;

import java.util.List;

public class StocksAdapter extends BaseAdapter {

    private Context context;
    private List<PromoItem> promoItems;

    public StocksAdapter(Context context, List<PromoItem> promoItems) {
        this.context = context;
        this.promoItems = promoItems;
    }

    @Override
    public int getCount() {
        return promoItems.size();
    }

    @Override
    public PromoItem getItem(int position) {
        return promoItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return promoItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.for_list_promo_item, null);
        }
        PromoItem promoItem = getPromo(position);

        ((TextView) convertView.findViewById(R.id.tvNamePromoForList)).setText(promoItem.getName());
        ((TextView) convertView.findViewById(R.id.tvDecriptPromoForList)).setText(promoItem.getDescription());
        return convertView;
    }

    private PromoItem getPromo(int poss){
        return promoItems.get(poss);
    }
}
