[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [ChangeAdActivity](index.md) / [adChange](./ad-change.md)

# adChange

`private fun adChange(adId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, price: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, photo: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Change ad information. This method use [ApiService.changeAd](../../com.example.anton.sb.service/-api-service/change-ad.md) and processing response from server.
If response is successful, then display "Объявление изменено" and start MyAdSettingsActivity,
else - display error processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`adId` - ad id

`title` - title ad

`city` - city of ad

`description` - description of ad

`price` - ad price

**See Also**

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

[ApiService.changeAd](../../com.example.anton.sb.service/-api-service/change-ad.md)

