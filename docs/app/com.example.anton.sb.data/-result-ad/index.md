[app](../../index.md) / [com.example.anton.sb.data](../index.md) / [ResultAd](./index.md)

# ResultAd

`data class ResultAd`

Represents a ResultAd, with the given [id](id.md), [title](title.md), [price](price.md), [city](city.md), [ad_images](ad_images.md), [owner_ad](owner_ad.md), [description_ad](description_ad.md).

### Constructors

| [&lt;init&gt;](-init-.md) | `ResultAd(id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`, title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, price: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`, city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`, ad_images: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?, owner_ad: `[`ResultUser`](../-result-user/index.md)`, description_ad: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`)`<br>Represents a ResultAd, with the given [id](id.md), [title](title.md), [price](price.md), [city](city.md), [ad_images](ad_images.md), [owner_ad](owner_ad.md), [description_ad](description_ad.md). |

### Properties

| [ad_images](ad_images.md) | `var ad_images: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)`>?`<br>images url for ad |
| [city](city.md) | `var city: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>city of ad |
| [description_ad](description_ad.md) | `val description_ad: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>description of ad |
| [id](id.md) | `val id: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>id of ad |
| [owner_ad](owner_ad.md) | `val owner_ad: `[`ResultUser`](../-result-user/index.md)<br>information about user-owner ad |
| [price](price.md) | `var price: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>price of ad |
| [title](title.md) | `var title: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>title of ad |

