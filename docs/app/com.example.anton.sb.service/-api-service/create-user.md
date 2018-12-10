[app](../../index.md) / [com.example.anton.sb.service](../index.md) / [ApiService](index.md) / [createUser](./create-user.md)

# createUser

`@FormUrlEncoded @Headers(["User-agent: Android_app"]) @POST("users/new") abstract fun createUser(@Field("first_name") firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("last_name") lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("email") email: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("password") password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("tel_number") telNumber: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, @Field("about") about: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): Single<`[`ResultUser`](../../com.example.anton.sb.data/-result-user/index.md)`>`

The createUser() method is defined to create user and an Single is returned.
The endpoint is defined as “users/new” with POST HTTP method.

**Field**
first_name - first name of user

**FIeld**
last_name - last name of user

**FIeld**
email - email of user

**FIeld**
password - entered password

**FIeld**
tel_number - phone number of user

**FIeld**
about - information about user

**Return**
Single

