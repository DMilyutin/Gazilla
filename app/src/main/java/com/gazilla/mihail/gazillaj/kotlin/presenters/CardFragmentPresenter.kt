package com.gazilla.mihail.gazillaj.kotlin.presenters

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.gazilla.mihail.gazillaj.R
import com.gazilla.mihail.gazillaj.kotlin.helps.App
import com.gazilla.mihail.gazillaj.kotlin.helps.MenuImg
import com.gazilla.mihail.gazillaj.kotlin.helps.QRCode.QRCode
import com.gazilla.mihail.gazillaj.kotlin.pojo.Balances
import com.gazilla.mihail.gazillaj.kotlin.pojo.DragonWheel
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuCategory
import com.gazilla.mihail.gazillaj.kotlin.pojo.MenuItem
import com.gazilla.mihail.gazillaj.kotlin.providers.CardProvider
import com.gazilla.mihail.gazillaj.kotlin.views.CardView
import com.google.zxing.BarcodeFormat

@InjectViewState
class CardFragmentPresenter : MvpPresenter<CardView>() {

    private val cardProvider= CardProvider(this)
    private var imgResWin : Int = 0
    private lateinit var descriptionResWin : String
    private var mySpins: Int = 0
    private var wheelingSuccess = false

    init {
        myIdForQRCode()
        update()
    }

    fun update(){
        Log.i("Loog", "update card presenter ")
        initWheel()
        cardProvider.myBalance()
        cardProvider.mySpins()
        cardProvider.getDescriptionLvl()
    }

    private fun myIdForQRCode(){
        val myId = App.userWithKeys.id
        val bitmap = QRCode().encodeAsBitmap(myId.toString(), BarcodeFormat.QR_CODE, 250, 250)
        viewState.setQrCode(bitmap!!)
    }

    private fun initWheel(){
        val myLvl = App.userWithKeys.level
        val res = when (myLvl){
            5 -> R.drawable.koleso5
            4 -> R.drawable.koleso4
            3 -> R.drawable.koleso3
            2 -> R.drawable.koleso2
            else ->{
                R.drawable.koleso1
            }
        }
        viewState.initWheelLvl(res)
    }

    fun myProgress(levels: Map<Int, Int>){
        Log.i("Loog", "App.userWithKeys.sum - ${App.userWithKeys.sum}")
        val maxSum : Int = if (App.userWithKeys.level == 5)
            levels.getValue(5)
        else
            levels.getValue(App.userWithKeys.level+1)
        viewState.setValueProgressBar(maxSum, App.userWithKeys.sum)
        setLvlDraconAdapter(levels)
    }

    private fun setLvlDraconAdapter(levels: Map<Int, Int>) {
        viewState.setLvlUserDraconAdapter(levels)
    }

    fun mySpins(qty: Int){
        mySpins = qty
        viewState.setSpins(qty = qty)
        if (qty>0)
            viewState.showWhiteRoundDrakonTip(true)
        else
            viewState.showWhiteRoundDrakonTip(false)
    }

    fun startWheeling(){
        cardProvider.wheeling()
        viewState.showWhiteRoundDrakonTip(false)
        viewState.startWheeling()
    }

    fun balanceResponse(balances: Balances){
        Log.i("Loog", "update balanceResponse - ${balances.score} ")
        App.userWithKeys.score = balances.score
        App.userWithKeys.sum = balances.sum
        viewState.updateBalance()
    }

    fun wheelingResponse(wheel: DragonWheel){
        wheelingSuccess = true
        val menuImg = MenuImg()
        if (wheel.winType=="point"){
            descriptionResWin = "${wheel.id} баллов"
            imgResWin = menuImg.getImg(0)
        }
        else{
            findItemById(wheel.id, menuImg)
        }
    }

    private fun findItemById(itemId: Int, menuImg: MenuImg){
        val menuCategory = App.menuFromServer.appMenuCategory
        menuCategory.forEach { t: MenuCategory? ->
            t!!.items.forEach { t2: MenuItem? ->
                if (t2!!.id==itemId){
                    descriptionResWin = t2.name
                    imgResWin = menuImg.getImg(itemId)
                }
            }
        }
    }

    fun showWin(){
        if (wheelingSuccess){
            if(wheelingSuccess&&descriptionResWin!=""){
                viewState.showMyWin(imgResWin, descriptionResWin)
                descriptionResWin = ""
                wheelingSuccess= false
            }
            else
                showErrorMessage("Медленное интернет соединение")
            update()
        }
    }

    fun showWinTest(){
        val menuImg = MenuImg()
        findItemById(49, menuImg)
    }

    fun showErrorMessage(error: String){
        viewState.showMessageDialog(error)
    }

    fun showMessageForReserve(message: String){
        viewState.openMessageWithReserve(message)
    }


    fun getDecriptionLvlDracon(key : Int){
         var lvlS: String
         var one: String
         var two: String
         var fri: String
         var fol: String
         var fif: String

        when(key){
            1 ->{
                lvlS = "1-й дракон"
                one = "- Начисление 8% от суммы чека бонусными баллами"
                two = "- Возможность играть в колесо дракона 1 раз в день и за каждые 750 рублей в чеке"
                fri = "- Приветственный подарок"
                fol = "- Возможность принимать участие в оффлайн розыгрышах от заведения"
                fif = ""
            }
            2 ->{
                lvlS = "2-й дракон"
                one = "- Начисление 11% от суммы чека бонусными баллами"
                two = "- Увеличенные призы в колесе дракона, повышенная вероятность выиграть подарок"
                fri = "- Бесплатный кальян в подарок при переходе на этот уровень"
                fol = "- Возможность покупать за баллы и получать бесплатные подарки по пятницам и субботам"
                fif = ""
            }
            3->{
                lvlS = "3-й дракон"
                one = "- Начисление 14% от суммы чека бонусными баллами"
                two = "- Дополнительная попытка в колесе дракона начисляется за каждые 500 рублей в чеке"
                fri = "- Начисляется 1500 бонусных баллов при переходе на этот уровень"
                fol = "- Специальные акции для обладателей этого статуса"
                fif = "- Возможность крутить колесо дракона без ограничений"
            }
            4->{
                lvlS = "4-й дракон"
                one = "- Начисление 17% от суммы чека бонусными баллами"
                two = "- Большие призы в колесе дракона, возможность поучаствовать в розыгрыше специального приза"
                fri = "- 4000 бонусных баллов при переходе на этот уровень"
                fol = "- Возможность бронировать столы без депозитов на ночные мероприятия"
                fif = ""
            }
            else ->{
                lvlS = "5-й дракон"
                one = "- Начисление 20% от суммы чека бонусными баллами"
                two = "- В колесо дракона можно играть дважды в день"
                fri = "- 10000 бонусных баллов при переходе на этот уровень и специальный подарок"
                fol = ""
                fif = ""
            }
        }

        viewState.showDetailLvlDracon(lvlS, one, two, fri, fol, fif)

    }
}