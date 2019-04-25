package com.gazilla.mihail.gazillaj.model.repository


import android.util.Log
import com.gazilla.mihail.gazillaj.helps.response.callback.*
import com.gazilla.mihail.gazillaj.model.ServerApi
import com.gazilla.mihail.gazillaj.pojo.QTY
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


@Suppress("UNUSED_EXPRESSION")
class RepositoryApi(private val serverApi: ServerApi) {

    fun getActualVersionApp(version: String, successCallBack: SuccessCallBack,
                            failCallBack: FailCallBack){
        Log.i("Loog", "Отправка запроса на версию кода")
        serverApi.getActualVersion(version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful)
                        successCallBack.successResponse(it.body())
                    else
                        successCallBack.errorCallBack(it.errorBody().toString())
                },{
                    failCallBack.throwableCallBack(it)
                })
    }

    /**  */
    fun authorizationCodeForAccount(phone: String, email: String, successCallBack: SuccessCallBack,
                                    failCallBack: FailCallBack){
        serverApi.getCodeForLoging(phone, email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    if (it.isSuccessful)
                        successCallBack.successResponse(it.body())
                    else
                        successCallBack.errorCallBack(it.errorBody().toString())}, failCallBack::throwableCallBack)
    }

    /** отправка защитного кода при восстановлении акк  */
    fun sendCodeForLoging(code: String, authorizationCallBack: AuthorizationCallBack, failCallBack: FailCallBack) {
        serverApi.sendCodeForLoging(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userWithKeysResponse ->
                    if (userWithKeysResponse.isSuccessful) {
                        authorizationCallBack.userWithKeyCallBack(userWithKeysResponse.body()!!)
                    } else
                        authorizationCallBack.errorCallBack(userWithKeysResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** Регистрация новго аккаунта   */

    fun registration(name: String, phone: String, email: String, password: String, referer: String, promo: String, deviceId: String,
                     autorizationCallBack: AuthorizCallB, failCallBack: FailCallBack) {
        serverApi.registration(name, phone, email, password, referer, promo, deviceId)
                .subscribeOn(Schedulers.io())
                .subscribe({ userWithKeysResponse ->
                    if (userWithKeysResponse.isSuccessful){
                        autorizationCallBack.userWithKeyCallBack(userWithKeysResponse.body()!!)
                        Log.i("Loog", "reg - ${userWithKeysResponse.body()!!.id}")
                    }
                    else
                        autorizationCallBack.errorCallBack(userWithKeysResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /*fun registration1(name: String, phone: String, email: String, password: String, referer: String, promo: String, deviceId: String,
                     autorizationCallBack: AuthorizCallB, failCallBack: FailCallBack): Observable<Response<UserWithKeys>>{
        val s = serverApi.registration(name, phone, email, password, referer, promo, deviceId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                return s
    }*/


    /** Запрос на обновление данных User   */
    fun updateUserData(name: String, phone: String, email: String, publicKey: String, signature: String, succsesCallback: SuccessCallBack,
                       failCallBack: FailCallBack) {
        serverApi.updateUserData(email, name, phone, publicKey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        succsesCallback.successResponse(successResponse.body())
                    } else
                        succsesCallback.errorCallBack(successResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Запрос на данные User  */
    fun userData(publicKey: String, signature: String, userCallBack: UserCallBack, failCallBack: FailCallBack) {
        serverApi.getDataUser(publicKey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userResponse ->
                    if (userResponse.isSuccessful) {
                        userCallBack.userCallBack(userResponse.body())
                    } else
                        userCallBack.errorCallBack(userResponse.errorBody()!!.string())

                }, failCallBack::throwableCallBack)
    }

    /** Запрос баланса User  */
    fun myBalances(publickey: String, signature: String, balanceCallBack: BalanceCallBack,
                   failCallBack: FailCallBack) {
        serverApi.getBalances(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ balancesResponse ->
                    if (balancesResponse.isSuccessful)
                        balanceCallBack.myBalance(balancesResponse.body())
                    else {
                        Log.i("Loog", "myBalances err -" + balancesResponse.errorBody()!!.byteStream())
                        balanceCallBack.errorCallBack(balancesResponse.errorBody()!!.string())
                    }
                }, failCallBack::throwableCallBack)


    }

    /** Запрос на уровни лояльности  */
    fun levels(publicKey: String, signature: String, levelsCallBack: LevelsCallBack, failCallBack: FailCallBack) {
        serverApi.getLevels(publicKey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ mapResponse ->
                    if (mapResponse.isSuccessful) {
                        levelsCallBack.levelsFromSerser(mapResponse.body())
                    } else
                        levelsCallBack.errorCallBack(mapResponse.errorBody()!!.string())
                },failCallBack::throwableCallBack)
    }

    /** Запрос меню с сервера  */
    fun ollMenu(publickey: String, signature: String, menuCallBack: MenuCallBack,
                failCallBack: FailCallBack) {
        serverApi.getOllMenu(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    if (listResponse.isSuccessful)
                        menuCallBack.ollMenu(listResponse.body())
                    else
                        menuCallBack.errorCallBack(listResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Добавление товара в избранное  */
    fun addFavoriteItem(publickey: String, id: Int, signature: String, successCallBack: SuccessCallBack,
                        failCallBack: FailCallBack) {
        serverApi.addFavoritItem(publickey, id, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        successCallBack.successResponse(successResponse.body())
                    } else
                        successCallBack.errorCallBack(successResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Удаление товара из избранного  */
    fun delFavoritItem(publickey: String, id: Int, signature: String, successCallBack: SuccessCallBack,
                       failCallBack: FailCallBack) {
        serverApi.deleteFavoritItem(publickey, id, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        successCallBack.successResponse(successResponse.body())
                    } else
                        successCallBack.errorCallBack(successResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** Последняя версия меню  */
    fun lastVersionMenu(publickey: String, signature: String, menuCallBack: LatestVersionCallBack,
                        failCallBack: FailCallBack) {
        serverApi.lastVersionDBMenu(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ latestVersionResponse ->
                    if (latestVersionResponse.isSuccessful)
                        menuCallBack.versionDB(latestVersionResponse.body())
                    else
                        menuCallBack.errorCallBack(latestVersionResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Запрос подарков на сервер  */
    fun giftsOnServer(publickey: String, signature: String, itemCallBack: MenuItemCallBack,
                      failCallBack: FailCallBack) {
        serverApi.getGifts(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    if (listResponse.isSuccessful)
                        itemCallBack.menuItem(listResponse.body())
                    else
                        itemCallBack.errorCallBack(listResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Последня версия таблицы подарков на сервере  */
    fun lastVersionMenuItem(publickey: String, signature: String, latestVersionCallBack: LatestVersionCallBack,
                            failCallBack: FailCallBack) {
        serverApi.lastVersionDBGifts(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ latestVersionResponse ->
                    if (latestVersionResponse.isSuccessful)
                        latestVersionCallBack.versionDB(latestVersionResponse.body())
                    else
                        latestVersionCallBack.errorCallBack(latestVersionResponse.errorBody().toString())
                }, failCallBack::throwableCallBack)

    }

    /** Запрос акций и новостей с сервера  */
    fun ollPromo(publickey: String, signature: String, promoCallBack: PromoCallBack,
                 failCallBack: FailCallBack) {

        serverApi.getOllPromo(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    if (listResponse.isSuccessful())
                        promoCallBack.myPromo(listResponse.body())
                    else
                        promoCallBack.errorCallBack(listResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Запрос последней версии акций  */
    /*fun lastVersionPromo(publickey: String, signature: String, promoCallBack: LVersionDBPromoCallBack,
                         failCallBack: com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack) {
        serverApi.lastVersionDBPromo(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ latestVersionResponse ->
                    if (latestVersionResponse.isSuccessful())
                        promoCallBack.versionDBPromo(latestVersionResponse.body())
                    else
                        promoCallBack.showError(latestVersionResponse.code())
                }, Consumer<Throwable> { failCallBack.setError(it) })

    }*/

    /** Загрузка статик файлов с сервера (картинок)  */
    fun getStaticFromServer(folder: String, id: String, staticCallBack: StaticCallBack,
                            failCallBack: FailCallBack) {
        serverApi.getStaticFromServer(folder, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ objectResponse ->
                    if (objectResponse.isSuccessful)
                        staticCallBack.myStatic(objectResponse.body())
                    else
                        staticCallBack.errorCallBack(objectResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)

    }

    /** Запрос кол-ва возможных вращений колеса драконов  */
    fun mySpins(publickey: String, signature: String, qtyCallBack: QTYCallBack,
                failCallBack: FailCallBack) {
        serverApi.mySpins(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ qtyResponse ->
                    if (qtyResponse.isSuccessful)
                        qtyCallBack.myQTY(qtyResponse.body())
                    else
                        qtyCallBack.myQTY(QTY(0))
                }, failCallBack::throwableCallBack)

    }

    /** запрос на прокрутку колеса  */
    fun wheeling(publickey: String, signature: String, wheelCallBack: WheelCallBack,
                 failCallBack: FailCallBack) {
        serverApi.wheeling(publickey, signature)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ dragonWheelResponse ->
                    if (dragonWheelResponse.isSuccessful) {
                        wheelCallBack.myWin(dragonWheelResponse.body())
                    } else
                        wheelCallBack.errorCallBack(dragonWheelResponse.errorBody()!!.string())

                }, failCallBack::throwableCallBack)
    }

    /** Запрос резерва   */
    fun reserving(qty: Int, hours: Int, date: String,
                  phone: String, name: String, comment: String, preorder: Boolean?, publickey: String,
                  signatur: String, successCallBack: SuccessCallBack, failCallBack: FailCallBack) {
        serverApi.reserving(comment, date, hours, name, phone, preorder, publickey, qty,
                signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        successCallBack.successResponse(successResponse.body())
                        Log.i("Loog", "success mes " + successResponse.code())
                    } else {
                        Log.i("Loog", "success mes " + successResponse.code())
                        Log.i("Loog", "error reserving repository API-" + successResponse.errorBody()!!.byteStream())
                        successCallBack.errorCallBack(successResponse.errorBody()!!.string())
                    }
                }, failCallBack::throwableCallBack)

    }

    /** Запрос на мои резервы */
    fun myReserveOnServer(publickey: String, signatur: String, reservesCallBack: ReservesCallBack, failCallBack: FailCallBack){
        serverApi.myReserves(publickey,signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({if (it.isSuccessful)
                    reservesCallBack.myReserves(it.body())
                    else
                    reservesCallBack.errorCallBack(it.errorBody().toString())
                }, {
                    failCallBack.throwableCallBack(it)
                })
    }
    /** Запрос на удаление моего резерва */
    fun dellMyReserve(publickey: String, id: Int, signatur: String, successCallBack: SuccessCallBack, failCallBack: FailCallBack){
        serverApi.dellMyReserve(publickey, signatur, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful)
                        successCallBack.successResponse(it.body())
                    else
                        successCallBack.errorCallBack(it.errorBody().toString())
                },{
                    failCallBack.throwableCallBack(it)
                })
    }

    /** хз че за хрень  */
    fun dragonwing(publickey: String, signatur: String, dragonWeyCallBack: DragonWeyCallBack,
                   failCallBack: FailCallBack) {
        serverApi.getLvlDragonway(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ levelsResponse ->
                    if (levelsResponse.isSuccessful) {
                        dragonWeyCallBack.myLvl(levelsResponse.body())
                    } else
                        dragonWeyCallBack.errorCallBack(levelsResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** запрос на акцию дымный абонимент  */
    fun smokerpassing(publickey: String, signatur: String, smokerpassCallBack: SmokerpassCallBack,
                      failCallBack: FailCallBack) {
        serverApi.getPromoSmokerpass(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ promoSmokerpassResponse ->
                    if (promoSmokerpassResponse.isSuccessful) {
                        smokerpassCallBack.mySmokerpass(promoSmokerpassResponse.body())
                    } else
                        smokerpassCallBack.errorCallBack(promoSmokerpassResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** оттправка отчета об шибках  */
    fun sendBugReport(message: String, publickey: String, signatur: String, successCallBack: SuccessCallBack,
                      failCallBack: FailCallBack) {

        serverApi.sendBugReport(message, publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        successCallBack.successResponse(successResponse.body())
                    }else
                        successCallBack.errorCallBack(successResponse.errorBody().toString())
                }, failCallBack::throwableCallBack)

    }

    /** получение плейлиста для гуслей  */
    /*fun playListFromServer(publickey: String, signatur: String, playlistSongCallBack: PlaylistSongCallBack,
                           failCallBack: com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack) {
        serverApi.getPlaylist(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful()) {
                        playlistSongCallBack.playlist(response.body())
                    } else
                        playlistSongCallBack.errorPlaylist("Ошибка загрузки плейлиста")
                }, Consumer<Throwable> { failCallBack.setError(it) })
    }*/

    /** загрузка возможных песен для гуслей  */
    /*fun songFromServer(publickey: String, signatur: String, songCallBack: SongCallBack,
                       failCallBack: com.gazilla.mihail.gazillaj.utils.callBacks.FailCallBack) {
        serverApi.getOllSongs(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful()) {
                        songCallBack.songFromServer(response.body())
                    } else
                        songCallBack.errorSongFromServer("Ошибка загрузки доступных песен")
                }, Consumer<Throwable> { failCallBack.setError(it) })

    }*/

    /** Добавить песню в очередь  */
    fun sendNextSong(id: Int, publickey: String, signatur: String, successCallBack: SuccessCallBack,
                     failCallBack: FailCallBack) {
        serverApi.sendMyNextSond(publickey, id, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        successCallBack.successResponse(successResponse.body())
                    } else
                        successCallBack.errorCallBack(successResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** Проверка последней версии уведомлений  */
    fun getLastVersionNotification(publickey: String, signatur: String, versionCallBack: LatestVersionCallBack,
                                   failCallBack: FailCallBack) {
        serverApi.getLastVersionNotification(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ latestVersionResponse ->
                    if (latestVersionResponse.isSuccessful) {
                        versionCallBack.versionDB(latestVersionResponse.body())
                    } else
                        versionCallBack.errorCallBack(latestVersionResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** Подгрузка последних уведомлений  */
    fun getOllNotification(publickey: String, signatur: String, notificationCallBack: NotificationCallBack,
                           failCallBack: FailCallBack) {
        serverApi.getOllNotification(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ listResponse ->
                    if (listResponse.isSuccessful) {
                        notificationCallBack.ollNotification(listResponse.body())
                    } else
                        notificationCallBack.errorCallBack(listResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    /** Отправка отчета о нажатой кнопки уведомлений */
    fun sendAnswerUserAboutNotification(publickey: String, alertId: Int, commandId: Int, signatur: String,
                                        successCallBack: SuccessCallBack, failCallBack: FailCallBack) {
        serverApi.answerUserAboutNotification(publickey, alertId, commandId, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ successResponse ->
                    if (successResponse.isSuccessful) {
                        successCallBack.successResponse(successResponse.body())
                    } else
                        successCallBack.errorCallBack(successResponse.errorBody()!!.string())
                }, failCallBack::throwableCallBack)
    }

    fun sendPromoCodeCoctail(code: String, force: Boolean, publickey: String, signatur: String,
                             successCallBack: SuccessCallBack, failCallBack: FailCallBack){
        serverApi.sendCoctailPromoCode(code, force, publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful)
                        successCallBack.successResponse(it.body())
                    else
                        successCallBack.errorCallBack(it.errorBody()!!.toString())
                }, failCallBack::throwableCallBack)
    }

    fun myStarsPromoCoctail(publickey: String, signatur: String, qtyCallBack: QTYCallBack, failCallBack: FailCallBack){
        serverApi.getPromoCoctailMyStars(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful)
                        qtyCallBack.myQTY(it.body())
                    else
                        qtyCallBack.errorCallBack(it.errorBody().toString())
                }, failCallBack::throwableCallBack)
    }

    fun idPromsIntoPromo(publickey: String, signatur: String, intArrayCallBack: IntArrayCallBack, failCallBack: FailCallBack){
        serverApi.getPromoCoctailIdIntoProms(publickey, signatur)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    if (it.isSuccessful)
                        intArrayCallBack.myArray(it.body()!!)
                    else
                        intArrayCallBack.errorCallBack(it.errorBody().toString())
                }, failCallBack::throwableCallBack)
    }
}