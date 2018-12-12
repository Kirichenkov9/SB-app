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
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.extensions.updateDataList
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import com.example.anton.sb.ui.adapters.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * A main screen of app with updating list of ads.
 *
 * @author Anton Kirichenkov
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    /**
     * @property token
     * @property keyToken
     * @property name
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
    private val name: String = "name"

    /**
     * email key for SharedPreference
     */
    private val mail: String = "email"

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        token = readUserData(keyToken, this)

        val list: ArrayList<ResultAd> = ArrayList()

        val recyclerView = find<RecyclerView>(R.id.ad_list)
        val layoutManager = LinearLayoutManager(this)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        nav_view.getHeaderView(0)

        val header = find<NavigationView>(R.id.nav_view).getHeaderView(0)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)
        val navViewHeader = header.find<View>(R.id.nav_view_header)
        val searchButton = find<ImageButton>(R.id.search_button)

        searchButton.setOnClickListener {
            startActivity<SearchActivity>()
        }

        setUsername(nameUser, userEmail)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }
        displayAds(list, recyclerView, layoutManager)

        progressBar_main.visibility = ProgressBar.VISIBLE
    }

    /**
     * Method for display ads with recyclerView Adapter.
     * If have problems with connection server or no ads,
     * then display message "Объявлений нет".
     *
     * @param list [ArrayList] of ads
     * @param recyclerView
     * @param layoutManager
     *
     * @see [updateDataList]
     * @see [MainAdapter]
     */
    private fun displayAds(
        list: ArrayList<ResultAd>,
        recyclerView: RecyclerView,
        layoutManager: LinearLayoutManager
    ) {

        doAsync {
            val dataList = updateDataList(list)
            uiThread {
                progressBar_main.visibility = ProgressBar.INVISIBLE
                if (dataList.isEmpty())
                    toast("Объявлений нет")
                val adapter =
                    MainAdapter(updateDataList(dataList), object : MainAdapter.OnItemClickListener {
                        override fun invoke(ad: ResultAd) {
                            startActivity<AdViewActivity>("adId" to ad.id)
                        }
                    })
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                recyclerView.addOnScrollListener(
                    MainAdapter.OnScrollListener(
                        layoutManager,
                        adapter,
                        dataList
                    )
                )
            }
        }
    }

    /**
     * @suppress
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
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
        drawer_layout.closeDrawer(GravityCompat.START)
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
            nameUser.text = readUserData(name, this)
            userEmail.text = readUserData(mail, this)
        }
    }
}
