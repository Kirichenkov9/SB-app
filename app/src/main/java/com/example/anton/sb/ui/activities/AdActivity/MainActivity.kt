package com.example.anton.sb.ui.activities.AdActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.updateDataList
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.activities.UserActivity.LoginActivity
import com.example.anton.sb.ui.activities.UserActivity.UserSettingsActivity
import com.example.anton.sb.ui.adapters.MainAdapter
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var token: String? = null
    private val keyToken = "token"
    private val name: String = "name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        token = read(keyToken)

        var dataList: ArrayList<ResultAd>
        val list: ArrayList<ResultAd> = ArrayList()

        val recyclerView = find<RecyclerView>(R.id.ad_list)
        val layoutManager = LinearLayoutManager(this)
        var adapter: MainAdapter

        doAsync {
          dataList = updateDataList(list)
            uiThread {
                adapter = MainAdapter(updateDataList(dataList), object : MainAdapter.OnItemClickListener {
                    override fun invoke(ad: ResultAd) {
                        startAdViewActivity(ad.id)
                    }
                })
                recyclerView.layoutManager = layoutManager
                recyclerView.adapter = adapter
                recyclerView.addOnScrollListener(MainAdapter.OnScrollListener(layoutManager, adapter, dataList))
            }
        }

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
        val floatingActionButton = find<FloatingActionButton>(R.id.floatingActionButton)

        floatingActionButton.setOnClickListener {

            if (token.isNullOrEmpty()) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, AddAdActivity::class.java)
                startActivity(intent)
            }
        }

        setUsername(nameUser, userEmail)

        navViewHeader.setOnClickListener {
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
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
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
                    val intent = Intent(this, MyAdActivity::class.java)
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
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun startAdViewActivity(id: Long) {
        val intent = Intent(this, AdViewActivity::class.java)
        intent.putExtra("adId", id)
        startActivity(intent)
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
            string = read.getString(key, " ")
        }
        return string
    }

    private fun getAd(ads: ArrayList<ResultAd>, limit: Int, offset: Int) {

        val apiService: ApiService = ApiService.create()

        apiService.getAds(offset, limit)
            .observeOn(mainThread())
            .subscribe({ result ->

                ads.addAll(result.body()!!)

            }, { error ->
                //handleError(error, "Что-то пошло не так")
            })
    }
}
