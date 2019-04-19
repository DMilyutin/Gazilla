package com.gazilla.mihail.gazillaj.kotlin.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.kotlin.activites.CardFragment
import com.gazilla.mihail.gazillaj.kotlin.activites.ContactsFragment
import com.gazilla.mihail.gazillaj.kotlin.activites.ProductsMenuFragment
import com.gazilla.mihail.gazillaj.kotlin.activites.StocksFragment
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.views.MainView

@InjectViewState
class MainPresenter: MvpPresenter<MainView>() {

    private val cardFragment = CardFragment()
    private val contactsFragment = ContactsFragment()
    private val productFragment= ProductsMenuFragment()
    private val stocksFragment = StocksFragment()

    init {
        viewState.addFirstFragment(cardFragment)
        viewState.showImgOpenAccount(true)
        updateScore()
    }

    fun updateScore(){
        viewState.setScoreInfo(App.userWithKeys.score.toString())
    }


    fun openCardFragment(){
        viewState.replaceFragment(cardFragment, "")
        viewState.showImgOpenAccount(true)
    }

    fun openProductFragment(){
        viewState.replaceFragment(productFragment, "Подарки")
        viewState.showImgOpenAccount(false)
    }

    fun openStockFragment(){
        viewState.replaceFragment(stocksFragment, "Акции и новости")
        viewState.showImgOpenAccount(false)
    }

    fun openContactsFragment(){
        viewState.replaceFragment(contactsFragment, "О нас")
        viewState.showImgOpenAccount(false)
    }

    fun openFragmentBeforNotificatoin(nameFragment: String){
        if (nameFragment == "ProductsMenuFragment")
            openProductFragment()
        if (nameFragment == "StocksFragment")
            openStockFragment()
    }

}