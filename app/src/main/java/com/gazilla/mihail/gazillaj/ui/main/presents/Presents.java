package com.gazilla.mihail.gazillaj.ui.main.presents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.gazilla.mihail.gazillaj.R;
import com.gazilla.mihail.gazillaj.ui.main.presents.tabPresent.GiftsFragment;
import com.gazilla.mihail.gazillaj.ui.main.presents.tabPresent.PresentsFragment;
import com.gazilla.mihail.gazillaj.utils.Initialization;

public class Presents extends Fragment {

    private GiftsFragment giftsFragment;
    private PresentsFragment presentsFragment;
    private FragmentTransaction fragmentTransaction;
    private TabLayout tabMenuPresents;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        giftsFragment = new GiftsFragment();
        presentsFragment = new PresentsFragment();

        fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.viewpager, presentsFragment);
        fragmentTransaction.commit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.present_fragment_2, null);

        tabMenuPresents = view.findViewById(R.id.tlMenuPresents);
        tabMenuPresents.addTab(tabMenuPresents.newTab().setText("За баллы"), 0);
        tabMenuPresents.addTab(tabMenuPresents.newTab().setText("Подарки"), 1);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(Initialization.userWithKeys.getFavorites()!=null)
        Log.i("Loog", "favorit [] -" + Initialization.userWithKeys.getFavorites().length);


        tabMenuPresents.setOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                switch (tab.getPosition()){
                    case 0:{
                        fragmentTransaction1.replace(R.id.viewpager, presentsFragment);

                        break;
                    }
                    case 1:{
                        fragmentTransaction1.replace(R.id.viewpager, giftsFragment);

                        break;
                    }
                }
                fragmentTransaction1.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



    }

}
