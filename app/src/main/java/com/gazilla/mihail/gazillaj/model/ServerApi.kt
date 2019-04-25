package com.gazilla.mihail.gazillaj.model


import com.gazilla.mihail.gazillaj.pojo.*

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ServerApi {


    // получение актуальной версии
    @GET("api/version")
    fun getActualVersion(@Query("version") version: String): Observable<Response<Success>>

    // Авторизация
    @FormUrlEncoded
    @POST("api/recover/request")
    fun getCodeForLoging(@Field("phone") phone: String, @Field("email") email: String): Observable<Response<Success>>

    @FormUrlEncoded
    @POST("api/recover/approve")
    fun sendCodeForLoging(@Field("code") phone: String): Observable<Response<UserWithKeys>>

    @FormUrlEncoded
    @POST("api/reg")
    fun registration(@Field("name") name: String,
                              @Field("phone") phone: String,
                              @Field("email") email: String,
                              @Field("password") password: String,
                              @Field("referer") referer: String,
                              @Field("promo") promo: String,
                              @Field("code") deviceId: String): Observable<Response<UserWithKeys>>


    // Изменение данных
    @FormUrlEncoded
    @POST("api/client/update")
    fun updateUserData(@Field("email") email: String,
                                @Field("name") name: String,
                                @Field("phone") phone: String,
                                @Header("publickey") publickey: String,
                                @Field("signature") signature: String): Observable<Response<Success>>

    @GET("api/client/userinfo")
    fun getDataUser(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<User>>

    // Баланс
    @GET("api/client/balances")
    fun getBalances(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<Balances>>

    // Уровни
    @GET("api/client/levels")
    fun getLevels(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<Map<Int, Int>>>

    // Меню
    @GET("api/client/menu")
    fun getOllMenu(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<List<MenuCategory>>>

    @GET("api/client/menu/latest")
    fun lastVersionDBMenu(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<LatestVersion>>

    @GET("api/client/gifts")
    fun getGifts(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<List<MenuItem>>>

    @GET("api/client/gifts/latest")
    fun lastVersionDBGifts(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<LatestVersion>>

    @FormUrlEncoded
    @POST("api/client/favorites")
    fun addFavoritItem(@Header("publickey") publickey: String, @Field("id") id: Int, @Field("signature") signature: String): Observable<Response<Success>>


    @DELETE("api/client/favorites")
    fun deleteFavoritItem(@Header("publickey") publickey: String, @Query("id") id: Int, @Query("signature") signature: String): Observable<Response<Success>>

    //Новости и акции
    @GET("api/client/promo")
    fun getOllPromo(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<List<PromoItem>>>

   // @GET("api/client/promo/latest")
   // abstract fun lastVersionDBPromo(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<LatestVersion>>

    // Документы
    @GET("static/{folder}/{id}") // menu или promo
    fun getStaticFromServer(@Path("folder") folder: String, @Path("id") id: String): Observable<Response<ResponseBody>>

    //Колесо дракона
    @GET("api/client/spins")
    fun mySpins(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<QTY>>

    @FormUrlEncoded
    @POST("api/client/wheel")
    fun wheeling(@Header("publickey") publickey: String, @Field("signature") signature: String): Observable<Response<DragonWheel>>

    //Резерв

    @FormUrlEncoded
    @POST("api/client/reserves")
    fun reserving(
            @Field("comment") comment: String,
            @Field("date") date: String,
            @Field("hours") hours: Int,
            @Field("name") name: String,
            @Field("phone") phone: String,
            @Field("preorder") preorder: Boolean?,
            @Header("publickey") publickey: String,
            @Field("qty") qty: Int,
            @Field("signature") signature: String): Observable<Response<Success>>


    // Мои резервы
    @GET("api/client/reserves")
    fun myReserves(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<List<Reserve>>>

    //Удалить резерв
    @DELETE("api/client/reserves")
    fun dellMyReserve(@Header("publickey") publickey: String, @Query("signature")signature: String, @Query("id")id: Int): Observable<Response<Success>>

    // Информация по акциям
    @GET("api/client/promos/dragonway")
    fun getLvlDragonway(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<Levels>>

    @GET("api/client/promos/smokerpass")
    fun getPromoSmokerpass(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<PromoSmokerpass>>

    // акция коктейли
    @FormUrlEncoded
    @POST("api/client/cocktail_promo/code")
    fun sendCoctailPromoCode(@Field("code") comment: String,
                             @Field("force") date: Boolean,
                             @Header("publickey") publickey: String,
                             @Field("signature") signature: String): Observable<Response<Success>>

    // получить кол-во звезд для акции с коктейлями
    @GET("api/client/cocktail_promo/stars")
    fun getPromoCoctailMyStars(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<QTY>>


    // получить кол-во звезд для акции с коктейлями
    @GET("api/client/cocktail_promo/sub_promos")
    fun getPromoCoctailIdIntoProms(@Header("publickey") publickey: String, @Query("signature") signature: String): Observable<Response<IntArray>>

    // отправка отчета об ошибке
    @FormUrlEncoded
    @POST("api/client/bug")
    fun sendBugReport(@Field("message") message: String,
                               @Header("publickey") publickey: String,
                               @Field("signature") signature: String): Observable<Response<Success>>

    // гусли
   // @GET("api/client/songs")
   // abstract fun getOllSongs(@Header("publickey") publickey: String,
                           //  @Query("signature") signature: String): Observable<Response<List<Song>>>

    /*@GET("api/client/playlist")
    abstract fun getPlaylist(@Header("publickey") publickey: String,
                             @Query("signature") signature: String): Observable<Response<PlaylistSongs>>*/

    @FormUrlEncoded
    @POST("api/client/playlist")
    fun sendMyNextSond(@Header("publickey") publickey: String,
                                @Field("next") next: Int,
                                @Field("signature") signature: String): Observable<Response<Success>>

    // Уведомления
    @GET("api/client/alerts/latest")
    fun getLastVersionNotification(@Header("publickey") publickey: String,
                                            @Query("signature") signature: String): Observable<Response<LatestVersion>>

    @GET("api/client/alerts")
    fun getOllNotification(@Header("publickey") publickey: String,
                                    @Query("signature") signature: String): Observable<Response<List<Notification>>>

    // ответ на уведомления
    @FormUrlEncoded
    @POST("api/client/alerts/report")
    fun answerUserAboutNotification(@Header("publickey") publickey: String,
                                             @Field("alertId") alertId: Int,
                                             @Field("commandId") commandId: Int,
                                             @Field("signature") signature: String): Observable<Response<Success>>

}