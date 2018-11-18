package com.example.anton.sb.data

import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {
    @POST("users/new")
    fun create_user(@Query("first_name") first_name: String,
                     @Query("last_name") last_name: String,
                     @Query("email") email: String,
                     @Query("password_hash") password: String,
                     @Query("telephone") tel_number: String,
                     @Query("about") about_me: String): Single<ResultUser>
    @POST("users/login")
    fun login_user(@Query("email") email: String,
                    @Query("password") password: String): Single<ResultUser>

    @POST("users/logout")
    fun logout_user(@Query("token") token: String): Single<ResultUser>


    @POST("ads/new")
    fun create_ad(@Query("title") title: String,
                  @Query("price") price: Int,
                  @Query("city") city: String,
                  @Query("description_ad") description: String,
                  @Query("token") token: String)

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}