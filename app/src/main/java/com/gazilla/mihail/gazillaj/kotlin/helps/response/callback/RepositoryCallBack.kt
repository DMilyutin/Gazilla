package com.gazilla.mihail.gazillaj.kotlin.helps.response.callback

import com.gazilla.mihail.gazillaj.kotlin.pojo.*
import okhttp3.ResponseBody

    interface AuthorizationCallBack{
        fun userWithKeyCallBack(userWithKeys: UserWithKeys)
        fun errorCallBack(error: String)
    }

    interface BalanceCallBack {
        fun myBalance(balances: Balances?)
        fun errorCallBack(error: String)
    }

    interface DragonWeyCallBack {
        fun myLvl(levels: Levels?)
        fun errorCallBack(error: String)
    }

    interface FailCallBack {
        fun throwableCallBack(throwable: Throwable)
    }

    interface ReservesCallBack{
        fun myReserves(reserves: List<Reserve>?)
        fun errorCallBack(error: String)
    }


    interface LevelsCallBack {
        fun levelsFromSerser(levels: Map<Int, Int>?)
        fun errorCallBack(error: String)
    }

     interface LatestVersionCallBack {
        fun versionDB(latestVersion: LatestVersion?)
        fun errorCallBack(error: String)
    }

     interface MenuCallBack {
        fun ollMenu(menuCategoryList: List<MenuCategory>?)
        fun errorCallBack(error: String)
    }

     interface MenuItemCallBack {
        fun menuItem(menuItemList: List<MenuItem>?)
        fun errorCallBack(error: String)
    }

     interface NotificationCallBack {
        fun ollNotification(notificationList: List<Notification>?)
        fun errorCallBack(error: String)
    }

     interface QTYCallBack {
        fun myQTY(qty: QTY?)
        fun errorCallBack(error: String)
    }

     interface SmokerpassCallBack {
        fun mySmokerpass(promoSmokerpass: PromoSmokerpass?)
        fun errorCallBack(error: String)
    }

     interface StaticCallBack {
        fun myStatic(responseBody: ResponseBody?)
        fun errorCallBack(error: String)
    }

    interface SuccessCallBack {
        fun successResponse(success: Success?)
        fun errorCallBack(error: String)
    }

     interface UserCallBack {
        fun userCallBack(user: User?)
        fun errorCallBack(error: String)
    }

     interface WheelCallBack {
        fun myWin(wheel: DragonWheel?)
        fun errorCallBack(error: String)
    }

    interface PromoCallBack{
        fun myPromo(promoItem: List<PromoItem>?)
        fun errorCallBack(error: String)
    }