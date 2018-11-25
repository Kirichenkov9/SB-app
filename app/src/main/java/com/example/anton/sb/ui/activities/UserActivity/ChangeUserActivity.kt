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
import android.widget.Toast
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_user.*
import org.jetbrains.anko.find
import java.io.IOException
import java.net.SocketTimeoutException

class ChangeUserActivity : AppCompatActivity() {


    private val key_token = "token"
    private val name: String = "first_name"
    private val mail: String = "email"

    private var token: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val first_name = find<EditText>(R.id.change_first_name)
        val last_name = find<EditText>(R.id.change_last_name)
        val tel_number = find<EditText>(R.id.change_tel_number)
        val about = find<EditText>(R.id.change_about)

        userData(first_name, last_name, tel_number, about)


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

        apiService.change_user(
            token.toString(), firstname,
            lastname, telephone,
            about
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")

            }, { error ->
                handleError(error, "Вы успешно изменили данные!", firstname)
            })
    }

    private fun userData(first_name: TextView, last_name: TextView, tel_number: TextView, about: TextView) {

        token = readToken()

        val apiService: ApiService = ApiService.create()

        apiService.get_user_data(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                first_name.text = result.first_name
                last_name.text = result.last_name
                tel_number.text = result.tel_number
                about.text = result.about

            }, { error ->
                handleError(error, "Что-то пошло не так", "")
            })
    }

    private fun readToken(): String? {
        var string: String? = null
        var save_token: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (save_token.contains("token")) {
            string = save_token.getString("token", null)
        }

        return string
    }

    private fun removeToken() {
        val save_token: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = save_token.edit()

        editor.remove(key_token)
        editor.remove(name)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }


    private fun handleError(throwable: Throwable, string: String, fname: String) {
        if (throwable is retrofit2.HttpException) {
            val httpException = throwable
            val statusCode = httpException.code()

            if (statusCode == 400) {
                Toast.makeText(this, "Имя не должно быть пустым", Toast.LENGTH_SHORT).show()
            }

            if (statusCode == 401) {
                removeToken()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            if (statusCode == 500) {
                Toast.makeText(this, "Нет соединения с сервером", Toast.LENGTH_SHORT).show()
            }

        } else if (throwable is SocketTimeoutException) {
            Toast.makeText(this, "Нет соединения с сервером", Toast.LENGTH_SHORT).show()
        } else if (throwable is IOException) {

            val save: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
            val editor: SharedPreferences.Editor = save.edit()
            editor.remove(name)
            editor.putString(name, fname)
            editor.apply()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
        }

    }
}

