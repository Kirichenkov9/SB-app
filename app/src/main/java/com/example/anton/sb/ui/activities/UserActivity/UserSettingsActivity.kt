package com.example.anton.sb.ui.activities.UserActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.AdActivity.AddAdActivity
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import com.example.anton.sb.ui.activities.AdActivity.MyAdsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_settings.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.app_bar_other.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class UserSettingsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null

    private val keyToken = "token"
    private val name: String = "name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        setSupportActionBar(toolbar_settings)

        token = read(keyToken)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_settings, toolbar_settings,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout_settings.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_settings.setNavigationItemSelectedListener(this)
        val header = find<NavigationView>(R.id.nav_view_settings).getHeaderView(0)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }

        nameUser.text = read(name)
        userEmail.text = read(mail)

        val firstName = find<TextView>(R.id.first_user_name)
        val lastName = find<TextView>(R.id.last_user_name)
        val email = find<TextView>(R.id.user_email)
        val telephone = find<TextView>(R.id.user_phone_number)
        val about = find<TextView>(R.id.user_about)

        userData(firstName, lastName, email, telephone, about)
        progressBar_user_settings.visibility = ProgressBar.VISIBLE

        firstName.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        lastName.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        telephone.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        about.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        exit_account.setOnClickListener {
            logout()
            progressBar_user_settings.visibility = ProgressBar.VISIBLE
        }

        delete_account.setOnClickListener {
            delete()
            progressBar_user_settings.visibility = ProgressBar.VISIBLE
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
            R.id.add_ad -> {
                startActivity<AddAdActivity>()
            }
            R.id.account -> {
                startActivity<UserSettingsActivity>()
            }
            R.id.search -> {
                startActivity<MainActivity>()
            }
            R.id.my_ads -> {
                startActivity<MyAdsActivity>()
            }
            R.id.about_app -> {
                startActivity<AboutApp>()
            }
        }
        drawer_layout_settings.closeDrawer(GravityCompat.START)
        return true
    }

    private fun logout() {

        val apiService: ApiService = ApiService.create()

        apiService.logoutUser(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE
                toast("Вы вышли из аккаунта")
                removeToken()
                this.finish()
                startActivity<MainActivity>()
            }, { error ->
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
    }

    private fun delete() {

        val apiService: ApiService = ApiService.create()

        apiService.deleteUser(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE
                toast("Аккаунт удален")
                removeToken()
                this.finish()
                startActivity<MainActivity>()
            },
                { error ->
                    progressBar_user_settings.visibility = ProgressBar.INVISIBLE
                    val errorStr = handleError(error)
                    if (errorStr == "empty body") {
                        toast("Аккаунт удален")
                        removeToken()
                        this.finish()
                        startActivity<MainActivity>()
                    } else if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                        removeToken()
                        startActivity<LoginActivity>()
                    } else
                        toast(errorStr)
                })
    }

    private fun userData(
        firstName: TextView,
        lastName: TextView,
        email: TextView,
        telephone: TextView,
        about: TextView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.getUserData(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE

                firstName.text = result.first_name
                lastName.text = result.last_name
                email.text = result.email
                telephone.text = result.tel_number
                about.text = result.about
            }, { error ->
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "empty body") {
                    toast("Объявление удалено")

                    startActivity<MyAdsActivity>()
                } else if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeToken()
                    startActivity<LoginActivity>()
                } else
                    toast(errorStr)
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
}
