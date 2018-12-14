package com.example.anton.sb.ui.activities

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.ui.activities.adActivity.AddAdActivity
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdsActivity
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import kotlinx.android.synthetic.main.activity_about_app.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.app_bar_other.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * A screen information about app
 *
 * @author Anton Kirichenkov
 */
class AboutApp : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        setContentView(R.layout.activity_about_app)
        setSupportActionBar(toolbar_settings)

        token = readUserData(keyToken, this)

        val header = find<NavigationView>(R.id.nav_view_about_app).getHeaderView(0)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)

        setUsername(nameUser, userEmail)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_about_app, toolbar_settings,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout_about_app.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_about_app.setNavigationItemSelectedListener(this)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }
        val web = find<TextView>(R.id.web)
        val email = find<TextView>(R.id.author_email)

        email.setOnClickListener {
            email(email.text.toString())
        }

        web.setOnClickListener {
            browse("https://search-build.herokuapp.com/ads")
        }
    }

    /**
     * @suppress
     */
    override fun onBackPressed() {
        if (drawer_layout_about_app.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_about_app.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * @suppress
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                startActivity<MainActivity>()
            }
            R.id.account -> {
                if (token.isNullOrEmpty()) {
                    startActivity<LoginActivity>()
                } else {
                    startActivity<UserSettingsActivity>()
                }
            }
            R.id.my_ads -> {
                if (token.isNullOrEmpty()) {
                    startActivity<LoginActivity>()
                } else {
                    startActivity<MyAdsActivity>()
                }
            }
            R.id.add_ad -> {
                if (token.isNullOrEmpty()) {
                    startActivity<LoginActivity>()
                } else {
                    startActivity<AddAdActivity>()
                }
            }
            R.id.about_app -> {
                startActivity<AboutApp>()
            }
        }
        drawer_layout_about_app.closeDrawer(GravityCompat.START)
        return true
    }
    /**
     * Method for display full user name and email from SharedPreference on nav_header.
     * If user doesn't login, then display "Войти / зарегистрироваться"
     *
     * @param nameUser user name
     * @param userEmail user email
     */
    private fun setUsername(nameUser: TextView, userEmail: TextView) {
        if (token.isNullOrEmpty()) {
            userEmail.text = getString(R.string.Enter_registration)
            nameUser.text = readUserData(mail, this)
        } else {
            nameUser.text = readUserData(username, this)
            userEmail.text = readUserData(mail, this)
        }
    }
}
