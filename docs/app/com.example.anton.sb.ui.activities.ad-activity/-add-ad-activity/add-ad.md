[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [AddAdActivity](index.md) / [addAd](./add-ad.md)

# addAd

`private fun addAd(title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, description: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, priceAd: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`?, token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Creating ad. This method use [ApiService.createAd](../../com.example.anton.sb.service/-api-service/create-ad.md) and processing response from server
and display it. If response isn't successful, then caused [handleError](../../com.example.anton.sb.extensions/handle-error.md) for process error.

### Parameters

`title` - title of ad

`city` - city of ad

`description` - description of ad

`priceAd` - price of ad

`token` - user-owner session_id

**See Also**

[ApiService.createAd](../../com.example.anton.sb.service/-api-service/create-ad.md)

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

