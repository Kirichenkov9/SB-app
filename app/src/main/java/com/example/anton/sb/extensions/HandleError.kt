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
    if (throwable is retrofit2.HttpException) {
        val statusCode = throwable.code()
        val errorJsonString = throwable.response().errorBody()?.string()
        val error = JsonParser().parse(errorJsonString).asJsonObject["error"].asString

        if (statusCode == 400) {
            if (error == "UserIsExistsError")
                string = " Пользователь уже существует"
            else if (error == "BadAuth")
                string = "Неверный логин или пароль"
            else if (error == "RequestDataValidError")
                string = "Данные введены неверно"
        }

        if (statusCode == 401) {
            string = "Что-то пошло не так... Попробуйте войти в аккаунт заново"
        }

        if (statusCode == 403) {
            string = "Что-то пошло не так... Попробуйте войти в аккаунт заново"
        }

        if (statusCode == 500) {
            if (error == "BadCookieError")
                string = "Что-то пошло не так... Попробуйте войти в аккаунт заново"
            else
                string = "Нет соединения с сервером"
        }
    } else if (throwable is SocketTimeoutException)
        string = "Нет соединения с сервером"
    else if (throwable is EOFException)
        string = "empty body"
    else if (throwable is IOException)
        string = "Нет соединения с сервером"
    else
        string = "Что-то пошло не так..."

    return string
}
