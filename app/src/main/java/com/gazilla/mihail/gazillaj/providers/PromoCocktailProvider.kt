package com.gazilla.mihail.gazillaj.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.QTYCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.SuccessCallBack
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.QTY
import com.gazilla.mihail.gazillaj.pojo.Success
import com.gazilla.mihail.gazillaj.presenters.PromoCocktailPresenter
import javax.inject.Inject

class PromoCocktailProvider(private val promoCocktailPresenter: PromoCocktailPresenter) {

    @Inject
    lateinit var repositoryApi: RepositoryApi
    private val userWithKeys = App.userWithKeys

    init {
        App.appComponent.inject(this)
    }


    fun myStars(){
        repositoryApi.myStarsPromoCoctail(userWithKeys.publickey, signatur(userWithKeys.privatekey, ""),
                object : QTYCallBack{
                    override fun myQTY(qty: QTY?) {
                       promoCocktailPresenter.starsFromServer(qty!!)
                    }

                    override fun errorCallBack(error: String) {
                        Log.i("Loog", "PromoCocktailProvider.myStars.errorCallBack - $error")
                        promoCocktailPresenter.showMessage(error)
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                promoCocktailPresenter.showMessage(throwable.message.toString())
                Log.i("Loog", "PromoCocktailProvider.myStars.throwableCallBack - ${throwable.message.toString()}")
            }
        })
    }

    fun sendPromoCode(code: String, force: Boolean){
        repositoryApi.sendPromoCodeCoctail(code, force, userWithKeys.publickey, signatur(userWithKeys.privatekey, "code=$code&force=$force"),
                object : SuccessCallBack{
                    override fun successResponse(success: Success?) {
                        promoCocktailPresenter.responseSendPromoCode(success!!)
                    }

                    override fun errorCallBack(error: String) {
                        promoCocktailPresenter.showMessage(error)
                        Log.i("Loog", "PromoCocktailProvider.sendPromoCode.errorCallBack - $error")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                promoCocktailPresenter.showMessage(throwable.message.toString())
                Log.i("Loog", "PromoCocktailProvider.sendPromoCode.throwableCallBack - ${throwable.message.toString()}")
            }
        })
    }

}