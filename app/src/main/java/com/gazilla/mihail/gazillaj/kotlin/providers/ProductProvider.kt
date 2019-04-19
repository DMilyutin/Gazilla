package com.gazilla.mihail.gazillaj.kotlin.providers

import android.graphics.BitmapFactory
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.StaticCallBack
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.presenters.ProductPresenter
import okhttp3.ResponseBody
import javax.inject.Inject

class ProductProvider(private val productPresenter: ProductPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }


    fun getItemImageFromServer(id: String){
        repositoryApi.getStaticFromServer("menu", id, object : StaticCallBack{
            override fun myStatic(responseBody: ResponseBody?) {
                val b = responseBody!!.bytes()
                productPresenter.setItemImg(BitmapFactory.decodeByteArray(b, 0, b.size))
            }

            override fun errorCallBack(error: String) {
               productPresenter.showErrorMessage("Фото временнно недоступно")
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "ProductProvider.getItemImageFromServer.throwableCallBack ")
                productPresenter.showErrorMessage(throwable.message.toString())
            }
        })

    }
}