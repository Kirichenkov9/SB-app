[app](../../index.md) / [com.example.anton.sb.ui.activities.adActivity](../index.md) / [UserAdActivity](./index.md)

# UserAdActivity

`class UserAdActivity : AppCompatActivity`

A screen user ads

**Author**
Anton Kirichenkov

### Constructors

| [&lt;init&gt;](-init-.md) | `UserAdActivity()`<br>A screen user ads |

### Properties

| [preAdId](pre-ad-id.md) | `var preAdId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>previous ad id |
| [userId](user-id.md) | `var userId: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)<br>user id |

### Functions

| [getUserAd](get-user-ad.md) | `fun getUserAd(id_user: `[`Long`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-long/index.html)`): `[`ArrayList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-array-list/index.html)`<`[`ResultAd`](../../com.example.anton.sb.data/-result-ad/index.md)`>`<br>Get user sds. This method called [ApiService.getUserAd](../../com.example.anton.sb.service/-api-service/get-user-ad.md) and processing response from server and display ads. If response isn't successful, then caused [handleError](../../com.example.anton.sb.extensions/handle-error.md) for process error. |

