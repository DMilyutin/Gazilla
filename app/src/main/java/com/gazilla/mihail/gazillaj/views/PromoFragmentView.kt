package com.gazilla.mihail.gazillaj.views

import com.arellomobile.mvp.MvpView
import com.gazilla.mihail.gazillaj.pojo.PromoWithImg

interface PromoFragmentView: MvpView {
    fun setPromoList(promoList: List<PromoWithImg>)
    fun openDetailPromo(promo: PromoWithImg)

    fun showMessageDialog(message: String)
}