package com.gazilla.mihail.gazillaj.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.pojo.QTY
import com.gazilla.mihail.gazillaj.pojo.Success
import com.gazilla.mihail.gazillaj.providers.PromoCocktailProvider
import com.gazilla.mihail.gazillaj.views.PromoCocktailView

@InjectViewState
class PromoCocktailPresenter: MvpPresenter<PromoCocktailView>() {

    private val promoCocktailProvider = PromoCocktailProvider(this)

    fun myStars(){
        promoCocktailProvider.myStars()
    }

    fun starsFromServer(qty: QTY){
       viewState.setMyStars(qty.qty)
    }

    fun sendCodeCoctailPromo(code: String, force: Boolean){
        val promocode = if (code.isEmpty())
            "empty"
        else
            code

        promoCocktailProvider.sendPromoCode(promocode, force)
    }

    fun responseSendPromoCode(success: Success){
        if (!success.success){
            viewState.showDialogAccessDeleteOldCoctail("У вас уже есть неиспользованный коктейль, если Вы продолжите, старый коктейль пропадет")
        }
        else{
            viewState.showMessage("Коктель скоро будет добавлен в Подарки")
            viewState.accessSendCode()
        }

    }

    fun showMessage(message: String){
        viewState.showMessage(message)
    }

}