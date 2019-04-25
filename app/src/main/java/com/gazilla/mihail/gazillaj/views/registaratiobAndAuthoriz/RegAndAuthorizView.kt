package com.gazilla.mihail.gazillaj.views.registaratiobAndAuthoriz

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType


@StateStrategyType(value = AddToEndSingleStrategy::class)
interface RegAndAuthorizView : MvpView {

    fun initFragment(firstFragment: Fragment)
    fun replaceFragment(fragment: Fragment, newTextForBt: String)
}