[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [AdViewActivity](index.md) / [adData](./ad-data.md)

# adData

`private fun adData(adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, city: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, description: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, price: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, username: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, telephone: `[`TextView`](https://developer.android.com/reference/android/widget/TextView.html)`, photo: `[`ImageView`](https://developer.android.com/reference/android/widget/ImageView.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Get ad information. This method use [ApiService.getAd](../../com.example.anton.sb.service/-api-service/get-ad.md) and processing response from server.
If response is successful, then display information about ad, else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`adId` - ad id

`title` - title ad

`city` - city of ad

`description` - description of ad

`price` - ad price

`username` - full name of owner ad

`telephone` - phone number of owner ad

`photo` - ad photo

**See Also**

[ApiService.getAd](../../com.example.anton.sb.service/-api-service/get-ad.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

