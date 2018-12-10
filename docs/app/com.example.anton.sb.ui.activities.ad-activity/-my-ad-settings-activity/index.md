[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [MyAdSettingsActivity](./index.md)

# MyAdSettingsActivity

`class MyAdSettingsActivity : AppCompatActivity`

A screen ad view of logged ih user

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `MyAdSettingsActivity()`<br>A screen ad view of logged ih user |

### Properties

| [adId](ad-id.md) | `var adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>ad id |
| [keyToken](key-token.md) | `val keyToken: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>token key for SharedPreference |
| [mail](mail.md) | `val mail: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>email key for SharedPreference |
| [name](name.md) | `val name: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>username key for SharedPreference |
| [token](token.md) | `var token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>saved session_id |

### Functions

| [adData](ad-data.md) | `fun adData(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, city: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, description: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, price: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, photo: `[`ImageView`](https://developer.android.com/reference/android/widget/ImageView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Get ad information. This method use [ApiService.getAd](../../com.example.anton.sb.service/-api-service/get-ad.md) and processing response from server. If response is successful, then display information about ad, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [deleteAd](delete-ad.md) | `fun deleteAd(token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Deleting ad. This method use [ApiService.deleteAd](../../com.example.anton.sb.service/-api-service/delete-ad.md) and processing response from server. If response is successful, then display message "Объявление удалено" and start MyAdsActivity, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [readData](read-data.md) | `fun readData(key: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?`<br>Reading information about user by key from SharedPreference. |
| [removeData](remove-data.md) | `fun removeData(): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for remove user data from SharedPreference. |

