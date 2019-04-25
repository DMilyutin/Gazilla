package com.gazilla.mihail.gazillaj.activites

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.adapters.ProductsMenuFragmentAdapter
import com.gazilla.mihail.gazillaj.presenters.ProductsMenuFragmentPresenter
import com.gazilla.mihail.gazillaj.views.ProductsMenuFragmentView
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsMenuFragment : MvpAppCompatFragment(), ProductsMenuFragmentView {


    @InjectPresenter
    lateinit var productsMenuFragmentPresenter: ProductsMenuFragmentPresenter

    private lateinit var productsMenuFragmentAdapter: ProductsMenuFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("Loog", "ProductsMenuFragment.onCreateView")
        return inflater.inflate(R.layout.fragment_products, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.i("Loog", "ProductsMenuFragment.onActivityCreated")

        viewpagerProducts.offscreenPageLimit = 2

        tlMenuPresents.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {

            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
               productsMenuFragmentPresenter.newTabSelected(tab!!.position)
            }
        })



    }

    override fun setCurrentItem(position: Int) {
        viewpagerProducts.currentItem = position
    }

    override fun setViewpagerAdapter(adapter: ProductsMenuFragmentAdapter) {
        viewpagerProducts.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        productsMenuFragmentAdapter = ProductsMenuFragmentAdapter(childFragmentManager, tlMenuPresents.tabCount)
        productsMenuFragmentPresenter.setAdapter(productsMenuFragmentAdapter)
        viewpagerProducts.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tlMenuPresents))
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.i("Loog", "ProductsMenuFragment.onDestroy")
    }

    override fun onStop() {
        super.onStop()
        viewpagerProducts.clearOnPageChangeListeners()
        viewpagerProducts.removeAllViews()

    }

}