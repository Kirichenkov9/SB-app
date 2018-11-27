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

    private var user_id: Long = 0
    private var pre_ad_id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_ad)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        user_id = intent.getLongExtra("user_id", 0)
        pre_ad_id = intent.getLongExtra("pre_ad_id", 0)

        var list: ArrayList<ResultAd> = ArrayList()

        val recyclerView = find<RecyclerView>(R.id.other_user_ad)
        recyclerView.layoutManager = LinearLayoutManager(this)

        doAsync {
            list = getUserAd(user_id)
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
        intent.putExtra("pre_ad_id", pre_ad_id)
        startActivity(intent)
        return true
    }

    private fun startAdViewActivity(ad_id: Long) {
        val intent = Intent(this, AdViewActivity::class.java)
        intent.putExtra("ad_id", ad_id)
        intent.putExtra("pre_ad_id", pre_ad_id)
        intent.putExtra("user_id", user_id)
        startActivity(intent)
    }

    private fun getUserAd(id_user: Long): ArrayList<ResultAd> {

        val ads: ArrayList<ResultAd> = ArrayList()

        val apiService: ApiService = ApiService.create()
        apiService.get_user_ad(id_user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                ads.addAll(result)

            }, { error ->
                //handleError(error, "Что-то пошло не так")
            })
        return ads
    }
}
