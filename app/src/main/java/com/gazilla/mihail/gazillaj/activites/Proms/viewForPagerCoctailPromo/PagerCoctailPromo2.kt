package com.gazilla.mihail.gazillaj.activites.Proms.viewForPagerCoctailPromo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.helps.App
import kotlinx.android.synthetic.main.for_view_pager_coctail_promo2.*

class PagerCoctailPromo2 : MvpAppCompatFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.for_view_pager_coctail_promo2, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        App.promoFromServer.promoWithImg.forEach {
            if (it.id == App.promoFromServer.coctaiPromoId[1])
                imgVPCoctailPromo2.setImageBitmap(BitmapFactory.decodeByteArray(it.img, 0, it.img.size))
        }

    }
}