package com.gazilla.mihail.gazillaj.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.adapters.ProductsMenuFragmentAdapter
import com.gazilla.mihail.gazillaj.views.ProductsMenuFragmentView

@InjectViewState
class ProductsMenuFragmentPresenter: MvpPresenter<ProductsMenuFragmentView>() {

    fun newTabSelected(position: Int){
        viewState.setCurrentItem(position)
    }

    fun setAdapter(adapter: ProductsMenuFragmentAdapter){
        viewState.setViewpagerAdapter(adapter)
    }
}