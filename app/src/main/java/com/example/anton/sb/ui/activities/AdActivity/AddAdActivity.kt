package com.example.anton.sb.ui.activities.AdActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.UserActivity.LoginActivity
import com.example.anton.sb.ui.activities.UserActivity.UserSettingsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_ad.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.activity_user_settings.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.app_bar_other.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class AddAdActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null

    private val keyToken = "token"
    private val username: String = "name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ad)
        setSupportActionBar(toolbar_settings)

        token = read(keyToken)

        val header = find<NavigationView>(R.id.nav_view_add_ad).getHeaderView(0)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)

        nameUser.text = read(username)
        userEmail.text = read(mail)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_add_ad, toolbar_settings,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout_add_ad.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_add_ad.setNavigationItemSelectedListener(this)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }

        // Adding photo to ad after pressing button
        button_ad_photo.setOnClickListener { }

        // Adding ad after pressing button
        button_ad_add.setOnClickListener { attemptForm() }
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
        drawer_layout_add_ad.closeDrawer(GravityCompat.START)
        return true
    }

    // Check the form field for validity
    private fun attemptForm() {
        // Reset errors.
        name.error = null
        city.error = null
        about.error = null

        // Store values
        val nameStr = name.text.toString()
        val cityStr = city.text.toString()
        val aboutStr = about.text.toString()

        var cancel = false
        var focusView: View? = null

        // Field check
        if (TextUtils.isEmpty(nameStr)) {
            name.error = getString(R.string.error_field_required)
            focusView = name
            cancel = true
        }

        if (TextUtils.isEmpty(aboutStr)) {
            about.error = getString(R.string.error_field_required)
            focusView = about
            cancel = true
        }

        if (TextUtils.isEmpty(cityStr)) {
            city.error = getString(R.string.error_field_required)
            focusView = city
            cancel = true
        }

        if (cancel) {
            // There was an error
            focusView?.requestFocus()
        } else {
            // Ad add
            addAd()
            progressBar_add_ad.visibility = ProgressBar.VISIBLE
        }
    }

    private fun addAd() {

        token = read(keyToken)

        var priceAd: Int
        if (price.text.isNullOrEmpty())
            priceAd = 0
        else
            priceAd = price.text.toString().toInt()

        val titleStr = name.text.toString()
        val cityStr = city.text.toString()
        val description: String = about.text.toString()
        val apiService: ApiService = ApiService.create()

        apiService.createAd(token.toString(), titleStr, priceAd, cityStr, description)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_add_ad.visibility = ProgressBar.INVISIBLE
                toast("Объявление добавлено")

                startActivity<MyAdsActivity>()
            }, { error ->
                progressBar_add_ad.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeToken()
                    startActivity<LoginActivity>()
                } else
                    toast("$errorStr")
            })
    }

    private fun read(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }

    private fun removeToken() {
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = saveToken.edit()

        editor.remove(keyToken)
        editor.remove(username)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }
}
