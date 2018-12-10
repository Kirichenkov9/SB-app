[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [UserSettingsActivity](index.md) / [logout](./logout.md)

# logout

`private fun logout(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Log out user. This method use [ApiService.logoutUser](../../com.example.anton.sb.service/-api-service/logout-user.md) and processing response from server.
If response is successful, display message "Вы вышли из аккаунта", else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

**See Also**

[ApiService.loginUser](../../com.example.anton.sb.service/-api-service/login-user.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

