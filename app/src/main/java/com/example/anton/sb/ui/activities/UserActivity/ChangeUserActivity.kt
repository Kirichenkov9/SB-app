package com.example.anton.sb.ui.activities.UserActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_user.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.io.IOException
import java.net.SocketTimeoutException

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


        change_user.setOnClickListener {
            changeUser()
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

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeData(firstname: String, lastname: String, telephone: String, about: String) {

        token = readToken()

        val apiService: ApiService = ApiService.create()

        apiService.changeUser(
            token.toString(), firstname,
            lastname, telephone,
            about
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")

            }, { error ->
                handleError(error, firstname)
            })
    }

    private fun userData(firstName: TextView, lastName: TextView, telNumber: TextView, about: TextView) {

        token = readToken()

        val apiService: ApiService = ApiService.create()

        apiService.getUserData(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                firstName.text = result.body()!!.first_name
                lastName.text = result.body()!!.last_name
                telNumber.text = result.body()!!.tel_number
                about.text = result.body()!!.about

            }, { error ->
                handleError(error, "")
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

    private fun removeToken() {
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = saveToken.edit()

        editor.remove(keyToken)
        editor.remove(name)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }


    private fun handleError(throwable: Throwable, fname: String) {
        if (throwable is retrofit2.HttpException) {
            val statusCode = throwable.code()

            if (statusCode == 400) {
                toast("Имя не должно быть пустым")
            }

            if (statusCode == 401) {
                removeToken()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            if (statusCode == 500) {
                toast("Нет соединения с сервером")
            }

        } else if (throwable is SocketTimeoutException) {
            toast("Нет соединения с сервером")
        } else if (throwable is IOException) {

            val save: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = save.edit()
            editor.remove(name)
            editor.putString(name, fname)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            toast("Что-то пошло не так...")
        }
    }
}

