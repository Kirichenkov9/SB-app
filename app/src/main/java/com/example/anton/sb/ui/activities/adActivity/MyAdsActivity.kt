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
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import com.example.anton.sb.ui.adapters.MainAdapter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.activity_my_ad.*
import kotlinx.android.synthetic.main.app_bar_my_ads.*
import org.jetbrains.anko.*

/**
 * A screen user ads
 *
 * @author Anton Kirichenkov
 */
class MyAdsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    /**
     * @property token
     * @property keyToken
     * @property name
     * @property mail
     * @property id
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
    private val name: String = "name"

    /**
     * email key for SharedPreference
     */
    private val mail: String = "email"

    /**
     * user id
     */
    private val id: String = "id"

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_NoActionBar)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ad)
        setSupportActionBar(toolbar_my_ads)

        token = readUserData(keyToken)

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

    /**
     * Method for display user ads with recyclerView Adapter.
     *
     * @param recyclerView
     * @param idUser
     *
     * @see [getUserAd]
     * @see [MainAdapter]
     */
    private fun displayAds(recyclerView: RecyclerView, idUser: Long) {

        doAsync {
            val list = getUserAd(idUser)
            uiThread {
                recyclerView.adapter = MainAdapter(list,
                    object : MainAdapter.OnItemClickListener {
                        override fun invoke(ad: ResultAd) {
                            startActivity<MyAdSettingsActivity>("adId" to ad.id)
                        }
                    })
            }
        }
    }

    /**
     * @suppress
     */
    override fun onBackPressed() {
        if (drawer_layout_user_ad.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_user_ad.closeDrawer(GravityCompat.START)
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
        drawer_layout_user_ad.closeDrawer(GravityCompat.START)
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
            nameUser.text = readUserData(mail)
        } else {
            nameUser.text = readUserData(name)
            userEmail.text = readUserData(mail)
        }
    }

    /**
     * Reading information about user by key from SharedPreference.
     *
     * @param key is a key for data from SharedPreference
     *
     * @return [String]
     */
    private fun readUserData(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, "")
        }
        return string
    }

    /**
     * Get user ads. This method use [ApiService.getUserAd] and processing response from server.
     * If response is successful, then display list of ads, else - display error
     * processing by [handleError].
     *
     * @param idUser ad id
     *
     * @see [handleError]
     * @see [ApiService.getUserAd]
     */
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
                    removeData()
                    startActivity<LoginActivity>()
                } else toast(errorStr)
            })
        return ads
    }

    /**
     * Method for remove user data from SharedPreference.
     *
     */
    private fun removeData() {
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = saveToken.edit()

        editor.remove(keyToken)
        editor.remove(name)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }
}
