[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [MainActivity](./index.md)

# MainActivity

`class MainActivity : AppCompatActivity, OnNavigationItemSelectedListener`

A main screen of app with updating list of ads.

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `MainActivity()`<br>A main screen of app with updating list of ads. |

### Properties

| [keyToken](key-token.md) | `val keyToken: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>token key for SharedPreference |
| [mail](mail.md) | `val mail: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>email key for SharedPreference |
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>username key for SharedPreference |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>saved session_id |

### Functions

| [displayAds](display-ads.md) | `fun displayAds(list: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>, recyclerView: RecyclerView, layoutManager: LinearLayoutManager): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for display ads with recyclerView Adapter. If have problems with connection server or no ads, then display message "Объявлений нет". |
| [readUserData](read-user-data.md) | `fun readUserData(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading information about user by key from SharedPreference. |
| [setUsername](set-username.md) | `fun setUsername(nameUser: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, userEmail: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for display full user name and email from SharedPreference on nav_header. If user doesn't login, then display "Войти / зарегистрироваться" |

