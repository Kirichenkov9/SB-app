package com.example.anton.sb.ui.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.anton.sb.R
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

class AboutApp : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null

    private val keyToken = "token"
    private val username: String = "name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app)
        setSupportActionBar(toolbar_settings)

        token = read(keyToken)

        val header = find<NavigationView>(R.id.nav_view_about_app).getHeaderView(0)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)

        nameUser.text = read(username)
        userEmail.text = read(mail)

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
            browse("https://protected-bayou-62297.herokuapp.com/ads")
        }
    }

    override fun onBackPressed() {
        if (drawer_layout_about_app.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_about_app.closeDrawer(GravityCompat.START)
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
        drawer_layout_about_app.closeDrawer(GravityCompat.START)
        return true
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
