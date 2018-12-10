[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [SearchActivity](./index.md)

# SearchActivity

`class SearchActivity : AppCompatActivity`

A screen searching ad

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `SearchActivity()`<br>A screen searching ad |

### Properties

| [request](request.md) | `var request: `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)<br>searching line |

### Functions

| [displayAds](display-ads.md) | `fun displayAds(list: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>, searchText: `[`EditText`](https://developer.android.com/reference/android/widget/EditText.html)`, recyclerView: RecyclerView, layoutManager: LinearLayoutManager): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Method for display searching ads with recyclerView Adapter. If have problems with connection server or no ads, then display message "Объявлений нет". |

