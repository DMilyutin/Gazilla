package com.gazilla.mihail.gazillaj.providers

import android.util.Log
import com.gazilla.mihail.gazillaj.helps.App
import com.gazilla.mihail.gazillaj.helps.BugReport.BugReport
import com.gazilla.mihail.gazillaj.helps.response.callback.*
import com.gazilla.mihail.gazillaj.helps.signatur
import com.gazilla.mihail.gazillaj.model.repository.RepositoryApi
import com.gazilla.mihail.gazillaj.pojo.Balances
import com.gazilla.mihail.gazillaj.pojo.DragonWheel
import com.gazilla.mihail.gazillaj.pojo.QTY
import com.gazilla.mihail.gazillaj.presenters.CardFragmentPresenter
import javax.inject.Inject

class CardProvider(private val cardFragmentPresenter: CardFragmentPresenter) {

    @Inject lateinit var repositoryApi: RepositoryApi
    private val user = App.userWithKeys

    init {
        App.appComponent.inject(this)
    }

    fun getDescriptionLvl(){
        repositoryApi.levels(user.publickey, signatur(user.privatekey, ""), object :LevelsCallBack{
            override fun levelsFromSerser(levels: Map<Int, Int>?) {
                cardFragmentPresenter.myProgress(levels!!)
            }

            override fun errorCallBack(error: String) {
                cardFragmentPresenter.showErrorMessage(error)
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "CardProvider.getDescriptionLvl.FailCallBack")
                cardFragmentPresenter.showErrorMessage(throwable.message.toString())
            }
        })
    }

    fun mySpins(){
        repositoryApi.mySpins(user.publickey, signatur(user.privatekey, ""), object : QTYCallBack{
            override fun myQTY(qty: QTY?) {
                cardFragmentPresenter.mySpins(qty!!.qty)
            }

            override fun errorCallBack(error: String) {
                cardFragmentPresenter.showErrorMessage(error)
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "CardProvider.mySpins.FailCallBack")
                cardFragmentPresenter.showErrorMessage(throwable.message.toString())
            }
        })
    }

    fun wheeling(){
        repositoryApi.wheeling(user.publickey, signatur(user.privatekey, ""), object : WheelCallBack{
            override fun myWin(wheel: DragonWheel?) {
                cardFragmentPresenter.wheelingResponse(wheel!!)
            }

            override fun errorCallBack(error: String) {
                when {
                    error.contains("7 attempts", false) ->
                        cardFragmentPresenter.showMessageForReserve("К сожалению, на 1 и 2 уровне лояльности можно играть в колесо дракона 7 раз после накопления чека. Для активации колеса посетите нас вновь!")
                    error.contains("no checks in last 2 weeks", false) ->
                        cardFragmentPresenter.showMessageForReserve("К сожалению, на 1 и 2 уровне лояльности можно играть в колесо дракона в течение 2 недель после накопления чека. Для активации колеса посетите нас вновь!")
                    else -> cardFragmentPresenter.showErrorMessage(error)
                }
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "CardProvider.wheeling.FailCallBack")
                cardFragmentPresenter.showErrorMessage(throwable.message.toString())
            }
        })
    }

    fun myBalance(){
        repositoryApi.myBalances(user.publickey, signatur(user.privatekey,""), object : BalanceCallBack{
            override fun myBalance(balances: Balances?) {
                Log.i("Loog", "balance - ${balances!!.score}")
                cardFragmentPresenter.balanceResponse(balances)
            }

            override fun errorCallBack(error: String) {
                cardFragmentPresenter.showErrorMessage(error)
            }
        }, object : FailCallBack{
            override fun throwableCallBack(throwable: Throwable) {
                BugReport().sendBugInfo(throwable.message.toString(), "CardProvider.myBalance.FailCallBack")
                cardFragmentPresenter.showErrorMessage(throwable.message.toString())
            }
        })
    }

}