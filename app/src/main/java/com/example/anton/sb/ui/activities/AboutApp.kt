package com.example.anton.sb.ui.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.ui.activities.AdActivity.AddAdActivity
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import com.example.anton.sb.ui.activities.AdActivity.MyAdsActivity
import com.example.anton.sb.ui.activities.UserActivity.LoginActivity
import com.example.anton.sb.ui.activities.UserActivity.UserSettingsActivity
import kotlinx.android.synthetic.main.activity_about_app.*
import kotlinx.android.synthetic.main.app_bar_other.*
import org.jetbrains.anko.find

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
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, UserSettingsActivity::class.java)
                startActivity(intent)
            }
        }
        val web = find<TextView>(R.id.web)

        web.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://protected-bayou-62297.herokuapp.com/ads"))
            startActivity(browserIntent)
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
            R.id.search -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.account -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, UserSettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.my_ads -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, MyAdsActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.add_ad -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, AddAdActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.about_app -> {
                val intent = Intent(this, AboutApp::class.java)
                startActivity(intent)
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
