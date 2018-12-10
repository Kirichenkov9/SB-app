[app](../index.md) / [com.example.anton.sb.extensions](index.md) / [updateSearchList](./update-search-list.md)

# updateSearchList

`fun updateSearchList(searchList: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../com.example.anton.sb.data/-result-ad/index.md)`>, string: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`): `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../com.example.anton.sb.data/-result-ad/index.md)`>`

This method uses for update items of recyclerView in SearchActivity.
Method called [ApiService.getAdsSearch](../com.example.anton.sb.service/-api-service/get-ads-search.md) with parameters string, searchList.size and limit (10).

### Parameters

`searchList` - [ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)

**Return**
[ArrayList](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)

**See Also**

[ApiService.getAdsSearch](../com.example.anton.sb.service/-api-service/get-ads-search.md)

