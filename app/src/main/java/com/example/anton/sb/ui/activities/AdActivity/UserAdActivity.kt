package com.example.anton.sb.ui.activities.AdActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.adapters.SearchAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user_ad.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
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

        var list: ArrayList<ResultAd>

        val recyclerView = find<RecyclerView>(R.id.other_user_ad)
        recyclerView.layoutManager = LinearLayoutManager(this)

        doAsync {
            list = getUserAd(userId)
            uiThread {
                recyclerView.adapter = SearchAdapter(list,
                    object : SearchAdapter.OnItemClickListener {
                        override fun invoke(ad: ResultAd) {
                            startActivity<AdViewActivity>(
                                "preAdId" to preAdId,
                                "adId" to ad.id,
                                "userId" to userId
                            )
                        }
                    })
            }
        }
        progressBar_user_ad.visibility = ProgressBar.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        startActivity<AdViewActivity>("preAdId" to preAdId)
        return true
    }

    private fun getUserAd(id_user: Long): ArrayList<ResultAd> {

        val ads: ArrayList<ResultAd> = ArrayList()

        val apiService: ApiService = ApiService.create()
        apiService.getUserAd(id_user)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                progressBar_user_ad.visibility = ProgressBar.INVISIBLE
                ads.addAll(result)
            }, { error ->
                progressBar_user_ad.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
        return ads
    }
}
