package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.content.Context
import android.util.Log
import com.gazilla.mihail.gazillaj.kotlin.adapters.ProductsForPointsAdapter
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.views.ProductsForPointsFragmentView
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class ProductsForPointsFragmentPresenter(private val productsForPointsFragmentView: ProductsForPointsFragmentView) {

    //lateinit var productsForPointsAdapter : ProductsForPointsAdapter

    fun getAdapter(context: Context) {

        Observable.create(
                ObservableOnSubscribe<ProductsForPointsAdapter> { emitter ->

                    emitter.onNext(ProductsForPointsAdapter(App.menuFromServer.appMenuCategory, context)) })

                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<ProductsForPointsAdapter>() {

                    override fun onComplete() {
                        Log.i("Loog", "Загрузка адаптера завершена")
                    }

                    override fun onNext(t: ProductsForPointsAdapter) {
                        setCreateAdapter(t)
                        //productsForPointsAdapter = t
                    }

                    override fun onError(e: Throwable) {
                        Log.i("Loog", e.message)
                    }
                })
    }

    private fun setCreateAdapter(productsForPointsAdapter: ProductsForPointsAdapter){
        productsForPointsFragmentView.setAdapterProductsForPoints(productsForPointsAdapter)
    }

}