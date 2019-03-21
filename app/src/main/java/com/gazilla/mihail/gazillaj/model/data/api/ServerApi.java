package com.gazilla.mihail.gazillaj.model.data.api;

import com.gazilla.mihail.gazillaj.utils.POJO.Balances;
import com.gazilla.mihail.gazillaj.utils.POJO.DragonWheel;
import com.gazilla.mihail.gazillaj.utils.POJO.LatestVersion;
import com.gazilla.mihail.gazillaj.utils.POJO.Levels;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuCategory;
import com.gazilla.mihail.gazillaj.utils.POJO.MenuItem;
import com.gazilla.mihail.gazillaj.utils.POJO.Notificaton;
import com.gazilla.mihail.gazillaj.utils.POJO.PlaylistSongs;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoItem;
import com.gazilla.mihail.gazillaj.utils.POJO.PromoSmokerpass;
import com.gazilla.mihail.gazillaj.utils.POJO.QTY;
import com.gazilla.mihail.gazillaj.utils.POJO.Song;
import com.gazilla.mihail.gazillaj.utils.POJO.Success;
import com.gazilla.mihail.gazillaj.utils.POJO.User;
import com.gazilla.mihail.gazillaj.utils.POJO.UserWithKeys;

import java.util.List;
import java.util.Map;


import io.reactivex.Observable;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/** интерфейс запросов к серверу с использованием Retrofit2 */
public interface ServerApi {

    // Авторизация
    @FormUrlEncoded
    @POST("api/recover/request")
    Observable<Response<Success>> getCodeForLoging(@Field("phone")String phone, @Field("email")String email);

    @FormUrlEncoded
    @POST("api/recover/approve")
    Observable<Response<UserWithKeys>> sendCodeForLoging(@Field("code")String phone);

    @FormUrlEncoded
    @POST("api/reg")
    Observable<Response<UserWithKeys>> registration(@Field("name")String name,
                                                    @Field("phone")String phone,
                                                    @Field("email")String email,
                                                    @Field("password")String password,
                                                    @Field("referer")String referer,
                                                    @Field("promo")String promo,
                                                    @Field("code")String deviceId
    );


    // Изменение данных
    @FormUrlEncoded
    @POST("api/client/update")
    Observable<Response<Success>> updateUserData(@Field("email")String email,
                                                 @Field("name")String name,
                                                 @Field("phone")String phone,
                                                 @Header("publickey")String publickey,
                                                 @Field("signature") String signature);

    @GET("api/client/userinfo")
    Observable<Response<User>> getDataUser(@Header("publickey")String publickey, @Query("signature") String signature);

    // Баланс
    @GET("api/client/balances")
    Observable<Response<Balances>> getBalances(@Header("publickey")String publickey, @Query("signature") String signature);

    // Уровни
    @GET("api/client/levels")
    Observable<Response<Map<Integer, Integer>>> getLevels(@Header("publickey")String publickey, @Query("signature") String signature);

    // Меню
    @GET("api/client/menu")
    Observable<Response<List<MenuCategory>>> getOllMenu(@Header("publickey")String publickey, @Query("signature") String signature);

    @GET("api/client/menu/latest")
    Observable<Response<LatestVersion>> lastVersionDBMenu(@Header("publickey")String publickey, @Query("signature") String signature);

    @GET("api/client/gifts")
    Observable<Response<List<MenuItem>>> getGifts(@Header("publickey")String publickey, @Query("signature") String signature);

    @GET("api/client/gifts/latest")
    Observable<Response<LatestVersion>> lastVersionDBGifts(@Header("publickey")String publickey, @Query("signature") String signature);

    @FormUrlEncoded
    @POST("api/client/favorites")
    Observable<Response<Success>> addFavoritItem(@Header("publickey")String publickey, @Field("id")int id, @Field("signature") String signature);


    @DELETE("api/client/favorites")
    Observable<Response<Success>> deleteFavoritItem(@Header("publickey")String publickey, @Query("id")int id, @Query("signature") String signature);

    //Новости и акции
    @GET("api/client/promo")
    Observable<Response<List<PromoItem>>> getOllPromo(@Header("publickey")String publickey, @Query("signature") String signature);

    @GET("api/client/promo/latest")
    Observable<Response<LatestVersion>> lastVersionDBPromo(@Header("publickey")String publickey, @Query("signature") String signature);

    // Документы
    @GET("static/{folder}/{id}") // menu или promo
    Observable<Response<ResponseBody>> getStaticFromServer(@Path("folder") String folder, @Path("id") String id);

    //Колесо дракона
    @GET("api/client/spins")
    Observable<Response<QTY>> mySpins(@Header("publickey")String publickey, @Query("signature") String signature);

    @FormUrlEncoded
    @POST("api/client/wheel")
    Observable<Response<DragonWheel>> wheeling(@Header("publickey")String publickey, @Field("signature") String signature);

    //Резерв

    @FormUrlEncoded
    @POST("api/client/reserves")
    Observable<Response<Success>> reserving(
                                            @Field("comment")String comment,
                                            @Field("date")String date,
                                            @Field("hours")int hours,
                                            @Field("name")String name,
                                            @Field("phone")String phone,
                                            @Field("preorder")Boolean preorder,
                                            @Header("publickey")String publickey,
                                            @Field("qty")int qty,
                                            @Field("signature") String signature);



    @GET("api/client/promos/dragonway")
    Observable<Response<Levels>> getLvlDragonway(@Header("publickey")String publickey, @Query("signature") String signature);

    @GET("api/client/promos/smokerpass")
    Observable<Response<PromoSmokerpass>> getPromoSmokerpass(@Header("publickey")String publickey, @Query("signature") String signature);

    // отправка отчета об ошибке
    @FormUrlEncoded
    @POST("api/client/bug")
    Observable<Response<Success>> sendBugReport(@Field("message") String message,
                                                @Header("publickey")String publickey,
                                                @Field("signature") String signature);

    // гусли
    @GET("api/client/songs")
    Observable<Response<List<Song>>> getOllSongs(@Header("publickey")String publickey,
                                               @Query("signature") String signature);

    @GET("api/client/playlist")
    Observable<Response<PlaylistSongs>> getPlaylist(@Header("publickey")String publickey,
                                                          @Query("signature") String signature);

    @FormUrlEncoded
    @POST("api/client/playlist")
    Observable<Response<Success>> sendMyNextSond(@Header("publickey")String publickey,
                                                 @Field("next") int next,
                                                 @Field("signature") String signature);

    // Уведомления
    @GET("api/client/alerts/latest")
    Observable<Response<LatestVersion>> getLastVersionNotification(@Header("publickey")String publickey,
                                                                   @Query("signature") String signature);

    @GET("api/client/alerts")
    Observable<Response<List<Notificaton>>> getOllNotification(@Header("publickey")String publickey,
                                                               @Query("signature") String signature);

    // ответ на уведомления
    @FormUrlEncoded
    @POST("api/client/alerts/report")
    Observable<Response<Success>> answerUserAboutNotification(@Header("publickey")String publickey,
                                                              @Field("alertId") int alertId,
                                                              @Field("commandId") int commandId,
                                                              @Field("signature") String signature);
}
