[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [UserSettingsActivity](index.md) / [delete](./delete.md)

# delete

`private fun delete(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Delete user. This method use [ApiService.deleteUser](../../com.example.anton.sb.service/-api-service/delete-user.md) and processing response from server.
If response is successful, display message "Аккаунт удален", else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

**See Also**

[ApiService.deleteUser](../../com.example.anton.sb.service/-api-service/delete-user.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

