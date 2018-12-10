package com.example.anton.sb.service

import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.data.ResultUser
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.* // ktlint-disable no-wildcard-imports

/**
 * Retrofit interface Api
 *
 * @author Anton Kirichenkov
 */
interface ApiService {

    /**
     * The createUser() method is defined to create user and an Single<ResultUser> is returned.
     * The endpoint is defined as “users/new” with POST HTTP method.
     *
     * @Field first_name - first name of user
     * @FIeld last_name - last name of user
     * @FIeld email - email of user
     * @FIeld password - entered password
     * @FIeld tel_number - phone number of user
     * @FIeld about - information about user
     *
     * @return Single<ResultUser>
     */
    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/new")
    fun createUser(
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tel_number") telNumber: String,
        @Field("about") about: String
    ): Single<ResultUser>

    /**
     * The loginUser() method is defined to login user and an Single<Response<ResultUser>> is returned.
     * The endpoint is defined as “users/login” with POST HTTP method.
     *
     * @FIeld email - email of user
     * @FIeld password - entered password
     *
     * @return Single<Response<ResultUser>>
     */
    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<retrofit2.Response<ResultUser>>

    /**
     * The logoutUser() method is defined to logout user.
     * The endpoint is defined as “users/logout” with POST HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @return Single<Response<Void>>
     */
    @Headers("User-agent: Android_app")
    @POST("users/logout")
    fun logoutUser(
        @Header("Cookie") token: String
    ): Single<retrofit2.Response<Void>>

    /**
     * The deleteUser() method is defined to delete user.
     * The endpoint is defined as “users/profile” with DELETE HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @return Single<ResultUser>
     */
    @Headers("User-agent: Android_app")
    @DELETE("users/profile")
    fun deleteUser(
        @Header("Cookie") token: String
    ): Single<ResultUser>

    /**
     * The createAd() method is defined to create ad.
     * The endpoint is defined as "ads/new” with POST HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @Field title - title of ad
     * @Field price - price of ad
     * @Field city - city of ad
     * @Field description_ad - description of ad
     *
     * @return Single<ResultAd>
     */
    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("ads/new")
    fun createAd(
        @Header("Cookie") token: String,
        @Field("title") title: String,
        @Field("price") price: Int?,
        @Field("city") city: String,
        @Field("description_ad") description: String
    ): Single<ResultAd>

    /**
     * The getUserData() method is defined to get information about user.
     * The endpoint is defined as “users/profile” with GET HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @return Single<ResultUser>
     */
    @Headers("User-agent: Android_app")
    @GET("users/profile")
    fun getUserData(
        @Header("Cookie") token: String
    ): Single<ResultUser>

    /**
     * The changeUser() method is defined to change information about user.
     * The endpoint is defined as “users/profile” with POST HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @Field first_name - first name of user
     * @FIeld last_name - last name of user
     * @FIeld tel_number - phone number of user
     * @FIeld about - information about user
     *
     * @return Single<ResultUser>
     */
    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/profile")
    fun changeUser(
        @Header("Cookie") token: String,
        @Field("first_name") firstName: String,
        @Field("last_name") lastName: String,
        @Field("tel_number") telNumber: String,
        @Field("about") about: String
    ): Single<ResultUser>

    /**
     * The getAds() method is defined to get ads.
     * The endpoint is defined as “ads” with GET HTTP method.
     *
     * @Query offset - bias id of ads
     * @Query limit - number ads
     *
     *@return Single<ArrayList<ResultAd>>
     */
    @Headers("User-agent: Android_app")
    @GET("ads")
    fun getAds(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ArrayList<ResultAd>>

    /**
     * The getUserAd() method is defined to get user ads.
     * The endpoint is defined as “users/{id}?show_ads=true” with GET HTTP method.
     *
     * @Path id - user id
     *
     *@return Single<ArrayList<ResultAd>>
     */
    @Headers("User-agent: Android_app")
    @GET("users/{id}?show_ads=true")
    fun getUserAd(@Path(value = "id") id: Long): Single<ArrayList<ResultAd>>

    /**
     * The getAd() method is defined to get ad.
     * The endpoint is defined as “ads/{id}” with GET HTTP method.
     *
     * @Path id - ad id
     *
     *@return Single<ResultAd>
     */
    @Headers("User-agent: Android_app")
    @GET("ads/{id}")
    fun getAd(@Path(value = "id") id: Long): Single<ResultAd>

    /**
     * The getUser() method is defined to get information about user.
     * The endpoint is defined as “users{id}” with GET HTTP method.
     *
     * @Path id - user id
     *
     *@return Single<ResultUser>
     */
    @Headers("User-agent: Android_app")
    @GET("users/{id}")
    fun getUser(@Path(value = "id") id: Long): Single<ResultUser>

    /**
     * The changeAd() method is defined to change information about ad.
     * The endpoint is defined as “ads/edit/{id}” with POST HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @Path id - ad id
     *
     * @Field title - title of ad
     * @Field price - price of ad
     * @Field city - city of ad
     * @Field description_ad - description of ad
     *
     * @return Single<ResultAd>
     */
    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("ads/edit/{id}")
    fun changeAd(
        @Path(value = "id") id: Long,
        @Header("Cookie") token: String,
        @Field("title") title: String,
        @Field("price") price: Int,
        @Field("city") city: String,
        @Field("description_ad") description: String,
        @Field("ad_images") photo: ArrayList<String>?
    ): Single<ResultAd>

    /**
     * The deleteAd() method is defined to delete ad.
     * The endpoint is defined as "ads/delete/{id}” with DELETE HTTP method.
     *
     * @Header Cookie - session_id of user session
     *
     * @Path id - ad id
     *
     * @return Single<ResultAd>
     */
    @Headers("User-agent: Android_app")
    @DELETE("ads/delete/{id}")
    fun deleteAd(
        @Path(value = "id") id: Long,
        @Header("Cookie") token: String
    ): Single<ResultAd>

    /**
     * The getAdsSearch() method is defined to get ads at SearchActivity.
     * The endpoint is defined as “ads” with GET HTTP method.
     *
     * @Query query - searching line
     * @Query offset - bias id of ads
     * @Query limit - number ads
     *
     *@return Single<ArrayList<ResultAd>>
     */
    @Headers("User-agent: Android_app")
    @GET("ads")
    fun getAdsSearch(
        @Query("query") string: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ArrayList<ResultAd>>

    /**
     * Companion object to create the ApiService
     */
    companion object Factory {
        /**
         * To send out network requests to an API,
         * we need to use the Retrofit builder class and specify the base URL for the service.
         */
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://search-build.herokuapp.com/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}