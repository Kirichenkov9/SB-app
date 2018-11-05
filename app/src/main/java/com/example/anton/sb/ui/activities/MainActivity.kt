package com.example.anton.sb.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.example.anton.sb.R
import com.example.anton.sb.data.Request
import com.example.anton.sb.ui.adapters.AddListAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.longToast
import org.jetbrains.anko.uiThread



class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val items = listOf(
        ""
    )

    private val flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val AddList = find<RecyclerView>(R.id.add_list)
        AddList.layoutManager = LinearLayoutManager(this)
        AddList.adapter = AddListAdapter(items)

        val url = "http://api.openweathermap.org/data/2.5/forecast/daily?" +
                "APPID=15646a06818f61f7b8d7823ca833e1ce&q=94043&mode=json&units=metric&cnt=7"

        doAsync {
            Request(url).run()
            uiThread { longToast("Request performed") }
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
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
            R.id.account -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.my_ads -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.add_ad -> {
                if (flag == true) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                else {
                    val intent = Intent(this, AddadActivity::class.java)
                    startActivity(intent)
                }
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
