[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [createAd](./create-ad.md)

# createAd

`@FormUrlEncoded @Headers(["User-agent: Android_app"]) @POST("ads/new") abstract fun createAd(@Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("title") title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("price") price: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`?, @Field("city") city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("description_ad") description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`

The createAd() method is defined to create ad.
The endpoint is defined as "ads/new” with POST HTTP method.

**Header**
Cookie - session_id of user session

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

