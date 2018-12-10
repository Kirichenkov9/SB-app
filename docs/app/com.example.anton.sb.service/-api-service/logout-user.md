[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [logoutUser](./logout-user.md)

# logoutUser

`@Headers(["User-agent: Android_app"]) @POST("users/logout") abstract fun logoutUser(@Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<Response<`[`Void`](https://developer.android.com/reference/java/lang/Void.html)`>>`

The logoutUser() method is defined to logout user.
The endpoint is defined as “users/logout” with POST HTTP method.

**Header**
Cookie - session_id of user session

**Return**
Single&lt;Response&gt;

