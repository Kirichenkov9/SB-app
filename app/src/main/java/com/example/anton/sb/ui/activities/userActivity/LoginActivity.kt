package com.example.anton.sb.ui.activities.userActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.extensions.isEmailValid
import com.example.anton.sb.extensions.isPasswordValid
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * A login screen that offers login via email/password.
 *
 * @author Anton Kirichenkov
 */
class LoginActivity : AppCompatActivity() {

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        email_sign_in_button.setOnClickListener { attemptLogin() }

        action_go_to_registration.setOnClickListener {
            startActivity<RegistrationActivity>()
        }
    }

    /**
     * @suppress
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        startActivity<MainActivity>()
        return true
    }

    /**
     * Attempts to sign in the account specified by the login form.
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
            login(emailStr, passwordStr)
            progressBar_login.visibility = ProgressBar.VISIBLE
        }
    }

    /**
     * Log in user. This method use [ApiService.loginUser] and processing response from server.
     * If response is successful, user login and saving his email, full name, session_id, else - display error
     * processing by [handleError].
     *
     * @param emailStr user email
     * @param passwordStr user password
     *
     * @see [ApiService.loginUser]
     * @see [handleError]
     */
    private fun login(
        emailStr: String,
        passwordStr: String
    ) {
        val apiService: ApiService = ApiService.create()

        apiService.loginUser(emailStr, passwordStr)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_login.visibility = ProgressBar.INVISIBLE
                if (result.code() == 200) {
                    toast("Привет, ${result.body()!!.first_name}")
                    saveUsername(
                        result.headers().get("set-cookie").toString(),
                        result.body()!!.first_name,
                        result.body()!!.last_name,
                        emailStr,
                        result.body()!!.id
                    )
                    this.finish()
                    startActivity<MainActivity>()
                } else
                    toast("Неверный пароль или логин")
            }, { error ->
                progressBar_login.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
    }

    /**
     * Saving user information in SharedPreference.
     *
     * @param token user session_id
     * @param firstName user first name
     * @param lastName user last name
     * @param email user EMAIL
     */
    private fun saveUsername(token: String, firstName: String, lastName: String, email: String, id: Long) {
        val save: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = save.edit()
        editor.putString("token", token)
        editor.putString("name", firstName + " " + lastName)
        editor.putString("email", email)
        editor.putLong("id", id)
        editor.apply()
    }
}