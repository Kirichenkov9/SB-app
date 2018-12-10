[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [getAd](./get-ad.md)

# getAd

`@Headers(["User-agent: Android_app"]) @GET("ads/{id}") abstract fun getAd(@Path("id") id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`

The getAd() method is defined to get ad.
The endpoint is defined as “ads/{id}” with GET HTTP method.

**Path**
id - ad id

**Return**
Single

