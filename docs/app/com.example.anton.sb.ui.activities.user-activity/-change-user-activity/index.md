[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [ChangeUserActivity](./index.md)

# ChangeUserActivity

`class ChangeUserActivity : AppCompatActivity`

A screen changing user information

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `ChangeUserActivity()`<br>A screen changing user information |

### Properties

| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>user session_id |

### Functions

| [changeData](change-data.md) | `fun changeData(firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, telephone: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, about: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Changing user information. This method use [ApiService.changeUser](../../com.example.anton.sb.service/-api-service/change-user.md) and processing response from server. If response is successful, then display message "Данные изменены", else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [changeUser](change-user.md) | `fun changeUser(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Checking entered data and change data or display error. This method called [changeData](change-data.md)`fun changeUser(name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Changing user name in SharedPreference. |
| [readToken](read-token.md) | `fun readToken(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading user session_id  from SharedPreference. |
| [userData](user-data.md) | `fun userData(firstName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, lastName: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, telNumber: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, about: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Get user information. This method use [ApiService.getUserData](../../com.example.anton.sb.service/-api-service/get-user-data.md) and processing response from server. If response is successful, then display user information, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |

