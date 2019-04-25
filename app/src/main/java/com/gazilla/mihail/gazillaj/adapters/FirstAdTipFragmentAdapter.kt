package com.gazilla.mihail.gazillaj.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gazilla.mihail.gazillaj.activites.FirstTipsApp.FirstAdTipFragment
import com.gazilla.mihail.gazillaj.activites.FirstTipsApp.SecondAdTipFragment
import com.gazilla.mihail.gazillaj.activites.FirstTipsApp.ThirdAdTipFragment

class FirstAdTipFragmentAdapter(private var fragmentManager: FragmentManager, var numOfTabs: Int): FragmentPagerAdapter(fragmentManager) {


    override fun getItem(pos: Int): Fragment {
        return when (pos) {
            0 -> FirstAdTipFragment()
            1 -> SecondAdTipFragment()
            else -> ThirdAdTipFragment()
        }

    }

    override fun getCount(): Int
        =  numOfTabs
}