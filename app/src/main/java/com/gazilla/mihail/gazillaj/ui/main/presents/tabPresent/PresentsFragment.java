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
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsView;
import com.gazilla.mihail.gazillaj.ui.detail.present.DetailPresentActivity;
import com.gazilla.mihail.gazillaj.ui.main.presents.Adapter.PresentsAdapter;

import java.util.List;

@SuppressWarnings("FinalizeCalledExplicitly")
public class PresentsFragment extends Fragment implements PresentsView {

    private Context mContext;

    public static final String ARG_PAGE = "ARG_PAGE_ONE";
    private int mPage = 1;
    /** Лист с меню */
    private ExpandableListView expandableListView;
    /** Адаптер для листа */
    private PresentsAdapter presentAdapter;
    /** Пресентро данного фрагмента */
    private PresentsPresenter presentsPresenter;

    private ProgressBar pbLoadMenu;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(presentsPresenter == null)
            presentsPresenter = new PresentsPresenter(this, new PresentsInteractor(), null);

        if (getArguments() != null) {
            mPage = getArguments().getInt(ARG_PAGE);
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.prezents_fragment, null);
        Log.i("Loog", "fragment Presents");
        expandableListView = view.findViewById(R.id.exListPresents);
        pbLoadMenu = view.findViewById(R.id.pbLoadMenu);
        expandableListView.setGroupIndicator(null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presentsPresenter.menuFromServer();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                MenuItem item = presentAdapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent(mContext, DetailPresentActivity.class);
                intent.putExtra("SelectedItem", item);
                intent.putExtra("Type", "buy");
                startActivity(intent);

                return true;
            }
        });

    }

    @Override
    public void setAdapterPresents(List<MenuCategory> categories) {

        presentAdapter = new PresentsAdapter(mContext, categories);
        expandableListView.setAdapter(presentAdapter);

    }

    @Override
    public void setAdapterGifts(List<MenuItem> gifts) {

    }

    @Override
    public void setVisibleProgressBar() {
        pbLoadMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void setUnvisibleProgressBar() {
        pbLoadMenu.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

}
