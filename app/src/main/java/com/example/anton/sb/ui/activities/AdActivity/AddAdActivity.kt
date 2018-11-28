package com.example.anton.sb.ui.activities.AdActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.UserActivity.UserSettingsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_ad.*
import kotlinx.android.synthetic.main.activity_user_settings.*
import kotlinx.android.synthetic.main.app_bar_other.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class AddAdActivity : AppCompatActivity(),  NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null
    private val keyToken = "token"
    private val username: String = "name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ad)
        setSupportActionBar(toolbar_settings)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_add_ad, toolbar_settings
            ,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout_add_ad.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_add_ad.setNavigationItemSelectedListener(this)

        val header = find<NavigationView>(R.id.nav_view_add_ad).getHeaderView(0)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)

        nameUser.text = read(username)
        userEmail.text = read(mail)

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
            R.id.account -> {
                val intent = Intent(this, UserSettingsActivity::class.java)
                startActivity(intent)
            }
            R.id.search -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.my_ads -> {
                val intent = Intent(this, MyAdActivity::class.java)
                startActivity(intent)
            }
        }
        drawer_layout_add_ad.closeDrawer(GravityCompat.START)
        return true
    }

    // Check the form field for validity
    private fun attemptForm() {
        // Reset errors.
        name.error = null
        about.error = null
        price.error = null
        city.error = null

        // Store values
        val nameStr = name.text.toString()
        val cityStr = city.text.toString()

        var cancel = false
        var focusView: View? = null

        // Field check
        if (TextUtils.isEmpty(nameStr)) {
            name.error = getString(R.string.error_field_required)
            focusView = name
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
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun addAd() {

        token = read(keyToken)

        val titleStr = name.text.toString()
        val price: Int = price.text.toString().toInt()
        val cityStr = city.text.toString()
        val description: String = about.text.toString()
        val apiService: ApiService = ApiService.create()

        apiService.createAd(token.toString(), titleStr, price, cityStr, description)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                toast("Объявление добавлено!")

            }, { error ->
                error.printStackTrace()
            })
    }

    private fun read(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if(read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }
}

