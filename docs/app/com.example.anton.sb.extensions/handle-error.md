[app](../index.md) / [com.example.anton.sb.extensions](index.md) / [handleError](./handle-error.md)

# handleError

`fun handleError(throwable: `[`Throwable`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-throwable/index.html)`): `[`String`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

This method check the type of exception and return appropriate string.
If throwable is a HTTPException, then check the code and message of error.

### Parameters

`throwable` - is triggered exception

**Return**
[String](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-string/index.html)

