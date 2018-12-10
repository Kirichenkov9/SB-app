[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [MyAdsActivity](./index.md)

# MyAdsActivity

`class MyAdsActivity : AppCompatActivity, OnNavigationItemSelectedListener`

A screen logged in user ads

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `MyAdsActivity()`<br>A screen logged in user ads |

### Properties

| [id](id.md) | `val id: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>user id |
| [keyToken](key-token.md) | `val keyToken: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>token key for SharedPreference |
| [mail](mail.md) | `val mail: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>email key for SharedPreference |
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>username key for SharedPreference |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>saved session_id |

### Functions

| [displayAds](display-ads.md) | `fun displayAds(recyclerView: RecyclerView, idUser: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for display user ads with recyclerView Adapter. |
| [getUserAd](get-user-ad.md) | `fun getUserAd(idUser: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`<br>Get user ads. This method use [ApiService.getUserAd](../../com.example.anton.sb.service/-api-service/get-user-ad.md) and processing response from server. If response is successful, then display list of ads, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [readUserData](read-user-data.md) | `fun readUserData(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading information about user by key from SharedPreference. |
| [removeData](remove-data.md) | `fun removeData(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for remove user data from SharedPreference. |
| [setUsername](set-username.md) | `fun setUsername(nameUser: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, userEmail: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for display full user name and email from SharedPreference on nav_header. If user doesn't login, then display "Войти / зарегистрироваться" |

