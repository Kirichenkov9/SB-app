[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [MyAdsActivity](index.md) / [getUserAd](./get-user-ad.md)

# getUserAd

`private fun getUserAd(idUser: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`

Get user ads. This method use [ApiService.getUserAd](../../com.example.anton.sb.service/-api-service/get-user-ad.md) and processing response from server.
If response is successful, then display list of ads, else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`idUser` - ad id

**See Also**

[handleError](../../com.example.anton.sb.extensions/handle-error.md)

[ApiService.getUserAd](../../com.example.anton.sb.service/-api-service/get-user-ad.md)

