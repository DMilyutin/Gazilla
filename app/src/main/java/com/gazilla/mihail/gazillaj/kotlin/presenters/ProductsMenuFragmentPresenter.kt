package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.support.v4.app.FragmentManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.kotlin.adapters.ProductsMenuFragmentAdapter
import com.gazilla.mihail.gazillaj.kotlin.views.ProductsMenuFragmentView

@InjectViewState
class ProductsMenuFragmentPresenter: MvpPresenter<ProductsMenuFragmentView>() {

    fun newTabSelected(position: Int){
        viewState.setCurrentItem(position)
    }

    fun setAdapter(adapter: ProductsMenuFragmentAdapter){
        viewState.setViewpagerAdapter(adapter)
    }
}