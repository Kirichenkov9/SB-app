[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [changeUser](./change-user.md)

# changeUser

`@FormUrlEncoded @Headers(["User-agent: Android_app"]) @POST("users/profile") abstract fun changeUser(@Header("Cookie") token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("first_name") firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("last_name") lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("tel_number") telNumber: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("about") about: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ResultUser`](../../com.example.anton.sb.data/-result-user/index.md)`>`

The changeUser() method is defined to change information about user.
The endpoint is defined as “users/profile” with POST HTTP method.

**Header**
Cookie - session_id of user session

**Field**
first_name - first name of user

**FIeld**
last_name - last name of user

**FIeld**
tel_number - phone number of user

**FIeld**
about - information about user

**Return**
Single

