package com.example.anton.sb.ui.activities.adActivity

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
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_ad.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.app_bar_other.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * A screen of adding ad
 *
 * @author Anton Kirichenkov
 */
class AddAdActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    /**
     * @property token
     * @property keyToken
     * @property username
     * @property mail
     */

    /**
     * saved session_id
     */
    private var token: String? = null

    /**
     * token key for SharedPreference
     */
    private val keyToken = "token"

    /**
     * username key for SharedPreference
     */
    private val username: String = "name"

    /**
     * email key for SharedPreference
     */
    private val mail: String = "email"

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ad)
        setSupportActionBar(toolbar_settings)

        token = readUserData(keyToken)

        val header = find<NavigationView>(R.id.nav_view_add_ad).getHeaderView(0)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)

        nameUser.text = readUserData(username)
        userEmail.text = readUserData(mail)

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

    /**
     * @suppress
     */
    override fun onBackPressed() {
        if (drawer_layout_add_ad.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_add_ad.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * @suppress
     */
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

    /**
     * Attempts to adding ad specified by the adding form.
     * If there are form errors (invalid title, missing fields, etc.), the
     * errors are presented and no actual adding attempt is made.
     */
    private fun attemptForm() {
        // Reset errors.
        name.error = null
        city.error = null
        about.error = null

        // Store values
        val nameStr = name.text.toString()
        val cityStr = city.text.toString()
        val aboutStr = about.text.toString()
        val priceAd = price.text.toString().toIntOrNull()

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
            addAd(
                nameStr,
                cityStr,
                aboutStr,
                priceAd,
                token.toString()
                )
            progressBar_add_ad.visibility = ProgressBar.VISIBLE
        }
    }

    /**
     * Creating ad. This method use [ApiService.createAd] and processing response from server
     * and display it. If response isn't successful, then caused [handleError] for process error.
     *
     * @param title title of ad
     * @param city city of ad
     * @param description description of ad
     * @param priceAd price of ad
     * @param token user-owner session_id
     *
     * @see [ApiService.createAd]
     * @see [handleError]
     */
    private fun addAd(
        title: String,
        city: String,
        description: String,
        priceAd: Int?,
        token: String
    ) {
        val apiService: ApiService = ApiService.create()

        apiService.createAd(token, title, priceAd, city, description)
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
                    removeUserData()
                    startActivity<LoginActivity>()
                } else
                    toast(errorStr)
            })
    }

    /**
     * Reading information about user by key from SharedPreference.
     *
     * @param key is a key for data from SharedPreference
     */
    private fun readUserData(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }

    /**
     * Removing information about user by key from SharedPreference.
     */
    private fun removeUserData() {
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = saveToken.edit()

        editor.remove(keyToken)
        editor.remove(username)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }
}
