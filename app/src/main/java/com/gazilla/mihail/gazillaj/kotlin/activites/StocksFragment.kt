package com.gazilla.mihail.gazillaj.kotlin.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.activites.Proms.*
import com.gazilla.mihail.gazillaj.kotlin.adapters.PromoFragmentAdapter
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.kotlin.presenters.PromoPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.PromoFragmentView
import kotlinx.android.synthetic.main.fragment_stock.*

class StocksFragment: MvpAppCompatFragment(), PromoFragmentView {

    private lateinit var mContext: Context

    @InjectPresenter
    lateinit var promoPresenter: PromoPresenter

    lateinit var promoAdapter: PromoFragmentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stock, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        promoPresenter.setPromoList()

        lvPromoFragment.setOnItemClickListener { _, _, position, _ ->
            val promoWithImg:PromoWithImg = promoAdapter.getItem(position)
            promoPresenter.openDetailPromo(promoWithImg)
        }
    }

    override fun setPromoList(promoList: List<PromoWithImg>) {
        promoAdapter = PromoFragmentAdapter(promoList, mContext)
        lvPromoFragment.adapter = promoAdapter

    }
    override fun openDetailPromo(promo: PromoWithImg){
        var intent = when(promo.id){
            1 -> Intent(mContext, PromoNewFriendActivity::class.java)
            2 -> Intent(mContext, PromoHoaxActivity::class.java)
            3 -> Intent(mContext, PromoSmokerpassActivity::class.java)
            4 -> Intent(mContext, PromoKitchenActivity::class.java)
            5 -> Intent(mContext, PromoPlayStationActivity::class.java)
            else -> {
                Intent(mContext, PromoBaseActivity::class.java)
            }
        }
        intent.putExtra("promo", promo)
        startActivity(intent)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }
}