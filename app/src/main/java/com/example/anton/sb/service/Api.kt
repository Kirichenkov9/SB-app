package com.example.anton.sb.service

import android.app.Activity
import android.content.Context
import android.widget.ProgressBar
import com.example.anton.sb.extensions.changeUser
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.extensions.removeUserData
import com.example.anton.sb.extensions.saveUsername
import com.example.anton.sb.model.ResultAd
import com.example.anton.sb.model.ResultUser
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdSettingsActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdsActivity
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.contentView
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * Function for operation with server.
 *
 * @author Anton Kirichenkov
 */
/**
 * Creating ad. This method use [ApiService.createAd] and processing response from server
 * and display it. If response isn't successful, then caused [handleError] for process error.
 *
 * @param title title of ad
 * @param city city of ad
 * @param description description of ad
 * @param priceAd price of ad
 * @param token user-owner session_id
 * @param progressBar_add_ad progressBar
 * @param context
 *
 * @see [ApiService.createAd]
 * @see [handleError]
 */
fun addAd(
    title: String,
    city: String,
    description: String,
    priceAd: Int?,
    token: String,
    progressBar_add_ad: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.createAd(token, title, priceAd, city, description)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({

            progressBar_add_ad.visibility = ProgressBar.INVISIBLE

            context.toast("Объявление добавлено")

            context.startActivity<MyAdsActivity>()

            (context as Activity).finish()
        }, { error ->
            progressBar_add_ad.visibility = ProgressBar.INVISIBLE

            val errorStr = handleError(error)

            if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                removeUserData(context)
                context.startActivity<LoginActivity>()
            } else
                (context as Activity).contentView?.snackbar(errorStr)
        })
}

/**
 * Get ad information. This method use [ApiService.getAd] and processing response from server.
 * If response is successful, then display information about ad, else - display error
 * processing by [handleError].
 *
 * @param adId ad id
 * @param context
 *
 * @return [ResultAd]
 *
 * @see [ApiService.getAd]
 * @see [handleError]
 */
fun adData(
    adId: Long,
    context: Context
): ResultAd? {
    var ad: ResultAd? = null
    val apiService: ApiService = ApiService.create()

    apiService.getAd(adId)
        .subscribe({ result ->
            ad = result
        }, { error ->
            context.runOnUiThread {
                (context as Activity).contentView?.snackbar(handleError(error))
            }
        })
    return ad
}

/**
 * Change ad information. This method use [ApiService.changeAd] and processing response from server.
 * If response is successful, then display "Объявление изменено" and start MyAdSettingsActivity,
 * else - display error processing by [handleError].
 *
 * @param adId ad id
 * @param title title ad
 * @param city city of ad
 * @param description description of ad
 * @param price ad price
 * @param token user session_id
 * @param progressBar_ad_change progressBar
 * @param context
 *
 * @see [handleError]
 * @see [ApiService.changeAd]
 */

fun adChange(
    adId: Long,
    title: String,
    city: String,
    description: String,
    price: Int,
    photo: ArrayList<String>,
    token: String,
    progressBar_ad_change: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.changeAd(adId, token, title, price, city, description, photo)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({
            progressBar_ad_change.visibility = ProgressBar.INVISIBLE
            context.toast("Объявление изменено")

            context.startActivity<MyAdSettingsActivity>("adId" to adId)
            (context as Activity).finish()
        }, { error ->
            progressBar_ad_change.visibility = ProgressBar.INVISIBLE
            val errorStr = handleError(error)
            if (errorStr == "empty body") {
                context.toast("Объявление изменено")

                context.startActivity<MyAdSettingsActivity>("adId" to adId)
                (context as Activity).finish()
            } else
                (context as Activity).contentView?.snackbar(errorStr)
        })
}

/**
 * Get user ads. This method use [ApiService.getUserAd] and processing response from server.
 * If response is successful, then display list of ads, else - display error
 * processing by [handleError].
 *
 * @param idUser ad id
 * @param context
 *
 * @return ArrayList<ResultAd>
 *
 * @see [handleError]
 * @see [ApiService.getUserAd]
 */
fun getUserAd(
    idUser: Long,
    context: Context
): ArrayList<ResultAd> {

    val ads: ArrayList<ResultAd> = ArrayList()
    val apiService: ApiService = ApiService.create()

    apiService.getUserAd(idUser)
        .subscribe({ result ->
            ads.addAll(result)

            if (result.isEmpty())
                context.runOnUiThread {
                    (context as Activity).contentView?.snackbar("Объявлений нет")
                }
        }, { error ->
            val errorStr = handleError(error)
            context.runOnUiThread {
                if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeUserData(context)
                    context.startActivity<LoginActivity>()
                } else
                    (context as Activity).contentView?.snackbar(errorStr)
            }
        })
    return ads
}

/**
 * Deleting ad. This method use [ApiService.deleteAd] and processing response from server.
 * If response is successful, then display message "Объявление удалено" and start MyAdsActivity, else - display error
 * processing by [handleError].
 *
 * @param token user session_id
 * @param adId ad id
 * @param progressBar_ad_settings Progressbar
 * @param context
 */
fun deleteAd(
    adId: Long,
    token: String?,
    progressBar_ad_settings: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.deleteAd(adId, token.toString())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({
            progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
            context.toast("Объявление удалено")

            context.startActivity<MyAdsActivity>()
            (context as Activity).finish()
        }, { error ->
            progressBar_ad_settings.visibility = ProgressBar.INVISIBLE

            val errorStr = handleError(error)

            when (errorStr) {
                "empty body" -> {
                    context.toast("Объявление удалено")

                    context.startActivity<MyAdsActivity>()
                    (context as Activity).finish()
                }
                "Что-то пошло не так... Попробуйте войти в аккаунт заново" -> {
                    context.toast(errorStr)
                    removeUserData(context)
                    context.startActivity<LoginActivity>()
                }
                else -> (context as Activity).contentView?.snackbar(errorStr)
            }
        })
}

/**
 * Get user information. This method use [ApiService.getUserData] and processing response from server.
 * If response is successful, then display user information, else - display error
 * processing by [handleError].
 *
 * @param token user session_id
 * @param context
 *
 * @return [ResultUser] or null
 *
 * @see [ApiService.getUserData]
 * @see [handleError]
 */
fun userData(
    token: String,
    context: Context
): ResultUser? {
    var user: ResultUser? = null
    val apiService: ApiService = ApiService.create()

    apiService.getUserData(token)
        .subscribe({ result ->
            user = result
        }, { error ->
            val errorStr = handleError(error)
            context.runOnUiThread {
                when (errorStr) {
                    "Что-то пошло не так... Попробуйте войти в аккаунт заново" -> {
                        context.toast(errorStr)
                        removeUserData(context)
                        context.startActivity<LoginActivity>()
                    }
                    else -> (context as Activity).contentView?.snackbar(errorStr)
                }
            }
        })
    return user
}

/**
 * Changing user information. This method use [ApiService.changeUser] and processing response from server.
 * If response is successful, then display message "Данные изменены", else - display error
 * processing by [handleError].
 *
 * @param firstName user first name
 * @param lastName user last name
 * @param telephone user phone number
 * @param about information about user
 * @param progressBar_user_change ProgressBar
 * @param context
 *
 * @see [ApiService.changeUser]
 * @see [handleError]
 */
fun changeData(
    token: String?,
    firstName: String,
    lastName: String,
    telephone: String,
    about: String,
    progressBar_user_change: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.changeUser(
        token.toString(), firstName,
        lastName, telephone,
        about
    )
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({
            progressBar_user_change.visibility = ProgressBar.INVISIBLE
            context.toast("Данные изменены")

            changeUser((firstName + " " + lastName), context)

            context.startActivity<UserSettingsActivity>()
            (context as Activity).finish()
        },
            { error ->
                progressBar_user_change.visibility = ProgressBar.INVISIBLE

                val errorString = handleError(error)

                if (errorString == "empty body") {
                    context.toast("Данные изменены")
                    changeUser((firstName + " " + lastName), context)

                    context.startActivity<UserSettingsActivity>()
                    (context as Activity).finish()
                } else
                    (context as Activity).contentView?.snackbar(errorString)
            })
}

/**
 * Get user information. This method use [ApiService.getUserData] and processing response from server.
 * If response is successful, then display user information, else - display error
 * processing by [handleError].
 *
 * @param id user id
 * @param context
 *
 * @return [ResultUser] or null
 * @see [ApiService.getUserData]
 * @see [handleError]
 */
fun userViewData(
    id: Long,
    context: Context
): ResultUser? {
    var user: ResultUser? = null
    val apiService: ApiService = ApiService.create()

    apiService.getUser(id)
        .subscribe({ result ->
            user = result
        }, { error ->
            context.runOnUiThread { (context as Activity).contentView?.snackbar(handleError(error)) }
        })
    return user
}

/**
 * Log in user. This method use [ApiService.loginUser] and processing response from server.
 * If response is successful, user login and saving his email, full name, session_id, else - display error
 * processing by [handleError].
 *
 * @param emailStr user email
 * @param passwordStr user password
 * @param progressBar_login ProgressBar
 * @param context
 *
 * @see [ApiService.loginUser]
 * @see [handleError]
 */
fun login(
    emailStr: String,
    passwordStr: String,
    progressBar_login: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.loginUser(emailStr, passwordStr)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({ result ->
            progressBar_login.visibility = ProgressBar.INVISIBLE
            if (result.code() == 200) {
                context.toast("Привет, ${result.body()!!.first_name}")

                saveUsername(
                    result.headers().get("set-cookie").toString(),
                    result.body()!!.first_name,
                    result.body()!!.last_name,
                    emailStr,
                    result.body()!!.id,
                    context
                )

                context.startActivity<MainActivity>()
                (context as Activity).finish()
            } else
                (context as Activity).contentView?.snackbar("Неверный пароль или логин")
        }, { error ->
            progressBar_login.visibility = ProgressBar.INVISIBLE
            (context as Activity).contentView?.snackbar(handleError(error))
        })
}

/**
 * Creating ad. This method use [ApiService.createUser] and processing response from server
 * and display message "Пользователь зарегистрирован". If response isn't successful,
 * then caused [handleError] for process error.
 *
 * @param firstName user first name
 * @param lastName user last name
 * @param email user email
 * @param password user password
 * @param telephone user telephone
 * @param about information about user
 * @param progressBar_registration ProgressBar
 * @param context
 *
 * @see [ApiService.createUser]
 * @see [handleError]
 */
fun adUser(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
    telephone: String,
    about: String,
    progressBar_registration: ProgressBar,
    context: Context
) {

    val apiService: ApiService = ApiService.create()

    apiService.createUser(firstName, lastName, email, password, telephone, about)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({
            progressBar_registration.visibility = ProgressBar.INVISIBLE
            context.toast("Пользователь зарегистрирован!")

            context.startActivity<MainActivity>()
            (context as Activity).finish()
        }, { error ->
            progressBar_registration.visibility = ProgressBar.INVISIBLE
            (context as Activity).contentView?.snackbar(handleError(error))
        })
}

/**
 * Log out user. This method use [ApiService.logoutUser] and processing response from server.
 * If response is successful, display message "Вы вышли из аккаунта", else - display error
 * processing by [handleError].
 *
 * @param token user session_id
 * @param progressBar_user_settings ProgressBar
 * @param context
 *
 * @see [ApiService.loginUser]
 * @see [handleError]
 */
fun logout(
    token: String?,
    progressBar_user_settings: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.logoutUser(token.toString())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({
            progressBar_user_settings.visibility = ProgressBar.INVISIBLE

            context.toast("Вы вышли из аккаунта")

            removeUserData(context)

            context.startActivity<MainActivity>()
            (context as Activity).finish()
        }, { error ->
            progressBar_user_settings.visibility = ProgressBar.INVISIBLE
            val errorStr = handleError(error)

            when (errorStr) {
                "Что-то пошло не так... Попробуйте войти в аккаунт заново" -> {
                    context.toast(errorStr)
                    removeUserData(context)
                    context.startActivity<LoginActivity>()
                }
                else -> (context as Activity).contentView?.snackbar(errorStr)
            }
        })
}

/**
 * Delete user. This method use [ApiService.deleteUser] and processing response from server.
 * If response is successful, display message "Аккаунт удален", else - display error
 * processing by [handleError].
 *
 * @param token user session_id
 * @param progressBar_user_settings ProgressBar
 * @param context
 *
 * @see [ApiService.deleteUser]
 * @see [handleError]
 */
fun delete(
    token: String?,
    progressBar_user_settings: ProgressBar,
    context: Context
) {
    val apiService: ApiService = ApiService.create()

    apiService.deleteUser(token.toString())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe({
            progressBar_user_settings.visibility = ProgressBar.INVISIBLE

            context.toast("Аккаунт удален")

            removeUserData(context)

            context.startActivity<MainActivity>()
            (context as Activity).finish()
        },
            { error ->
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE

                val errorStr = handleError(error)

                when (errorStr) {
                    "empty body" -> {
                        context.toast("Аккаунт удален")

                        removeUserData(context)
                        context.startActivity<MainActivity>()
                        (context as Activity).finish()
                    }
                    "Что-то пошло не так... Попробуйте войти в аккаунт заново" -> {
                        context.toast(errorStr)
                        removeUserData(context)
                        context.startActivity<LoginActivity>()
                    }
                    else -> (context as Activity).contentView?.snackbar(errorStr)
                }
            })
}

/**
 *  This method uses for update items of recyclerView in MainActivity.
 *  Method called [ApiService.getAds] with parameters dataList.size and limit (10).
 *  If result isn't successful, then display error message from [handleError].
 *
 *  @param dataList [ArrayList]
 *  @param context
 *
 *  @return [ArrayList]
 *
 *  @see ApiService.getAds
 *  @see handleError
 */
fun updateDataList(dataList: ArrayList<ResultAd>, context: Context): ArrayList<ResultAd> {
    val apiService: ApiService = ApiService.create()
    apiService.getAds(dataList.size, 10)
        .subscribe({ result ->
            dataList.addAll(result)
            if (dataList.isEmpty())
                context.runOnUiThread {
                    (context as Activity).contentView?.snackbar("Объявлений нет")
                }
        }, { error ->
            context.runOnUiThread {
                (context as Activity).contentView?.snackbar(handleError(error))
            }
        })
    return dataList
}

/**
 *  This method uses for update items of recyclerView in SearchActivity.
 *  Method called [ApiService.getAdsSearch] with parameters string, searchList.size and limit (10).
 *  If result isn't successful, then display error message from [handleError].
 *
 *  @param searchList [ArrayList]
 *  @param string search line
 *  @param context
 *
 *  @return [ArrayList]
 *
 *  @see ApiService.getAdsSearch
 *  @see handleError
 */
fun updateSearchList(
    searchList: ArrayList<ResultAd>,
    string: String,
    context: Context
): ArrayList<ResultAd> {

    val apiService: ApiService = ApiService.create()

    apiService.getAdsSearch(string, searchList.size, 10)
        .subscribe({ result ->
            searchList.addAll(result)
            if (searchList.isEmpty())
                context.runOnUiThread {
                    (context as Activity).contentView?.snackbar("Объявления не найдены")
                }
        }, { error ->
            context.runOnUiThread {
                (context as Activity).contentView?.snackbar(handleError(error))
            }
        })
    return searchList
}