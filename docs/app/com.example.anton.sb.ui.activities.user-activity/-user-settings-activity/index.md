[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [UserSettingsActivity](./index.md)

# UserSettingsActivity

`class UserSettingsActivity : AppCompatActivity, OnNavigationItemSelectedListener`

A screen user settings

### Constructors

| [&lt;init&gt;](-init-.md) | `UserSettingsActivity()`<br>A screen user settings |

### Properties

| [keyToken](key-token.md) | `val keyToken: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>token key for SharedPreference |
| [mail](mail.md) | `val mail: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>email key for SharedPreference |
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>username key for SharedPreference |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>saved session_id |

### Functions

| [delete](delete.md) | `fun delete(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Delete user. This method use [ApiService.deleteUser](../../com.example.anton.sb.service/-api-service/delete-user.md) and processing response from server. If response is successful, display message "Аккаунт удален", else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [logout](logout.md) | `fun logout(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Log out user. This method use [ApiService.logoutUser](../../com.example.anton.sb.service/-api-service/logout-user.md) and processing response from server. If response is successful, display message "Вы вышли из аккаунта", else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [readUserData](read-user-data.md) | `fun readUserData(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading user data by key from SharedPreference. |
| [removeData](remove-data.md) | `fun removeData(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for remove user data from SharedPreference. |
| [userData](user-data.md) | `fun userData(firstName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, lastName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, email: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, telephone: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, about: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Get user information. This method use [ApiService.getUserData](../../com.example.anton.sb.service/-api-service/get-user-data.md) and processing response from server. If response is successful, then display user information, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |

