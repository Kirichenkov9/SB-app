[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [UserAdActivity](index.md) / [getUserAd](./get-user-ad.md)

# getUserAd

`private fun getUserAd(id_user: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`

Get user sds. This method called [ApiService.getUserAd](../../com.example.anton.sb.service/-api-service/get-user-ad.md) and processing response from server
and display ads. If response isn't successful, then caused [handleError](../../com.example.anton.sb.extensions/handle-error.md) for process error.

### Parameters

`id_user` - user id

**See Also**

[ApiService.getUserAd](../../com.example.anton.sb.service/-api-service/get-user-ad.md)

