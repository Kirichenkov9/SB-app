[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [ChangeUserActivity](index.md) / [changeData](./change-data.md)

# changeData

`private fun changeData(firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, telephone: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, about: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Changing user information. This method use [ApiService.changeUser](../../com.example.anton.sb.service/-api-service/change-user.md) and processing response from server.
If response is successful, then display message "Данные изменены", else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`firstName` - user first name

`lastName` - user last name

`telephone` - user phone number

`about` - information about user

**See Also**

[ApiService.changeUser](../../com.example.anton.sb.service/-api-service/change-user.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

