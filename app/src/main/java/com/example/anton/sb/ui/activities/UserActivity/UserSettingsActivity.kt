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
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.AddAdActivity
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import com.example.anton.sb.ui.activities.AdActivity.MyAdActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_settings.*
import kotlinx.android.synthetic.main.app_bar_other.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import java.io.IOException
import java.net.SocketTimeoutException

class UserSettingsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null

    private val keyToken = "token"
    private val name: String = "name"
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


        val header = find<NavigationView>(R.id.nav_view_settings).getHeaderView(0)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)

        nameUser.text = read(name)
        userEmail.text = read(mail)

        val firstName = find<TextView>(R.id.first_user_name)
        val lastName = find<TextView>(R.id.last_user_name)
        val email = find<TextView>(R.id.user_email)
        val telephone = find<TextView>(R.id.user_phone_number)
        val about = find<TextView>(R.id.user_about)

        userData(firstName, lastName, email, telephone, about)

        firstName.setOnClickListener {
            val intent = Intent(this, ChangeUserActivity::class.java)
            startActivity(intent)
        }

        lastName.setOnClickListener {
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
                val intent = Intent(this, MyAdActivity::class.java)
                startActivity(intent)
            }
            R.id.search -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.add_ad -> {
                val intent = Intent(this, AddAdActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout_settings.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {

        token = read(keyToken)

        val apiService: ApiService = ApiService.create()

        apiService.logoutUser(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({},
                { error ->
                    handleError(error, "Вы успешно вышли из аккаунта")
                })
    }

    private fun delete() {

        token = read(keyToken)

        val apiService: ApiService = ApiService.create()

        apiService.deleteUser(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")
            }, { error ->
                handleError(error, "Аккаунт удален")
            })
    }

    private fun userData(
        firstName: TextView,
        lastName: TextView,
        email: TextView,
        telephone: TextView,
        about: TextView
    ) {

        token = read(keyToken)

        val apiService: ApiService = ApiService.create()

        apiService.getUserData(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                Log.d("Result", "Success.")

                firstName.text = result.body()!!.first_name
                lastName.text = result.body()!!.last_name
                email.text = result.body()!!.email
                telephone.text = result.body()!!.tel_number
                about.text = result.body()!!.about

            }, { error ->
                handleError(error, "")
            })
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

    private fun read(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }

    private fun handleError(throwable: Throwable, string: String) {
        if (throwable is retrofit2.HttpException) {
            val statusCode = throwable.code()

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
            removeToken()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            toast(string)

        }
    }
}



