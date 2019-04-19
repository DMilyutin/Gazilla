package com.gazilla.mihail.gazillaj.kotlin.helps

import android.util.Log
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.PromoCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.StaticCallBack
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoItem
import com.gazilla.mihail.gazillaj.kotlin.pojo.PromoWithImg
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

class PromoFromServer {

    @Singleton
    var promoWithImg : List<PromoWithImg>

    @Inject
    lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
        promoWithImg = ArrayList()
    }

    fun donloadStocks(){
        repositoryApi.ollPromo(App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, ""),
                object : PromoCallBack{
                    override fun myPromo(promoItem: List<PromoItem>?) {
                        Log.i("Loog", "кол-во акций на сервере - ${promoItem!!.size}")
                        donloadImgForStoks(promoItem)
                    }

                    override fun errorCallBack(error: String) {
                        Log.i("Loog", "Ошибка загрузки промо с сервера - $error")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "Ошибка загрузки промо с сервера t - ${throwable.message}")
            }
        })
    }

    fun donloadImgForStoks(promoItem: List<PromoItem>){
        var listPromo : MutableList<PromoWithImg> = arrayListOf()

        promoItem.forEach {
            repositoryApi.getStaticFromServer("promo", it.id.toString(), object : StaticCallBack{
                override fun myStatic(responseBody: ResponseBody?) {
                    listPromo.add(PromoWithImg(it.id, it.name, it.promoType,it.shortDescription, it.description, responseBody!!.bytes()))

                    promoWithImg = listPromo
                    Log.i("Loog", "кол-во акций c картинками Mutable- ${listPromo.size}")
                    Log.i("Loog", "кол-во акций c картинками - ${promoWithImg.size}")

                }

                override fun errorCallBack(error: String) {
                    Log.i("Loog", "Ошибка загрузки картинки для промо - $error")
                }
            }, object : FailCallBack{
                override fun throwableCallBack(throwable: Throwable) {
                    Log.i("Loog", "Ошибка загрузки картинки для промо t - ${throwable.message}")
                }

            })
        }

        Log.i("Loog", "кол-во акций c картинками - ${promoWithImg.size}")

    }


}