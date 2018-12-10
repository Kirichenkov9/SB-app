[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [loginUser](./login-user.md)

# loginUser

`@FormUrlEncoded @Headers(["User-agent: Android_app"]) @POST("users/login") abstract fun loginUser(@Field("email") email: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("password") password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<Response<`[`ResultUser`](../../com.example.anton.sb.data/-result-user/index.md)`>>`

The loginUser() method is defined to login user and an Single&lt;Response&gt; is returned.
The endpoint is defined as “users/login” with POST HTTP method.

**FIeld**
email - email of user

**FIeld**
password - entered password

**Return**
Single&lt;Response&gt;

