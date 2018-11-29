package com.example.anton.sb.ui.activities.UserActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import com.google.gson.JsonParser
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*
import java.io.IOException
import java.net.SocketTimeoutException

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
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
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
                result ->
                result.code()
                Log.d("Result",  "Success" +  result.code())
                Toast.makeText(this, "Пользователь зарегистрирован!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }, { error -> handleError(error, "Что-то пошло не так")
            })
    }

    private fun handleError(throwable: Throwable, string: String) {
        if (throwable is retrofit2.HttpException) {
            val statusCode = throwable.code()

            val errorJsonString = throwable.response().errorBody()?.string()

            val message = JsonParser().parse(errorJsonString).asJsonObject["message"].asString
            val error = JsonParser().parse(errorJsonString).asJsonObject["error"].asString
            val description = JsonParser().parse(errorJsonString).asJsonObject["description_ad"].asString

            if (statusCode == 400) {
                Toast.makeText(this, "Неверный логин и пароль", Toast.LENGTH_SHORT).show()
            }

            if (statusCode == 500) {
                    Toast.makeText(this, "Пользователь уже существует", Toast.LENGTH_SHORT).show()
            }

        } else if (throwable is SocketTimeoutException) {
            Toast.makeText(this, "Нет соединения с сервером", Toast.LENGTH_SHORT).show()
        } else if (throwable is IOException) {
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
        }
    }
}