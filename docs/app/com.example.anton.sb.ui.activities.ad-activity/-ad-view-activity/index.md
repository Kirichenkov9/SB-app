[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [AdViewActivity](./index.md)

# AdViewActivity

`class AdViewActivity : AppCompatActivity`

A screen of ad information

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `AdViewActivity()`<br>A screen of ad information |

### Properties

| [adId](ad-id.md) | `var adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>ad id |
| [phone](phone.md) | `var phone: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>phone number of owner ad |
| [preAdId](pre-ad-id.md) | `var preAdId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>id of previous ad |
| [preUserId](pre-user-id.md) | `var preUserId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>this variable for check previous Activity |
| [userId](user-id.md) | `var userId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>id of owner ad |

### Functions

| [adData](ad-data.md) | `fun adData(adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, city: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, description: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, price: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, username: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, telephone: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, photo: `[`ImageView`](https://developer.android.com/reference/android/widget/ImageView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Get ad information. This method use [ApiService.getAd](../../com.example.anton.sb.service/-api-service/get-ad.md) and processing response from server. If response is successful, then display information about ad, else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md). |
| [makePhoneCall](make-phone-call.md) | `fun makePhoneCall(string: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Make phone call by number of owner ad. This method check availability permission for making call. |

