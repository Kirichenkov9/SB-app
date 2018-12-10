[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [getUserAd](./get-user-ad.md)

# getUserAd

`@Headers(["User-agent: Android_app"]) @GET("users/{id}?show_ads=true") abstract fun getUserAd(@Path("id") id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>>`

The getUserAd() method is defined to get user ads.
The endpoint is defined as “users/{id}?show_ads=true” with GET HTTP method.

**Path**
id - user id

**Return**
Single&lt;ArrayList&gt;

