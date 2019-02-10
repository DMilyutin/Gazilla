package com.gazilla.mihail.gazillaj.ui.main.stock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.gazilla.mihail.gazillaj.model.data.db.AppDatabase;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockKitchen.StockKitchenActivity;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockPlayStation.StoakPlayStationActivity;
import com.gazilla.mihail.gazillaj.utils.AppDialogs;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.PromoInteractor;
import com.gazilla.mihail.gazillaj.presentation.main.stock.PromoPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.stock.PromoView;
import com.gazilla.mihail.gazillaj.ui.main.stock.Adapter.StocksAdapter;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockDragonway.DragonwayActivity;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockHoax.StockHoaxActivity;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockNewFriend.StockNewFriendActivity;
import com.gazilla.mihail.gazillaj.ui.main.stock.StockSmokerpass.SmokerpassActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

public class StockFragment extends Fragment implements PromoView {

    private Context mContext;

    private ImageLoader imageLoader;

    private ConstraintLayout clPromoDragonWay;
    private ConstraintLayout clPromoSmokerpass;
    private ConstraintLayout clPromoHoax;
    private ConstraintLayout clFriend;
    private ConstraintLayout clKitchen;
    private ConstraintLayout clPlayStation;

    private ImageView imgNewFrendStockPromo;
    private ImageView imgDragonwayStockPromo;
    private ImageView imgSmokerpassStockPromo;
    private ImageView imgKitchenStockPromo;
    private ImageView imgPlayStationStockPromo;


    private PromoPresenter promoPresenter;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();

        if(promoPresenter == null)
            promoPresenter = new PromoPresenter(this, new PromoInteractor());
        try {
            imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
        }catch (NullPointerException ex){
            new AppDialogs().warningDialog(mContext, "Ошибка загрузки картинок");
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.stock_fragment, null);

        clPromoDragonWay = view.findViewById(R.id.clPromoDragonWay);
        clPromoSmokerpass = view.findViewById(R.id.clPromoSmokerpass);
        clPromoHoax = view.findViewById(R.id.clPromoHoax);
        clFriend = view.findViewById(R.id.clFriend);
        clKitchen = view.findViewById(R.id.clKitchen);
        clPlayStation = view.findViewById(R.id.clPlayStation);

        imgNewFrendStockPromo   = view.findViewById(R.id.imgNewFrendStockPromo);
        imgDragonwayStockPromo  = view.findViewById(R.id.imgDragonwayStockPromo);
        imgSmokerpassStockPromo = view.findViewById(R.id.imgSmokerpassStockPromo);
        imgKitchenStockPromo    = view.findViewById(R.id.imgKitchenStockPromo);
        imgPlayStationStockPromo    = view.findViewById(R.id.imgPlayStationStockPromo);

        String res = "drawable://" + R.drawable.photo_new_friend;
        imageLoader.displayImage(res, imgNewFrendStockPromo);

        res = "drawable://" + R.drawable.dragon_hoax;
        imageLoader.displayImage(res, imgDragonwayStockPromo);

        res = "drawable://" + R.drawable.photo_smoke;
        imageLoader.displayImage(res, imgSmokerpassStockPromo);

        res = "drawable://" + R.drawable.appetizer;
        imageLoader.displayImage(res, imgKitchenStockPromo);

        res = "drawable://" + R.drawable.stock_play_station_photo;
        imageLoader.displayImage(res, imgPlayStationStockPromo);



        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
       // promoPresenter.myPromo();

        clPromoSmokerpass.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SmokerpassActivity.class );
            startActivity(intent);
        });

        clPromoDragonWay.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, DragonwayActivity.class);
            startActivity(intent);
        });

        clPromoHoax.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, StockHoaxActivity.class);
            startActivity(intent);
        });

        clFriend.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, StockNewFriendActivity.class);
            startActivity(intent);
        });

        clKitchen.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, StockKitchenActivity.class);
            startActivity(intent);
        });

        clPlayStation.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, StoakPlayStationActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void setPromoAdapter(List<PromoItem> promoItems) {
        //stocksAdapter = new StocksAdapter(getContext(), promoItems);
        //lvStocks.setAdapter(stocksAdapter);
    }

    @Override
    public void showErrorCode(int error) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
