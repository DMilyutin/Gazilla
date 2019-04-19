package com.gazilla.mihail.gazillaj.kotlin.helps

import com.gazilla.mihail.gazillaj.kotlin.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.FailCallBack
import com.gazilla.mihail.gazillaj.kotlin.helps.response.callback.MenuCallBack
import com.gazilla.mihail.gazillaj.kotlin.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuCategory
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class MenuFromServer {

    @Singleton
    lateinit var appMenuCategory: List<MenuCategory>

    @Singleton
    var favoriteMenu: IntArray = IntArray(0)

    @Inject
    lateinit var repositoryApi: RepositoryApi

    init {
        App.appComponent.inject(this)
    }

    fun updateMenu(){
        val user = App.userWithKeys
        repositoryApi.ollMenu(user.publickey, signatur(user.privatekey, ""), object : MenuCallBack {
            override fun ollMenu(menuCategoryList: List<MenuCategory>?) {
                appMenuCategory = menuCategoryList!!
            }

            override fun errorCallBack(error: String) {
                BugReport().sendBugInfo(error, "MenuFromServer.updateMenu.errorCallBack")
            }

        }, object : FailCallBack {
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "MenuFromServer.updateMenu.FailCallBack")
            }
        })
    }
}