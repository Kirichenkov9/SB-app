[app](../../index.md) / [com.example.anton.sb.ui.activities.userActivity](../index.md) / [RegistrationActivity](./index.md)

# RegistrationActivity

`class RegistrationActivity : AppCompatActivity`

A screen registration

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `RegistrationActivity()`<br>A screen registration |

### Functions

| [adUser](ad-user.md) | `fun adUser(firstName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, lastName: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, email: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, password: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, telephone: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, about: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Creating ad. This method use [ApiService.createUser](../../com.example.anton.sb.service/-api-service/create-user.md) and processing response from server and display message "Пользователь зарегистрирован". If response isn't successful, then caused [handleError](../../com.example.anton.sb.extensions/handle-error.md) for process error. |
| [attemptForm](attempt-form.md) | `fun attemptForm(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Attempts to register the account specified by the login form. If there are form errors (invalid email, missing fields, etc.), the errors are presented and no actual login attempt is made. |

