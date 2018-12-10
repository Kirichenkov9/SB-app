[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [deleteUser](./delete-user.md)

# deleteUser

`@Headers(["User-agent: Android_app"]) @DELETE("users/profile") abstract fun deleteUser(@Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ResultUser`](../../com.example.anton.sb.data/-result-user/index.md)`>`

The deleteUser() method is defined to delete user.
The endpoint is defined as “users/profile” with DELETE HTTP method.

**Header**
Cookie - session_id of user session

**Return**
Single

