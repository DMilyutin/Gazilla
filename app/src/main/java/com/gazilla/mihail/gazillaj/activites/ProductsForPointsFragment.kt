package com.gazilla.mihail.gazillaj.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.adapters.ProductsForPointsAdapter
import com.gazilla.mihail.gazillaj.presenters.ProductsForPointsFragmentPresenter
import com.gazilla.mihail.gazillaj.views.ProductsForPointsFragmentView
import kotlinx.android.synthetic.main.fragment_prezents.*

class ProductsForPointsFragment: MvpAppCompatFragment(), ProductsForPointsFragmentView {


    private lateinit var productsForPointsAdapter : ProductsForPointsAdapter
    private lateinit var mContext: Context
    private val productsForPointsFragmentPresenter = ProductsForPointsFragmentPresenter(this)

    init {
        Log.i("Loog", "ProductsForPointsFragment.init")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_prezents, null)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        productsForPointsFragmentPresenter.getAdapter(activity!!.applicationContext)

        exListProductsForPoints.setOnChildClickListener { _, _, groupPosition, childPosition, _ ->
            val menuItem = productsForPointsAdapter.getChild(groupPosition, childPosition)

            val intent = Intent(mContext, ProductActivity::class.java)
            intent.putExtra("SelectedItem",menuItem)
            intent.putExtra("Type","buy")
            startActivity(intent)

            return@setOnChildClickListener true
        }

    }

    override fun setAdapterProductsForPoints(productsForPointsAdapter: ProductsForPointsAdapter) {
        this.productsForPointsAdapter = productsForPointsAdapter
        exListProductsForPoints.setAdapter(productsForPointsAdapter)
    }

    override fun onResume() {
        super.onResume()
        //productsForPointsAdapter = ProductsForPointsAdapter(App.menuFromServer.appMenuCategory, activity!!.applicationContext)
        //exListProductsForPoints.setAdapter(productsForPointsAdapter)

        //productsForPointsFragmentPresenter.getAdapter(activity!!.applicationContext)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mContext = context!!
    }

}