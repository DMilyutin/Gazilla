package com.gazilla.mihail.gazillaj.kotlin.activites

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
import com.gazilla.mihail.gazillaj.kotlin.adapters.ProductsFreeAdapter
import com.gazilla.mihail.gazillaj.kotlin.helps.AppDialogs
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuItem
import com.gazilla.mihail.gazillaj.kotlin.pojo.SmartMenuItem
import com.gazilla.mihail.gazillaj.kotlin.presenters.ProductsFreeFragmentPresenter
import com.gazilla.mihail.gazillaj.kotlin.views.ProductsFreeFragmentView
import kotlinx.android.synthetic.main.fragment_gifts.*

class ProductsFreeFragment: MvpAppCompatFragment(), ProductsFreeFragmentView {

    lateinit var productsFreeAdapter: ProductsFreeAdapter

    @InjectPresenter lateinit var productsFreeFragmentPresenter: ProductsFreeFragmentPresenter

    private lateinit var mContext: Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gifts, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        productsFreeFragmentPresenter.myGifts()


        lvGifts.setOnItemClickListener { _, _, position, _ ->
            val smartMenuItem = productsFreeAdapter.getItem(position)
            val menuItem = MenuItem(smartMenuItem.id, smartMenuItem.name, smartMenuItem.price, smartMenuItem.weight, smartMenuItem.description)

            val intent = Intent(mContext, ProductActivity::class.java)
            intent.putExtra("SelectedItem", menuItem)
            intent.putExtra("Type", "free")
            startActivity(intent)
        }

    }

    override fun setGiftsAdapter(menuItemList: List<SmartMenuItem>) {
        productsFreeAdapter = ProductsFreeAdapter(menuItemList, mContext)
        lvGifts.adapter = productsFreeAdapter
    }

    override fun showMessageDialog(message: String) {
        AppDialogs().messageDialog(mContext, message)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }
}