package com.example.anton.sb.ui.activities.UserActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val buttonRegistration = findViewById<Button>(R.id.button_registration)

        buttonRegistration.setOnClickListener { attemptForm() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        startActivity<LoginActivity>()
        return true
    }

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
            adUser()
            progressBar_registration.visibility = ProgressBar.VISIBLE
        }
    }

    private fun isEmailValid(email: String): Boolean {
        // Check entered email
        val emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
        val pattern = Pattern.compile(emailPattern)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        // Check entered password
        return password.length >= 6
    }

    private fun adUser() {

        val firstNameStr = first_name_registration.text.toString()
        val lastNameStr = last_name_registration.text.toString()
        val emailStr = email_registration.text.toString()
        val telNumberStr = phone_number_registration.text.toString()
        val passwordStr = password_registration.text.toString()
        val aboutStr = about_registration.text.toString()

        val apiService: ApiService = ApiService.create()

        apiService.createUser(firstNameStr, lastNameStr, emailStr, passwordStr, telNumberStr, aboutStr)
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