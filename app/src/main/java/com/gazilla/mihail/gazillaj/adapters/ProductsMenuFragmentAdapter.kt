package com.gazilla.mihail.gazillaj.adapters


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.gazilla.mihail.gazillaj.activites.ProductsForPointsFragment
import com.gazilla.mihail.gazillaj.activites.ProductsFreeFragment

class ProductsMenuFragmentAdapter(private var fragmentManager: FragmentManager, var numOfTabs: Int) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(pos: Int): Fragment {
        return if (pos==0)
            ProductsForPointsFragment()
        else
            ProductsFreeFragment()
    }

    override fun getCount(): Int {
       return numOfTabs
    }
}