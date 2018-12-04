package com.example.anton.sb.ui.activities.UserActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_user.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class ChangeUserActivity : AppCompatActivity() {


    private val keyToken = "token"
    private val name: String = "name"
    private val mail: String = "email"

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val firstName = find<EditText>(R.id.change_first_name)
        val lastName = find<EditText>(R.id.change_last_name)
        val telNumber = find<EditText>(R.id.change_tel_number)
        val about = find<EditText>(R.id.change_about)

        userData(firstName, lastName, telNumber, about)
        progressBar_user_change.visibility = ProgressBar.VISIBLE

        change_user.setOnClickListener {
            changeUser()
            progressBar_user_change.visibility = ProgressBar.VISIBLE
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, UserSettingsActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun changeUser() {
        var cancel = false
        var focusView: View? = null

        change_first_name.error = null
        change_last_name.error = null

        val firstnameStr: String? = change_first_name.text.toString()
        val lastnameStr: String? = change_last_name.text.toString()
        val telephoneStr: String? = change_tel_number.text.toString()
        val aboutStr: String? = change_about.text.toString()

        if (TextUtils.isEmpty(firstnameStr)) {
            change_first_name.error = getString(R.string.error_field_required)
            focusView = change_first_name
            cancel = true
        }

        if (TextUtils.isEmpty(lastnameStr)) {
            change_last_name.error = getString(R.string.error_field_required)
            focusView = change_first_name
            cancel = true
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner and to
            // perform the user login attempt.
            changeData(
                firstnameStr.toString(), lastnameStr.toString(),
                telephoneStr.toString(), aboutStr.toString()
            )
        }
    }

    private fun changeData(firstName: String, lastName: String, telephone: String, about: String) {

        token = readToken()

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
                toast("Данные изменены")
                this.finish()

                val intent = Intent(this, UserSettingsActivity::class.java)
                startActivity(intent)
            },
                { error ->
                    progressBar_user_change.visibility = ProgressBar.INVISIBLE
                    val errorString = handleError(error)
                    if (errorString == "empty body") {
                        toast("Данные изменены")
                        this.finish()

                        val intent = Intent(this, UserSettingsActivity::class.java)
                        startActivity(intent)
                    } else
                        toast(errorString)
                })
    }

    private fun userData(firstName: TextView, lastName: TextView, telNumber: TextView, about: TextView) {

        token = readToken()

        val apiService: ApiService = ApiService.create()

        apiService.getUserData(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_user_change.visibility = ProgressBar.INVISIBLE
                firstName.text = result.first_name
                lastName.text = result.last_name
                telNumber.text = result.tel_number
                about.text = result.about

            }, { error ->
                progressBar_user_change.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
    }

    private fun readToken(): String? {
        var string: String? = null
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (saveToken.contains("token")) {
            string = saveToken.getString("token", null)
        }

        return string
    }
}

