package com.gazilla.mihail.gazillaj.kotlin.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.kotlin.views.PromoFragmentView

@InjectViewState
class PromoPresenter : MvpPresenter<PromoFragmentView>(){

    fun setPromoList(){
        viewState.setPromoList(App.promoFromServer.promoWithImg)
    }

    fun openDetailPromo(promo: PromoWithImg){
        viewState.openDetailPromo(promo)
    }
}