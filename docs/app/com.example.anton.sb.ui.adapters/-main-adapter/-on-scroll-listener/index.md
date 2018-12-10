[app](../../../index.md) / [com.example.anton.sb.ui.adapters](../../index.md) / [MainAdapter](../index.md) / [OnScrollListener](./index.md)

# OnScrollListener

`class OnScrollListener : OnScrollListener`

Class for automatic update list of ads.

**Return**
RecyclerView.OnScrollListener()

### Constructors

| [&lt;init&gt;](-init-.md) | `OnScrollListener(layoutManager: LinearLayoutManager, adapter: Adapter<`[`ViewHolder`](../-view-holder/index.md)`>, dataList: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../../com.example.anton.sb.data/-result-ad/index.md)`>)`<br>Class for automatic update list of ads. |

### Properties

| [adapter](adapter.md) | `val adapter: Adapter<`[`ViewHolder`](../-view-holder/index.md)`>` |
| [dataList](data-list.md) | `val dataList: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../../com.example.anton.sb.data/-result-ad/index.md)`>` |
| [firstVisibleItem](first-visible-item.md) | `var firstVisibleItem: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Position of first visible item |
| [layoutManager](layout-manager.md) | `val layoutManager: LinearLayoutManager` |
| [loading](loading.md) | `var loading: `[`Boolean`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-boolean/index.html)<br>Flag for update list of ads |
| [previousTotal](previous-total.md) | `var previousTotal: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Previous(before update) total item on recyclerView. |
| [totalItemCount](total-item-count.md) | `var totalItemCount: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Total iten count on list |
| [visibleItemCount](visible-item-count.md) | `var visibleItemCount: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Count of visible item |
| [visibleThreshold](visible-threshold.md) | `val visibleThreshold: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)<br>Threshold of visible item |

