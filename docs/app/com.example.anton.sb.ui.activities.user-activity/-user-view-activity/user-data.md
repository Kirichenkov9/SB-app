[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [UserViewActivity](index.md) / [userData](./user-data.md)

# userData

`private fun userData(firstName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, lastName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, email: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, telephone: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, about: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Get user information. This method use [ApiService.getUser](../../com.example.anton.sb.service/-api-service/get-user.md) and processing response from server.
If response is successful, then display user information, else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`firstName` - user first name

`lastName` - user last name

`telephone` - user phone number

`about` - information about user

**See Also**

[ApiService.getUserData](../../com.example.anton.sb.service/-api-service/get-user-data.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

