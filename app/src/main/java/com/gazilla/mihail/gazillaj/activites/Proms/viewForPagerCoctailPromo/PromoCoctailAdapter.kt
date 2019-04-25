package com.gazilla.mihail.gazillaj.activites.Proms.viewForPagerCoctailPromo

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class PromoCoctailAdapter(private var fragmentManager: FragmentManager, var numOfTabs: Int): FragmentPagerAdapter(fragmentManager)  {

    override fun getItem(pos: Int): Fragment {
       return when(pos){
            0 -> PagerCoctailPromo1()
            1 -> PagerCoctailPromo2()
            else -> PagerCoctailPromo3()
        }
    }

    override fun getCount(): Int
        = numOfTabs
}