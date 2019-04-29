package com.gazilla.mihail.gazillaj.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.helps.response.callback.MenuItemCallBack
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.MenuItem
import com.gazilla.mihail.gazillaj.pojo.SmartMenuItem
import com.gazilla.mihail.gazillaj.views.ProductsFreeFragmentView
import javax.inject.Inject
import kotlin.collections.ArrayList

@InjectViewState
class ProductsFreeFragmentPresenter : MvpPresenter<ProductsFreeFragmentView>() {

    @Inject lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun myGifts(){
        val userWithKeys = App.userWithKeys

        val signature = signatur(userWithKeys.privatekey, "")


        repositoryApi.giftsOnServer(userWithKeys.publickey, signature,
                object : MenuItemCallBack{
                    override fun menuItem(menuItemList: List<MenuItem>?) {
                        smartMenuItemList(menuItemList!!)
                    }

                    override fun errorCallBack(error: String) {
                        viewState.showMessageDialog(error)
                    }
                }, object : FailCallBack{
                    override fun throwableCallBack(throwable: Throwable) {
                        viewState.showMessageDialog(throwable.message.toString())
                        BugReport().sendBugInfo(throwable.message.toString(), "ProductsFreeFragmentPresenter.myGifts.FailCallBack")

            }
        })
    }

    fun smartMenuItemList(menuItemListt: List<MenuItem>){
        var menuItemList: MutableList<MenuItem> = menuItemListt as MutableList<MenuItem>
        var smartList : List<SmartMenuItem> = ArrayList()
        var max  = 0

        menuItemList.forEach {
            if (it.id>max) max = it.id
        }

        var countArr = IntArray((max+1)) {0}

        menuItemList.forEach{
            countArr[it.id] += 1
        }


        var count = 0
        var count2 : Int

        for(it in countArr) {
            Log.i("Loog", "count in arr - $count")
            if (it>0) {
                count2 = 0
                for (it1 in menuItemList){
                    if (it1.id==count&&count2==0){
                        val smartMenuItem = SmartMenuItem(it1.id, it1.name, it1.price, it1.weight, it1.description, it)
                        Log.i("Loog", "smartMenuItem -  ${it1.id}, ${it1.name}, ${it1.price}, ${it1.weight}, ${it1.description}, $it")
                        smartList = smartList + smartMenuItem
                        count2++
                    }
                }
            }
            count += 1
        }
        viewState.setGiftsAdapter(smartList)
    }
}