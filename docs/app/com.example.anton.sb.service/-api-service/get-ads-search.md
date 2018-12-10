[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [getAdsSearch](./get-ads-search.md)

# getAdsSearch

`@Headers(["User-agent: Android_app"]) @GET("ads") abstract fun getAdsSearch(@Query("query") string: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Query("offset") offset: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, @Query("limit") limit: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`): Single<`[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>>`

The getAdsSearch() method is defined to get ads at SearchActivity.
The endpoint is defined as “ads” with GET HTTP method.

**Query**
query - searching line

**Query**
offset - bias id of ads

**Query**
limit - number ads

**Return**
Single&lt;ArrayList&gt;

