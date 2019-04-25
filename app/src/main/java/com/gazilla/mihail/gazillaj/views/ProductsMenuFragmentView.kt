package com.gazilla.mihail.gazillaj.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gazilla.mihail.gazillaj.adapters.ProductsMenuFragmentAdapter

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface ProductsMenuFragmentView: MvpView {

    fun setCurrentItem(position: Int)
    fun setViewpagerAdapter(adapter: ProductsMenuFragmentAdapter)

}