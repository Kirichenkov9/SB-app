[app](../../index.md) / [com.example.anton.sb.ui.adapters](../index.md) / [MainAdapter](./index.md)

# MainAdapter

`class MainAdapter : Adapter<`[`ViewHolder`](-view-holder/index.md)`>`

RecyclerView Adapter for display ads

**Author**
Anton Kirichenkov

### Types

| [OnItemClickListener](-on-item-click-listener.md) | `interface OnItemClickListener`<br>Interface for processing click on ad |
| [OnScrollListener](-on-scroll-listener/index.md) | `class OnScrollListener : OnScrollListener`<br>Class for automatic update list of ads. |
| [ViewHolder](-view-holder/index.md) | `class ViewHolder : ViewHolder`<br>Class for interaction with layout |

### Constructors

| [&lt;init&gt;](-init-.md) | `MainAdapter(ads: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>, itemClick: `[`OnItemClickListener`](-on-item-click-listener.md)`)`<br>RecyclerView Adapter for display ads |

### Properties

| [ads](ads.md) | `val ads: `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>` |
| [itemClick](item-click.md) | `val itemClick: `[`OnItemClickListener`](-on-item-click-listener.md) |

