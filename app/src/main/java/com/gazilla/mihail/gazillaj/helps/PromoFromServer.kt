package com.gazilla.mihail.gazillaj.helps

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.IntArrayCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.PromoCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.StaticCallBack
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.PromoItem
import com.gazilla.mihail.gazillaj.pojo.PromoWithImg
import com.gazilla.mihail.gazillaj.views.StartAppInitializationView
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

class PromoFromServer {

    lateinit var coctaiPromoId: IntArray

    @Singleton
    var promoWithImg : List<PromoWithImg>

    @Inject
    lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
        promoWithImg = ArrayList()
    }

    fun donloadStocks(startAppInitializationView: StartAppInitializationView){
        startAppInitializationView.showMessageToast("Загрузка акций")
        repositoryApi.ollPromo(App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, ""),
                object : PromoCallBack{
                    override fun myPromo(promoItem: List<PromoItem>?) {
                        Log.i("Loog", "кол-во акций на сервере - ${promoItem!!.size}")
                        donloadImgForStoks(promoItem,startAppInitializationView)
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

    fun donloadImgForStoks(promoItem: List<PromoItem>, startAppInitializationView: StartAppInitializationView){
        var listPromo : MutableList<PromoWithImg> = arrayListOf()
        var i = 0
        promoItem.forEach {
            repositoryApi.getStaticFromServer("promo", it.id.toString(), object : StaticCallBack{
                override fun myStatic(responseBody: ResponseBody?) {
                    listPromo.add(PromoWithImg(it.id, it.name, it.promoType,it.shortDescription, it.description, responseBody!!.bytes()))

                    promoWithImg = listPromo
                    Log.i("Loog", "кол-во акций c картинками Mutable- ${listPromo.size}")
                    Log.i("Loog", "кол-во акций c картинками - ${promoWithImg.size}")
                    i++
                    downloadImgForStoksComplite(i, promoItem.size,startAppInitializationView)

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

    private fun downloadImgForStoksComplite(count : Int, max: Int, startAppInitializationView: StartAppInitializationView){
        if (count == max){
            Log.i("Loog", "downloadImgForStoksComplite равны ")
            downloadCoctailPromo(startAppInitializationView)
        }

        else
            return
    }

    fun downloadCoctailPromo(startAppInitializationView: StartAppInitializationView){
        repositoryApi.idPromsIntoPromo(App.userWithKeys.publickey, signatur(App.userWithKeys.privatekey, ""),
                object : IntArrayCallBack{
                    override fun myArray(array: IntArray) {
                       coctaiPromoId = array
                    }

                    override fun errorCallBack(error: String) {
                        Log.i("Loog", "Ошибка загрузки промо коктаил  - $error")
                    }
                }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                Log.i("Loog", "Ошибка загрузки промо коктаил t - ${throwable.message}")
            }
        })

        startAppInitializationView.startMainActivity()
    }


}