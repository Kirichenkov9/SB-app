[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [getUserData](./get-user-data.md)

# getUserData

`@Headers(["User-agent: Android_app"]) @GET("users/profile") abstract fun getUserData(@Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ResultUser`](../../com.example.anton.sb.data/-result-user/index.md)`>`

The getUserData() method is defined to get information about user.
The endpoint is defined as “users/profile” with GET HTTP method.

**Header**
Cookie - session_id of user session

**Return**
Single

