package com.gazilla.mihail.gazillaj.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.views.PromoFragmentView

@InjectViewState
class PromoPresenter : MvpPresenter<PromoFragmentView>(){

    fun setPromoList(){
        if (App.promoFromServer.promoWithImg!=null&&App.promoFromServer.coctaiPromoId!=null){
            val z = App.promoFromServer.coctaiPromoId
            var listPromo : MutableList<PromoWithImg> = arrayListOf()
            App.promoFromServer.promoWithImg.forEach {
                if (it.id!= z[0]&&it.id!= z[1]&&it.id!= z[2])
                    listPromo.add(it)
            }
            viewState.setPromoList(listPromo)
        }
        else
            viewState.showMessageDialog("Ошибка загрузки Акций")
    }

    fun openDetailPromo(promo: PromoWithImg){
        viewState.openDetailPromo(promo)
    }
}