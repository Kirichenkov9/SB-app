[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [getAds](./get-ads.md)

# getAds

`@Headers(["User-agent: Android_app"]) @GET("ads") abstract fun getAds(@Query("offset") offset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @Query("limit") limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>>`

The getAds() method is defined to get ads.
The endpoint is defined as “ads” with GET HTTP method.

**Query**
offset - bias id of ads

**Query**
limit - number ads

**Return**
Single&lt;ArrayList&gt;

