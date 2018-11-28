package com.example.anton.sb.ui.activities.AdActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.adapters.MainAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class UserAdActivity : AppCompatActivity() {

    private var userId: Long = 0
    private var preAdId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ad)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        userId = intent.getLongExtra("userId", 0)
        preAdId = intent.getLongExtra("preAdId", 0)

        var list: ArrayList<ResultAd> = ArrayList()

        val recyclerView = find<RecyclerView>(R.id.other_user_ad)
        recyclerView.layoutManager = LinearLayoutManager(this)

        doAsync {
            list = getUserAd(userId)
            uiThread {
                recyclerView.adapter = MainAdapter(list,
                    object : MainAdapter.OnItemClickListener {
                        override fun invoke(ad: ResultAd) {
                            startAdViewActivity(ad.id)
                        }
                    })
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, AdViewActivity::class.java)
        intent.putExtra("preAdId", preAdId)
        startActivity(intent)
        return true
    }

    private fun startAdViewActivity(adId: Long) {
        val intent = Intent(this, AdViewActivity::class.java)
        intent.putExtra("adId", adId)
        intent.putExtra("preAdId", preAdId)
        intent.putExtra("userId", userId)
        startActivity(intent)
    }

    private fun getUserAd(id_user: Long): ArrayList<ResultAd> {

        val ads: ArrayList<ResultAd> = ArrayList()

        val apiService: ApiService = ApiService.create()
        apiService.getUserAd(id_user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                ads.addAll(result.body()!!)

            }, { error ->
                //handleError(error, "Что-то пошло не так")
            })
        return ads
    }
}
