package com.gazilla.mihail.gazillaj.kotlin.views

import com.arellomobile.mvp.MvpView
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg

interface PromoFragmentView: MvpView {
    fun setPromoList(promoList: List<PromoWithImg>)
    fun openDetailPromo(promo: PromoWithImg)
}