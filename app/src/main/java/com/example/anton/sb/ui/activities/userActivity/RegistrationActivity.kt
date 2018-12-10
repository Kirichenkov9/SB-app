package com.example.anton.sb.ui.activities.userActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.extensions.isEmailValid
import com.example.anton.sb.extensions.isPasswordValid
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * A screen registration
 *
 * @author Anton Kirichenkov
 */
class RegistrationActivity : AppCompatActivity() {

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val buttonRegistration = find<Button>(R.id.button_registration)

        buttonRegistration.setOnClickListener { attemptForm() }
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
        startActivity<LoginActivity>()
        return true
    }

    /**
     * Attempts to register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private fun attemptForm() {
        // Reset errors.
        first_name_registration.error = null
        last_name_registration.error = null
        email_registration.error = null
        phone_number_registration.error = null
        password_registration.error = null
        repeat_password_registration.error = null

        // Store values
        val firstNameRegistrationStr = first_name_registration.text.toString()
        val lastNameRegistrationStr = last_name_registration.text.toString()
        val emailRegistrationStr = email_registration.text.toString()
        val phoneNumberStr = phone_number_registration.text.toString()
        val passwordRegistrationStr = password_registration.text.toString()
        val repeatPassRegistrationStr = repeat_password_registration.text.toString()
        val aboutStr = about_registration.text.toString()

        var cancel = false
        var focusView: View? = null

        // Field check
        if (TextUtils.isEmpty(firstNameRegistrationStr)) {
            first_name_registration.error = getString(R.string.error_field_required)
            focusView = first_name_registration
            cancel = true
        }

        if (TextUtils.isEmpty(lastNameRegistrationStr)) {
            last_name_registration.error = getString(R.string.error_field_required)
            focusView = last_name_registration
            cancel = true
        }

        if (TextUtils.isEmpty(emailRegistrationStr)) {
            email_registration.error = getString(R.string.error_field_required)
            focusView = email_registration
            cancel = true
        } else if (!isEmailValid(emailRegistrationStr)) {
            email_registration.error = getString(R.string.error_invalid_email)
            focusView = email_registration
            cancel = true
        }

        if (TextUtils.isEmpty(phoneNumberStr)) {
            phone_number_registration.error = getString(R.string.error_field_required)
            focusView = phone_number_registration
            cancel = true
        }

        if (TextUtils.isEmpty(passwordRegistrationStr)) {
            password_registration.error = getString(R.string.error_field_required)
            focusView = password_registration
            cancel = true
        } else if (!isPasswordValid(passwordRegistrationStr)) {
            password_registration.error = getString(R.string.error_invalid_password)
            focusView = password_registration
            cancel = true
        }

        if (TextUtils.isEmpty(repeatPassRegistrationStr)) {
            repeat_password_registration.error = getString(R.string.error_field_required)
            focusView = repeat_password_registration
            cancel = true
        } else if (passwordRegistrationStr != repeatPassRegistrationStr) {
            password_registration.error = getString(R.string.error_same_password)
            focusView = repeat_password_registration
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Ad add
            adUser(
                firstNameRegistrationStr,
                lastNameRegistrationStr,
                emailRegistrationStr,
                passwordRegistrationStr,
                phoneNumberStr,
                aboutStr
            )
            progressBar_registration.visibility = ProgressBar.VISIBLE
        }
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
     *
     * @see [ApiService.createUser]
     * @see [handleError]
     */
    private fun adUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        telephone: String,
        about: String
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.createUser(firstName, lastName, email, password, telephone, about)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_registration.visibility = ProgressBar.INVISIBLE
                toast("Пользователь зарегистрирован!")

                this.finish()

                startActivity<MainActivity>()
            }, { error ->
                progressBar_registration.visibility = ProgressBar.INVISIBLE

                toast(handleError(error))
            })
    }
}