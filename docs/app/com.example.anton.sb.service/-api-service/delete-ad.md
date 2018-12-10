[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [deleteAd](./delete-ad.md)

# deleteAd

`@Headers(["User-agent: Android_app"]) @DELETE("ads/delete/{id}") abstract fun deleteAd(@Path("id") id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, @Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`

The deleteAd() method is defined to delete ad.
The endpoint is defined as "ads/delete/{id}” with DELETE HTTP method.

**Header**
Cookie - session_id of user session

**Path**
id - ad id

**Return**
Single

