package com.gazilla.mihail.gazillaj.kotlin.views.registaratiobAndAuthoriz

import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpFragment
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.gazilla.mihail.gazillaj.kotlin.activites.registrationAndAuthorization.RegistrationFragment
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy



@StateStrategyType(value = AddToEndSingleStrategy::class)
interface RegAndAuthorizView : MvpView {

    fun initFragment(firstFragment: Fragment)
    fun replaceFragment(fragment: Fragment, newTextForBt: String)
}