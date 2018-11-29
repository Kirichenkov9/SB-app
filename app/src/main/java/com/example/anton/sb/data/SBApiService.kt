package com.example.anton.sb.data

import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.data.ResponseClasses.ResultUser
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

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
    ): Single<Response<ResultUser>>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/login")
    fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String
    ): Single<Response<ResultUser>>


    @Headers("User-agent: Android_app")
    @POST("users/logout")
    fun logoutUser(
        @Header("Cookie") token: String
    ): Observable<Response<ResultUser>>

    @Headers("User-agent: Android_app")
    @DELETE("users/profile")
    fun deleteUser(
        @Header("Cookie") token: String
    ): Single<Response<ResultUser>>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("ads/new")
    fun createAd(
        @Header("Cookie") token: String,
        @Field("title") title: String,
        @Field("price") price: Int,
        @Field("city") city: String,
        @Field("description_ad") description: String
    ): Single<Response<ResultAd>>

    @Headers("User-agent: Android_app")
    @GET("users/profile")
    fun getUserData(
        @Header("Cookie") token: String
    ): Single<Response<ResultUser>>

    @FormUrlEncoded
    @Headers("User-agent: Android_app")
    @POST("users/profile")
    fun changeUser(
        @Header("Cookie") token: String,
        @Field("first_name") first_name: String,
        @Field("last_name") last_name: String,
        @Field("tel_number") tel_number: String,
        @Field("about") about_me: String
    ): Single<Response<ResultUser>>

    @Headers("User-agent: Android_app")
    @GET("ads")
    fun getAds(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): Single<Response<ArrayList<ResultAd>>>

    @Headers("User-agent: Android_app")
    @GET("users/{id}?show_ads=true")
    fun getUserAd(@Path(value = "id") id: Long): Single<Response<ArrayList<ResultAd>>>

    @Headers("User-agent: Android_app")
    @GET("ads/{id}")
    fun getAd(@Path(value = "id") id: Long): Single<Response<ResultAd>>

    @Headers("User-agent: Android_app")
    @GET("users/{id}")
    fun getUser(@Path(value = "id") id: Long): Single<Response<ResultUser>>

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
    ): Single<Response<ResultAd>>

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://10.0.2.2:8080/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}