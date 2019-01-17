package com.gazilla.mihail.gazillaj.ui.main.presents.tabPresent;

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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;

import com.gazilla.mihail.gazillaj.POJO.ImgGazilla;
import com.gazilla.mihail.gazillaj.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.POJO.Success;
import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.model.interactor.PresentsInteractor;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsPresenter;
import com.gazilla.mihail.gazillaj.presentation.main.presents.PresentsView;
import com.gazilla.mihail.gazillaj.ui.detail.present.DetailPresentActivity;
import com.gazilla.mihail.gazillaj.ui.main.presents.Adapter.GifsAdapter;
import com.gazilla.mihail.gazillaj.ui.main.presents.Adapter.PresentsAdapter;
import com.gazilla.mihail.gazillaj.utils.Initialization;
import com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack;
import com.gazilla.mihail.gazillaj.utils.callBacks.SuccessCallBack;

import java.util.HashMap;
import java.util.List;

@SuppressWarnings("FinalizeCalledExplicitly")
public class PresentsFragment extends Fragment implements PresentsView {

    public static final String ARG_PAGE = "ARG_PAGE_ONE";
    private int mPage = 1;

    private ExpandableListView expandableListView;
    private PresentsAdapter presentAdapter;
    private PresentsPresenter presentsPresenter;

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
        expandableListView.setGroupIndicator(null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        presentsPresenter.initMenu();




        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                MenuItem item = presentAdapter.getChild(groupPosition, childPosition);

                Intent intent = new Intent(getActivity(), DetailPresentActivity.class);
                intent.putExtra("SelectedItem", item);
                intent.putExtra("Type", "buy");
                startActivity(intent);

                return true;
            }
        });

    }





    @Override
    public void setAdapterPresents(List<MenuCategory> categories, List<ImgGazilla> imgGazillaList) {
        HashMap<Integer, Boolean> favoritt = new HashMap<>();
        int[] favor = Initialization.userWithKeys.getFavorites();


        for(int iCategories = 0; iCategories < categories.size(); iCategories++ ){

            for(int iItem = 0; iItem<categories.get(iCategories).getItems().size(); iItem++){

                Integer id = categories.get(iCategories).getItems().get(iItem).getId();

                for (Integer aFavor : favor) {

                    if (id.equals(aFavor)){

                        favoritt.put(id, true);
                        Log.i("Loog", id +"!!! равны menu_id_2 !!! " + favoritt.get(2));
                    }
                    else{
                        favoritt.put(id, false);
                    }
                }
            }
        }
        presentAdapter = new PresentsAdapter(getContext(), categories, imgGazillaList);
        expandableListView.setAdapter(presentAdapter);

    }

    @Override
    public void setAdapterGifts(List<MenuItem> gifts) {

    }


}
