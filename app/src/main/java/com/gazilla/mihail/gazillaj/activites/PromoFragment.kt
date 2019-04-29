package com.gazilla.mihail.gazillaj.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.activites.Proms.*
import com.gazilla.mihail.gazillaj.adapters.PromoFragmentAdapter
import com.gazilla.mihail.gazillaj.helps.AppDialogs
import com.gazilla.mihail.gazillaj.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.presenters.PromoPresenter
import com.gazilla.mihail.gazillaj.views.PromoFragmentView
import kotlinx.android.synthetic.main.fragment_stock.*

class PromoFragment: MvpAppCompatFragment(), PromoFragmentView {


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

        promoList.forEach {
            Log.i("Loog", "id promo - ${it.id}")
            Log.i("Loog", "id name - ${it.name}")
            Log.i("Loog", "_____________________________")
        }

    }
    override fun openDetailPromo(promo: PromoWithImg){
        val intent = when(promo.id){
            1 -> Intent(mContext, PromoNewFriendActivity::class.java)
            2 -> Intent(mContext, PromoHoaxActivity::class.java)
            3 -> Intent(mContext, PromoSmokerpassActivity::class.java)
            //4
            //5 -> Intent(mContext, PromoPlayStationActivity::class.java)
            //6
            7 -> Intent(mContext, PromoKitchenActivity::class.java)
            8 -> Intent(mContext, PromoEveryDayActivity::class.java)
            9 -> Intent(mContext, PromoCocktailActivity::class.java)
            13 -> Intent(mContext, PromoTouchTimeActivity::class.java)
            else -> {
                Intent(mContext, PromoBaseActivity::class.java)
            }
        }

        intent.putExtra("id promo", promo.id)
        startActivity(intent)
    }

    override fun showMessageDialog(message: String) {
        AppDialogs().messageDialog(mContext, message)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }
}