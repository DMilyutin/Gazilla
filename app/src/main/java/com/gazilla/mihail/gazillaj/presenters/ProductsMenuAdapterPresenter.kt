package com.gazilla.mihail.gazillaj.presenters

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.MenuCategory
import com.gazilla.mihail.gazillaj.pojo.Success
import com.gazilla.mihail.gazillaj.views.ProductsForPointsAdapterView
import javax.inject.Inject

class ProductsMenuAdapterPresenter(private val productsForPointsAdapterView: ProductsForPointsAdapterView) {

    @Inject lateinit var repositoryApi: RepositoryApi

    private val userWithKeys = App.userWithKeys

    init {
        App.appComponent.inject(this)
    }

    fun favoriteProduct(isFavoriteNow : Boolean, idProduct: Int){
        if (isFavoriteNow)
            delFavoriteProduct(idProduct)
        else
            addFavoriteProduct(idProduct)
    }

    private fun addFavoriteProduct(idProduct: Int){
        repositoryApi.addFavoriteItem(publickey = userWithKeys.publickey, id = idProduct,  signature = signatur(userWithKeys.privatekey, "id=$idProduct"),
                successCallBack = object : SuccessCallBack {
                    override fun successResponse(success: Success?) {
                        productsForPointsAdapterView.showMessage("Товар добавлен в избранное")
                        productsForPointsAdapterView.updateList(true, idProduct)
                    }

                    override fun errorCallBack(error: String) {

                    }

                },failCallBack =  object : FailCallBack  {
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", " addFavoriteProduct.throwableCallBack ${throwable.message.toString()}")
                BugReport().sendBugInfo(throwable.message.toString(), "ProductsMenuAdapterPresenter.addFavoriteProduct.throwableCallBack")
            }

        })
    }

    private fun delFavoriteProduct(idProduct: Int){
        repositoryApi.delFavoritItem(publickey = userWithKeys.publickey, id = idProduct,  signature = signatur(userWithKeys.privatekey, "id=$idProduct"),
                successCallBack = object : SuccessCallBack {
                    override fun successResponse(success: Success?) {
                        productsForPointsAdapterView.showMessage("Товар удален из избранного")
                        productsForPointsAdapterView.updateList(false, idProduct)
                    }

                    override fun errorCallBack(error: String) {

                    }

                },failCallBack =  object : FailCallBack  {
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "ProductsMenuAdapterPresenter.delFavoriteProduct.throwableCallBack")
                Log.i("Loog", " addFavoriteProduct.throwableCallBack ${throwable.message.toString()}")
            }

        })
    }

    fun favorite(): IntArray {

        if (App.menuFromServer.favoriteMenu.isNotEmpty()){
            return App.menuFromServer.favoriteMenu
        }

        val categories = App.menuFromServer.appMenuCategory
        val favoriteFromServer = App.userWithKeys.favorites

        val max = maxId(categories)

        val mapFavorite = IntArray(max + 1)
        Log.i("Loog", "max = $max, favorite size = ${mapFavorite.size}")
        for (z in 0 until max + 1)
            mapFavorite[z] = 0

        for (iCategories in categories.indices) {

            for (iItem in 0 until categories[iCategories].items.size) {

                val id = categories[iCategories].items[iItem].id

                for (i in favoriteFromServer.indices) {
                    if (favoriteFromServer[i] == id){
                        mapFavorite[id] = 1
                    }
                }
            }
        }
        App.menuFromServer.favoriteMenu = mapFavorite
        return mapFavorite
    }

    private fun maxId(categories: List<MenuCategory>): Int{
        var max = 0
        for (iCategories in categories.indices) {

            for (iItem in 0 until categories[iCategories].items.size) {
                if (max < categories[iCategories].items[iItem].id)
                    max = categories[iCategories].items[iItem].id
            }
        }
        return max
    }
}