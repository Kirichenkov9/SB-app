[app](../index.md) / [com.example.anton.sb.extensions](index.md) / [updateDataList](./update-data-list.md)

# updateDataList

`fun updateDataList(dataList: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../com.example.anton.sb.data/-result-ad/index.md)`>): `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../com.example.anton.sb.data/-result-ad/index.md)`>`

This method uses for update items of recyclerView in MainActivity.
Method called [ApiService.getAds](../com.example.anton.sb.service/-api-service/get-ads.md) with parameters dataList.size and limit (10).

### Parameters

`dataList` - [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)

**Return**
[ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)

**See Also**

[ApiService.getAds](../com.example.anton.sb.service/-api-service/get-ads.md)

