package com.example.anton.sb.extensions

import android.content.Context
import android.content.SharedPreferences

/**
 * Functions for operation with SharedPreference.
 *
 * @author Anton Kirichenkov
 */

/**
 * Reading information about user by key from SharedPreference.
 *
 * @param key is a key for data from SharedPreference
 * @param context
 */
fun readUserData(key: String, context: Context): String? {
    var string: String? = null
    val read: SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)

    if (read.contains(key)) {
        string = if (key == "id")
            read.getLong(key, 0).toString()
        else
            read.getString(key, " ")
    }
    return string
}

/**
 * Removing information about user by key from SharedPreference.
 *
 * @param context
 */
fun removeUserData(context: Context) {
    val saveToken: SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = saveToken.edit()

    editor.remove("token")
    editor.remove("name")
    editor.remove("mail")
    editor.remove("id")
    editor.clear()
    editor.apply()
}

/**
 * Saving user information in SharedPreference.
 *
 * @param token user session_id
 * @param firstName user first name
 * @param lastName user last name
 * @param email user EMAIL
 * @param id user id
 * @param context
 */

fun saveUsername(token: String, firstName: String, lastName: String, email: String, id: Long, context: Context) {
    val save: SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = save.edit()
    editor.putString("token", token)
    editor.putString("name", "$firstName $lastName")
    editor.putString("email", email)
    editor.putLong("id", id)
    editor.apply()
}

/**
 * Changing user name in SharedPreference.
 *
 * @param name user full name
 * @param context
 */
fun changeUser(name: String, context: Context) {
    val save: SharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE)
    val editor: SharedPreferences.Editor = save.edit()
    editor.remove(name)
    editor.putString("name", name)
    editor.apply()
}
