[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [MyAdSettingsActivity](index.md) / [deleteAd](./delete-ad.md)

# deleteAd

`private fun deleteAd(token: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`?): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)

Deleting ad. This method use [ApiService.deleteAd](../../com.example.anton.sb.service/-api-service/delete-ad.md) and processing response from server.
If response is successful, then display message "Объявление удалено" and start MyAdsActivity, else - display error
processing by [handleError](../../com.example.anton.sb.extensions/handle-error.md).

### Parameters

`token` - user session_id