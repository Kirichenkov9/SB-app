package com.example.anton.sb.ui.activities.UserActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.AddadActivity
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_settings.*
import kotlinx.android.synthetic.main.app_bar_other.*
import org.jetbrains.anko.find
import java.io.IOException
import java.net.SocketTimeoutException

class UserSettingsActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null

    private val key_token = "token"
    private val name: String = "first_name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        setSupportActionBar(toolbar_settings)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_settings, toolbar_settings
            ,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout_settings.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_settings.setNavigationItemSelectedListener(this)


        var header = find<NavigationView>(R.id.nav_view_settings).getHeaderView(0)

        var name_user = header.find<TextView>(R.id.user_first_name)
        var user_email = header.find<TextView>(R.id.mail)

        name_user.text = read(name)
        user_email.text = read(mail)

        val first_name = find<TextView>(R.id.first_user_name)
        val last_name = find<TextView>(R.id.last_user_name)
        val email = find<TextView>(R.id.user_email)
        val telephone = find<TextView>(R.id.user_phone_number)
        val about = find<TextView>(R.id.user_about)

        userData(first_name, last_name, email, telephone, about)

        first_name.setOnClickListener {
            val intent = Intent(this, ChangeUserActivity::class.java)
            startActivity(intent)
        }

        last_name.setOnClickListener {
            val intent = Intent(this, ChangeUserActivity::class.java)
            startActivity(intent)
        }

        telephone.setOnClickListener {
            val intent = Intent(this, ChangeUserActivity::class.java)
            startActivity(intent)
        }

        about.setOnClickListener {
            val intent = Intent(this, ChangeUserActivity::class.java)
            startActivity(intent)
        }

        exit_account.setOnClickListener {
            logout()
        }

        delete_account.setOnClickListener {
            delete()
        }

    }

    override fun onBackPressed() {
        if (drawer_layout_settings.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_settings.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.my_ads -> {
                val intent = Intent(this, UserAdActivity::class.java)
                startActivity(intent)
            }
            R.id.search -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.add_ad -> {
                val intent = Intent(this, AddadActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout_settings.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {

        token = read(key_token)

        val apiService: ApiService = ApiService.create()

        apiService.logout_user(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")
                // Toast.makeText(this, "Вы успешно вышли из аккаунта", Toast.LENGTH_SHORT).show()
            }, { error ->
                handleError(error, "Вы успешно вышли из аккаунта")
            })
    }

    private fun delete() {

        token = read(key_token)

        val apiService: ApiService = ApiService.create()

        apiService.delete_user(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")
            }, { error ->
                handleError(error, "Аккаунт удален")
            })
    }

    private fun userData(
        first_name: TextView,
        last_name: TextView,
        email: TextView,
        telephone: TextView,
        about: TextView
    ) {

        token = read(key_token)

        val apiService: ApiService = ApiService.create()

        apiService.get_user_data(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")

                first_name.text = result.first_name
                last_name.text = result.last_name
                email.text = result.email
                telephone.text = result.tel_number
                about.text = result.about

            }, { error ->
                handleError(error, "")
            })
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

    private fun read(key: String): String? {
        var string: String? = null
        var read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }

    private fun handleError(throwable: Throwable, string: String) {
        if (throwable is retrofit2.HttpException) {
            val httpException = throwable
            val statusCode = httpException.code()

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
            removeToken()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, string, Toast.LENGTH_SHORT).show()

        }
    }
}



