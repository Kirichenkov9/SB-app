[app](../../index.md) / [com.example.anton.sb.ui.activities](../index.md) / [AboutApp](./index.md)

# AboutApp

`class AboutApp : AppCompatActivity, OnNavigationItemSelectedListener`

A screen information about app

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `AboutApp()`<br>A screen information about app |

### Properties

| [keyToken](key-token.md) | `val keyToken: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>token key for SharedPreference |
| [mail](mail.md) | `val mail: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>email key for SharedPreference |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>saved session_id |
| [username](username.md) | `val username: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>username key for SharedPreference |

### Functions

| [readUserData](read-user-data.md) | `fun readUserData(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading information about user by key from SharedPreference. |

