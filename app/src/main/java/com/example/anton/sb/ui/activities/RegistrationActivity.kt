package com.example.anton.sb.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)


        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        val button_to_registr = findViewById<Button>(R.id.button_registration)

        button_to_registr.setOnClickListener { attemptForm() }

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
        val first_name_registrationStr = first_name_registration.text.toString()
        val last_name_registrationStr = last_name_registration.text.toString()
        val email_registrationStr = email_registration.text.toString()
        val phone_numberStr = phone_number_registration.text.toString()
        val password_registrationStr = password_registration.text.toString()
        val repeat_pass_registrationStr = repeat_password_registration.text.toString()

        var cancel = false
        var focusView: View? = null

        // Field check
        if (TextUtils.isEmpty(first_name_registrationStr)) {
            first_name_registration.error = getString(R.string.error_field_required)
            focusView = first_name_registration
            cancel = true
        }

        if (TextUtils.isEmpty(last_name_registrationStr)) {
            last_name_registration.error = getString(R.string.error_field_required)
            focusView = last_name_registration
            cancel = true
        }

        if (TextUtils.isEmpty(email_registrationStr)) {
            email_registration.error = getString(R.string.error_field_required)
            focusView = email_registration
            cancel = true
        } else if (!isEmailValid(email_registrationStr)) {
            email_registration.error = getString(R.string.error_invalid_email)
            focusView = email_registration
            cancel = true
        }

        if (TextUtils.isEmpty(phone_numberStr)) {
            phone_number_registration.error = getString(R.string.error_field_required)
            focusView = phone_number_registration
            cancel = true
        }

        if (TextUtils.isEmpty(password_registrationStr)) {
            password_registration.error = getString(R.string.error_field_required)
            focusView = password_registration
            cancel = true
        } else if (!isPasswordValid(password_registrationStr)) {
            password_registration.error = getString(R.string.error_invalid_password)
            focusView = password_registration
            cancel = true
        }

        if (TextUtils.isEmpty(repeat_pass_registrationStr)) {
            repeat_password_registration.error = getString(R.string.error_field_required)
            focusView = repeat_password_registration
            cancel = true
        } else if (password_registrationStr != repeat_pass_registrationStr) {
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun isEmailValid(email: String): Boolean {
        //Check entered email
        return email.contains("@")
    }

    private fun isPasswordValid(password: String): Boolean {
        //Check entered password
        return password.length > 6
    }

    private  fun adUser() {

        val first_nameStr = first_name_registration.text.toString()
        val last_nameStr = last_name_registration.text.toString()
        val emailStr = email_registration.text.toString()
        val tel_numberStr = phone_number_registration.text.toString()
        val passwordStr = password_registration.text.toString()
        val aboutStr = about_registration.text.toString()

        val apiService: ApiService = ApiService.create()

        apiService.create_user(first_nameStr, last_nameStr, emailStr, passwordStr, tel_numberStr, aboutStr)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                Log.d("Result", "Success")
                showToast(this)
            }, { error ->
                error.printStackTrace()
            })
    }

    private fun showToast(view: RegistrationActivity) {
        // Create and show message
        val toast = Toast.makeText(
            applicationContext,
            getString(R.string.user_was_aded),
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.BOTTOM, 0, 20)
        toast.show()
    }
}