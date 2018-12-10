[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [ChangeAdActivity](./index.md)

# ChangeAdActivity

`class ChangeAdActivity : AppCompatActivity`

A screen changing ad

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `ChangeAdActivity()`<br>A screen changing ad |

### Properties

| [adId](ad-id.md) | `var adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>ad id |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>user session_id |

### Functions

| [adChange](ad-change.md) | `fun adChange(adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, price: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, photo: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Change ad information. This method use [ApiService.changeAd](../../com.example.anton.sb.service/-api-service/change-ad.md) and processing response from server. If response is successful, then display "Объявление изменено" and start MyAdSettingsActivity, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [adData](ad-data.md) | `fun adData(adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, city: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, description: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, price: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, photo: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Get ad information. This method use [ApiService.getAd](../../com.example.anton.sb.service/-api-service/get-ad.md) and processing response from server. If response is successful, then display information about ad, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [readToken](read-token.md) | `fun readToken(): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading user  session_id from SharedPreference. |

