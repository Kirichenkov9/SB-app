package com.example.anton.sb.ui.activities.UserActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.AddadActivity
import com.example.anton.sb.ui.activities.AdActivity.MainActivity
import com.example.anton.sb.ui.adapters.MyAdapter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_ad.*
import kotlinx.android.synthetic.main.app_bar_myads.*
import org.jetbrains.anko.find

class UserAdActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private var token: String? = null
    private val key_token = "token"
    private val name: String = "first_name"
    private val mail: String = "email"
    private val id: String = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ad)
        setSupportActionBar(toolbar_my_ads)

        token = read(key_token)

        getUserAd()

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_user_ad, toolbar_my_ads,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout_user_ad.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_user_ad.setNavigationItemSelectedListener(this)

        nav_view_user_ad.getHeaderView(0)

        val header = find<NavigationView>(R.id.nav_view_user_ad).getHeaderView(0)

        val name_user = header.find<TextView>(R.id.user_first_name)
        val user_email = header.find<TextView>(R.id.mail)

        setUsername(name_user, user_email)

        user_email.setOnClickListener {
            if (token.isNullOrEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, UserSettingsActivity::class.java)
                startActivity(intent)
            }

        }
    }

    override fun onBackPressed() {
        if (drawer_layout_user_ad.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_user_ad.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.account -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, UserSettingsActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.search -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
            }
            R.id.add_ad -> {
                if (token.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, AddadActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        drawer_layout_user_ad.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUsername(name_user: TextView, user_email: TextView) {
        if (token.isNullOrEmpty()) {
            user_email.text = getString(R.string.Enter_registration)
            name_user.text = read(mail)
        } else {
            name_user.text = read(name)
            user_email.text = read(mail)
        }

    }

    private fun read(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }

    private fun getUserAd() {
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val id_user: Long = read.getLong(id, 0)

        val apiService: ApiService = ApiService.create()
        apiService.get_user_ad(id_user)
            .observeOn(mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                //Toast.makeText(this, "$result", Toast.LENGTH_SHORT).show()

                val recyclerView = find<RecyclerView>(R.id.user_ad)
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = MyAdapter(result)

            }, { error ->
                //handleError(error, "Что-то пошло не так")
            })
    }
}
