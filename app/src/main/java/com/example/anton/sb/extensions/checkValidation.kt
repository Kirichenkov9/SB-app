package com.example.anton.sb.extensions

import java.util.regex.Pattern

/**
 * @author Anton Kirichenkov
 */

/**
 * This function check valid of email at login and registration user.
 *
 * @param email [String]
 *
 * @return [Boolean]
 */
fun isEmailValid(email: String): Boolean {
    // Check entered email
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    val pattern = Pattern.compile(emailPattern)
    val matcher = pattern.matcher(email)
    return matcher.matches()
}

/**
 * This function check valid of password at login and registration user.
 *
 * @param password [String]
 *
 * @return [Boolean]
 */
fun isPasswordValid(password: String): Boolean {
    // Check entered password
    return password.length >= 6
}
