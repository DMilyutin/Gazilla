package com.gazilla.mihail.gazillaj.ui.main.stock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.gazilla.mihail.gazillaj.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.presentation.main.stock.PromoPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.stock.PromoView;
import com.gazilla.mihail.gazillaj.ui.main.stock.Adapter.StocksAdapter;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockDragonway.DragonwayActivity;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockSmokerpass.SmokerpassActivity;
import com.gazilla.mihail.gazillaj.utils.Initialization;

import java.util.List;

public class StockFragment extends Fragment implements PromoView {

    private ListView lvStocks;
    private StocksAdapter stocksAdapter;
    private ConstraintLayout constraintLayout;
    private ConstraintLayout constraintLayout2;

    private PromoPresenter promoPresenter;

    private TextView tvReferer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(promoPresenter == null)
            promoPresenter = new PromoPresenter(this, new PromoInteractor());


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_fragment, null);
        constraintLayout = view.findViewById(R.id.clPromoDragonWay);
        constraintLayout2 = view.findViewById(R.id.clPromoSmokerpass);

                lvStocks = view.findViewById(R.id.lvStocks);

        tvReferer = view.findViewById(R.id.tvMyRefererLink);


        if (Initialization.userWithKeys.getRefererLink()!=null)
            tvReferer.setText(Initialization.userWithKeys.getRefererLink());
        else
            tvReferer.setText("Отсутсвует");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // promoPresenter.myPromo();

        constraintLayout2.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SmokerpassActivity.class );
            startActivity(intent);
        });

        constraintLayout.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), DragonwayActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void setPromoAdapter(List<PromoItem> promoItems) {
        stocksAdapter = new StocksAdapter(getContext(), promoItems);
        lvStocks.setAdapter(stocksAdapter);
    }

    @Override
    public void showErrorCode(int error) {

    }
}
