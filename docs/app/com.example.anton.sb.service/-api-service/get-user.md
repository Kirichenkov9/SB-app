[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [getUser](./get-user.md)

# getUser

`@Headers(["User-agent: Android_app"]) @GET("users/{id}") abstract fun getUser(@Path("id") id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): Single<`[`ResultUser`](../../com.example.anton.sb.data/-result-user/index.md)`>`

The getUser() method is defined to get information about user.
The endpoint is defined as “users{id}” with GET HTTP method.

**Path**
id - user id

**Return**
Single

