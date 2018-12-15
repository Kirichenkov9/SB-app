package com.example.anton.sb.ui.activities.adActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import android.widget.ProgressBar
import com.example.anton.sb.R
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.service.getUserAd
import com.example.anton.sb.ui.adapters.MainAdapter
import kotlinx.android.synthetic.main.activity_user_ad.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

/**
 * A screen user ads
 *
 * @author Anton Kirichenkov
 */
class UserAdActivity : AppCompatActivity() {

    /**
     * @property userId
     * @property preAdId
     */

    /**
     * user id
     */
    private var userId: Long = 0

    /**
     * previous ad id
     */
    private var preAdId: Long = 0

    /**
     * @suppress
     */
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
            list = getUserAd(userId, this@UserAdActivity)
            uiThread {
                progressBar_user_ad.visibility = ProgressBar.INVISIBLE
                recyclerView.adapter = MainAdapter(list,
                    object : MainAdapter.OnItemClickListener {
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

    /**
     * @suppress
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        startActivity<AdViewActivity>("preAdId" to preAdId)
        return true
    }
}
