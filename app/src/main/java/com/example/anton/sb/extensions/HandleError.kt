package com.example.anton.sb.extensions

import com.google.gson.JsonParser
import java.io.EOFException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * @author Anton Kirichenkov
 *
 */
/**
 * This method check the type of exception and return appropriate string.
 * If throwable is a HTTPException, then check the code and message of error.
 *
 * @param throwable is triggered exception
 *
 * @return [String]
 */
fun handleError(throwable: Throwable): String {
    var string: String = ""
    when (throwable) {
        is retrofit2.HttpException -> {
            val statusCode = throwable.code()
            val errorJsonString = throwable.response().errorBody()?.string()
            val error = JsonParser().parse(errorJsonString).asJsonObject["error"].asString

            when (statusCode) {
                400 -> {
                    when (error) {
                        "UserIsExistsError" -> string = " Пользователь уже существует"
                        "BadAuth" -> string = "Неверный логин или пароль"
                        "RequestDataValidError" -> string = "Данные введены неверно"
                    }
                }

                401 -> string = "Что-то пошло не так... Попробуйте войти в аккаунт заново"

                403 -> string = "Что-то пошло не так... Попробуйте войти в аккаунт заново"


                500 ->  {
                    string = if (error == "BadCookieError")
                        "Что-то пошло не так... Попробуйте войти в аккаунт заново"
                    else
                        "Нет соединения с сервером"
                }
            }
        }
        is SocketTimeoutException -> string = "Нет соединения с сервером"
        is EOFException -> string = "empty body"
        is IOException -> string = "Нет соединения с сервером"
        else -> string = "Что-то пошло не так..."
    }

    return string
}
