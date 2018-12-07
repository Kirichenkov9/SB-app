package com.example.anton.sb.ui.activities.adActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import com.example.anton.sb.ui.adapters.SearchAdapter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.activity_my_ad.* // ktlint-disable no-wildcard-imports
import kotlinx.android.synthetic.main.app_bar_my_ads.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.* // ktlint-disable no-wildcard-imports

class MyAdsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null
    private val keyToken = "token"
    private val name: String = "name"
    private val mail: String = "email"
    private val id: String = "id"

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ad)
        setSupportActionBar(toolbar_my_ads)

        token = read(keyToken)

        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val idUser: Long = read.getLong(id, 0)

        val recyclerView = find<RecyclerView>(R.id.user_ad)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val header = find<NavigationView>(R.id.nav_view_user_ad).getHeaderView(0)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        setUsername(nameUser, userEmail)

        displayAds(recyclerView, idUser)

        progressBar_my_ad.visibility = ProgressBar.VISIBLE

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_user_ad, toolbar_my_ads,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout_user_ad.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_user_ad.setNavigationItemSelectedListener(this)

        nav_view_user_ad.getHeaderView(0)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }
    }

    private fun displayAds(recyclerView: RecyclerView, idUser: Long) {

        doAsync {
            val list = getUserAd(idUser)
            uiThread {
                recyclerView.adapter = SearchAdapter(list,
                    object : SearchAdapter.OnItemClickListener {
                        override fun invoke(ad: ResultAd) {
                            startActivity<MyAdSettingsActivity>("adId" to ad.id)
                        }
                    })
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
        drawer_layout_user_ad.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setUsername(name_user: TextView, userEmail: TextView) {
        if (token.isNullOrEmpty()) {
            userEmail.text = getString(R.string.Enter_registration)
            name_user.text = read(mail)
        } else {
            name_user.text = read(name)
            userEmail.text = read(mail)
        }
    }

    private fun read(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, "")
        }
        return string
    }

    private fun getUserAd(idUser: Long): ArrayList<ResultAd> {

        val ads: ArrayList<ResultAd> = ArrayList()

        val apiService: ApiService = ApiService.create()
        apiService.getUserAd(idUser)
            .observeOn(mainThread())
            .subscribe({ result ->
                progressBar_my_ad.visibility = ProgressBar.INVISIBLE
                ads.addAll(result)

                if (result.isEmpty())
                    toast("Объявлений нет")
            }, { error ->
                progressBar_my_ad.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeToken()
                    startActivity<LoginActivity>()
                } else toast(errorStr)
            })
        return ads
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
}
