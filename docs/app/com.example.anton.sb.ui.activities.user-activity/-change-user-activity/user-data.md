[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [ChangeUserActivity](index.md) / [userData](./user-data.md)

# userData

`private fun userData(firstName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, lastName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, telNumber: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, about: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Get user information. This method use [ApiService.getUserData](../../com.example.anton.sb.service/-api-service/get-user-data.md) and processing response from server.
If response is successful, then display user information, else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`firstName` - user first name

`lastName` - user last name

`telNumber` - user phone number

`about` - information about user

**See Also**

[ApiService.getUserData](../../com.example.anton.sb.service/-api-service/get-user-data.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

