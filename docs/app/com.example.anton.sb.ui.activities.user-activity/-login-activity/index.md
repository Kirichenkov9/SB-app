[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [LoginActivity](./index.md)

# LoginActivity

`class LoginActivity : AppCompatActivity`

A login screen that offers login via email/password.

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `LoginActivity()`<br>A login screen that offers login via email/password. |

### Functions

| [attemptLogin](attempt-login.md) | `fun attemptLogin(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Attempts to sign in the account specified by the login form. If there are form errors (invalid email, missing fields, etc.), the errors are presented and no actual login attempt is made. |
| [login](login.md) | `fun login(emailStr: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, passwordStr: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Log in user. This method use [ApiService.loginUser](../../com.example.anton.sb.service/-api-service/login-user.md) and processing response from server. If response is successful, user login and saving his email, full name, session_id, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [saveUsername](save-username.md) | `fun saveUsername(token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, email: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Saving user information in SharedPreference. |

