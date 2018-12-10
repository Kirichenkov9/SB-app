[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [AddAdActivity](./index.md)

# AddAdActivity

`class AddAdActivity : AppCompatActivity, OnNavigationItemSelectedListener`

A screen of adding ad

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `AddAdActivity()`<br>A screen of adding ad |

### Properties

| [keyToken](key-token.md) | `val keyToken: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>token key for SharedPreference |
| [mail](mail.md) | `val mail: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>email key for SharedPreference |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>saved session_id |
| [username](username.md) | `val username: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>username key for SharedPreference |

### Functions

| [addAd](add-ad.md) | `fun addAd(title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, priceAd: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`?, token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Creating ad. This method use [ApiService.createAd](../../com.example.anton.sb.service/-api-service/create-ad.md) and processing response from server and display it. If response isn't successful, then caused [handleError](../../com.example.anton.sb.extensions/handle-error.md) for process error. |
| [attemptForm](attempt-form.md) | `fun attemptForm(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Attempts to adding ad specified by the adding form. If there are form errors (invalid title, missing fields, etc.), the errors are presented and no actual adding attempt is made. |
| [readUserData](read-user-data.md) | `fun readUserData(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading information about user by key from SharedPreference. |
| [removeUserData](remove-user-data.md) | `fun removeUserData(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Removing information about user by key from SharedPreference. |

