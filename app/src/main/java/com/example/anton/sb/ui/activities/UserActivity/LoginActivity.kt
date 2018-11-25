package com.example.anton.sb.ui.activities.UserActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import java.io.IOException
import java.net.SocketTimeoutException


/**
 * A login screen that offers login via email/password.
 */
@Suppress("UNREACHABLE_CODE")
class LoginActivity : AppCompatActivity() {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        email_sign_in_button.setOnClickListener { attemptLogin() }

        action_go_to_registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptLogin() {
        // Reset errors.
        email.error = null
        password.error = null

        // Store values at the time of the login attempt.
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null

        // Check for a valid password
        if (TextUtils.isEmpty(passwordStr)) {
            password.error = getString(R.string.error_field_required)
            focusView = password
            cancel = true
        } else if (!isPasswordValid(passwordStr)) {
            password.error = getString(R.string.error_invalid_password)
            focusView = password
            cancel = true
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(emailStr)) {
            email.error = getString(R.string.error_field_required)
            focusView = email
            cancel = true
        } else if (!isEmailValid(emailStr)) {
            email.error = getString(R.string.error_invalid_email)
            focusView = email
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner and to
            // perform the user login attempt.
            login()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        //Check entered email
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //Check entered password
        return password.length >= 6
    }


    private fun login() {

        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        val apiService: ApiService = ApiService.create()

        apiService.login_user(emailStr, passwordStr)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                Toast.makeText(this, "Вы успешно вошли в аккаунт!", Toast.LENGTH_SHORT).show()
                saveToken(result.headers().get("set-cookie"))

                saveUsername(result.body()!!.first_name, emailStr, result.body()!!.id)


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } ,{ error -> error.stackTrace

                 handleError(error, "Что-то пошло не так")
            })
    }

    private fun saveToken(string: String?) {
        if (string != null) {
            val save_token: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = save_token.edit()
            editor.putString("token", string)
            editor.apply()
        }
    }

    private fun saveUsername(first_name: String, email: String, id: Long) {
        val save: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = save.edit()
        editor.putString("first_name", first_name)
        editor.putString("email", email)
        editor.putLong("id", id)
        editor.apply()
    }

    private fun handleError(throwable: Throwable, string: String) {
        if (throwable is retrofit2.HttpException) {
            val httpException = throwable
            val statusCode = httpException.code()
            val errorJsonString = httpException.response().errorBody()?.string()

            val message = JsonParser().parse(errorJsonString).asJsonObject["message"].asString
            val error = JsonParser().parse(errorJsonString).asJsonObject["error"].asString
            val description = JsonParser().parse(errorJsonString).asJsonObject["description"].asString

            if (statusCode == 400) {
                Toast.makeText(this, message + error + description, Toast.LENGTH_SHORT).show()

            }
                if (statusCode == 500) {
                    Toast.makeText(this, message + error + description, Toast.LENGTH_SHORT).show()
                }

            } else if (throwable is SocketTimeoutException) {
                Toast.makeText(this, "Нет соединения с сервером", Toast.LENGTH_SHORT).show()
            } else if (throwable is IOException) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
            }

        }
}