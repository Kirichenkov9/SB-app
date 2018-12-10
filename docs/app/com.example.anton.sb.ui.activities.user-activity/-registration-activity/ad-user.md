[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [RegistrationActivity](index.md) / [adUser](./ad-user.md)

# adUser

`private fun adUser(firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, email: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, telephone: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, about: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Creating ad. This method use [ApiService.createUser](../../com.example.anton.sb.service/-api-service/create-user.md) and processing response from server
and display message "Пользователь зарегистрирован". If response isn't successful,
then caused [handleError](../../com.example.anton.sb.extensions/handle-error.md) for process error.

### Parameters

`firstName` - user first name

`lastName` - user last name

`email` - user email

`password` - user password

`telephone` - user telephone

`about` - information about user

**See Also**

[ApiService.createUser](../../com.example.anton.sb.service/-api-service/create-user.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

