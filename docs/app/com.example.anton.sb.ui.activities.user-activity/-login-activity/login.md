[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [LoginActivity](index.md) / [login](./login.md)

# login

`private fun login(emailStr: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, passwordStr: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Log in user. This method use [ApiService.loginUser](../../com.example.anton.sb.service/-api-service/login-user.md) and processing response from server.
If response is successful, user login and saving his email, full name, session_id, else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`emailStr` - user email

`passwordStr` - user password

**See Also**

[ApiService.loginUser](../../com.example.anton.sb.service/-api-service/login-user.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

