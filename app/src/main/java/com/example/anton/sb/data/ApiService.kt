package com.example.anton.sb.data

import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.data.ResponseClasses.ResultUser
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Path

interface ApiService {
    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/new")
    fun createUser(
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("tel_number") tel_number: String,
        @Field("about") about_me: String
    ): Single<ResultUser>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<retrofit2.Response<ResultUser>>

    @Headers("User-agent: Android_app")
    @POST("users/logout")
    fun logoutUser(
        @Header("Cookie") token: String
    ): Single<retrofit2.Response<Void>>

    @Headers("User-agent: Android_app")
    @DELETE("users/profile")
    fun deleteUser(
        @Header("Cookie") token: String
    ): Single<ResultUser>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("ads/new")
    fun createAd(
        @Header("Cookie") token: String,
        @Field("title") title: String,
        @Field("price") price: Int,
        @Field("city") city: String,
        @Field("description_ad") description: String
    ): Single<ResultAd>

    @Headers("User-agent: Android_app")
    @GET("users/profile")
    fun getUserData(
        @Header("Cookie") token: String
    ): Single<ResultUser>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/profile")
    fun changeUser(
        @Header("Cookie") token: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("tel_number") tel_number: String,
        @Field("about") about_me: String
    ): Single<ResultUser>

    @Headers("User-agent: Android_app")
    @GET("ads")
    fun getAds(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ArrayList<ResultAd>>

    @Headers("User-agent: Android_app")
    @GET("users/{id}?show_ads=true")
    fun getUserAd(@Path(value = "id") id: Long): Single<ArrayList<ResultAd>>

    @Headers("User-agent: Android_app")
    @GET("ads/{id}")
    fun getAd(@Path(value = "id") id: Long): Single<ResultAd>

    @Headers("User-agent: Android_app")
    @GET("users/{id}")
    fun getUser(@Path(value = "id") id: Long): Single<ResultUser>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("ads/edit/{id}")
    fun changeAd(
        @Path(value = "id") id: Long,
        @Header("Cookie") token: String,
        @Field("title") title: String,
        @Field("price") price: Int,
        @Field("city") city: String,
        @Field("description_ad") description: String
    ): Single<ResultAd>

    @Headers("User-agent: Android_app")
    @DELETE("ads/delete/{id}")
    fun deleteAd(
        @Path(value = "id") id: Long,
        @Header("Cookie") token: String
    ): Single<ResultAd>

    @Headers("User-agent: Android_app")
    @GET("ads")
    fun getAdsSearch(
        @Query("query") string: String,
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<ArrayList<ResultAd>>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://protected-bayou-62297.herokuapp.com/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}