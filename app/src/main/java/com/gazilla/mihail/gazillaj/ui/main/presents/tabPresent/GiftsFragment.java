package com.gazilla.mihail.gazillaj.ui.main.presents.tabPresent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gazilla.mihail.gazillaj.utils.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsView;
import com.gazilla.mihail.gazillaj.ui.detail.present.DetailPresentActivity;
import com.gazilla.mihail.gazillaj.ui.main.presents.Adapter.GifsAdapter;

import java.util.List;

public class GiftsFragment extends Fragment implements PresentsView {

    private Context mContext;

    public static final String ARG_PAGE = "ARG_PAGE_TWO";
    private int mPage = 2;
    /** Адаптер для листа с подарками */
    private GifsAdapter gifsAdapter;
    private ListView giftsList;
    /** Пресентер данного фрагмента */
    private PresentsPresenter presentsPresenter;

    public static GiftsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        GiftsFragment fragment = new GiftsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (presentsPresenter == null)
            presentsPresenter = new PresentsPresenter(this, new PresentsInteractor(), null);

        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.gifts_fragment, null);
        Log.i("Loog", "fragment Gifts");
        giftsList = view.findViewById(R.id.lvGifts);


        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presentsPresenter.initGifts();

        giftsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MenuItem menuItem = gifsAdapter.getItem(position);
                Intent intent = new Intent(mContext, DetailPresentActivity.class);
                intent.putExtra("SelectedItem", menuItem);
                intent.putExtra("Type", "free");
                startActivity(intent);
            }
        });
    }


    @Override
    public void setAdapterPresents(List<MenuCategory> categories) {

    }

    @Override
    public void setAdapterGifts(List<MenuItem> gifts) {
        gifsAdapter = new GifsAdapter(mContext , gifts);
        giftsList.setAdapter(gifsAdapter);
    }

    @Override
    public void setVisibleProgressBar() {

    }

    @Override
    public void setUnvisibleProgressBar() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }
}
