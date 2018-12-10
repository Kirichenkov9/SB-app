[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [changeAd](./change-ad.md)

# changeAd

`@FormUrlEncoded @Headers(["User-agent: Android_app"]) @POST("ads/edit/{id}") abstract fun changeAd(@Path("id") id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, @Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("title") title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("price") price: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @Field("city") city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("description_ad") description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("ad_images") photo: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?): Single<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`

The changeAd() method is defined to change information about ad.
The endpoint is defined as “ads/edit/{id}” with POST HTTP method.

**Header**
Cookie - session_id of user session

**Path**
id - ad id

**Field**
title - title of ad

**Field**
price - price of ad

**Field**
city - city of ad

**Field**
description_ad - description of ad

**Return**
Single

