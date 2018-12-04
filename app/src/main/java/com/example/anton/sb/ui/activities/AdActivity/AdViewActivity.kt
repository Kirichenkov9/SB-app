package com.example.anton.sb.ui.activities.AdActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.activities.UserActivity.UserViewActivity
import kotlinx.android.synthetic.main.activity_ad_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class AdViewActivity : AppCompatActivity() {

    private var userId: Long = 0
    private var preUserId: Long = 0
    private var preAdId: Long = 0
    private var adId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_view)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)


        val intent = intent
        adId = intent.getLongExtra("adId", 0)
        preUserId = intent.getLongExtra("userId", 0)
        preAdId = intent.getLongExtra("preAdId", 0)

        if (adId == 0L)
            adId = preAdId

        val title = find<TextView>(R.id.title_ad)
        val city = find<TextView>(R.id.city_ad)
        val description = find<TextView>(R.id.about_ad)
        val price = find<TextView>(R.id.price_ad)
        val username = find<TextView>(R.id.username_ad)
        val telephone = find<TextView>(R.id.user_phone_number_ad)
        val button = find<Button>(R.id.go_to_user)

        button.setOnClickListener {
            val intent = Intent(this, UserAdActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("preAdId", adId)
            startActivity(intent)
        }

        username.setOnClickListener {
            val intent = Intent(this, UserViewActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("adId", adId)
            startActivity(intent)
        }

        doAsync {
            val ad: ResultAd? = adData(adId)

            uiThread {

                progressBar_ad_view.visibility = ProgressBar.INVISIBLE

                if (ad != null) {
                    title.text = ad.title
                    city.text = ad.city
                    description.text = ad.description_ad
                    price.text = ad.price.toString()
                    username.text = (ad.owner_ad.first_name + " " + ad.owner_ad.last_name)
                    telephone.text = ad.owner_ad.tel_number
                    userId = ad.owner_ad.id
                    actionBar.title = title.text
                }
            }
        }

        progressBar_ad_view.visibility = ProgressBar.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val zero: Long = 0
        if (preUserId == zero) {
            if (!intent.hasExtra("request")) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                val string: String = intent.getStringExtra("request")
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra("request", string)
                startActivity(intent)
            }
        } else {
            val intent = Intent(this, UserAdActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("preAdId", preAdId)
            startActivity(intent)
        }

        return true
    }

    private fun adData(
        adId: Long
    ): ResultAd? {
        var ad: ResultAd? = null
        val apiService: ApiService = ApiService.create()

        apiService.getAd(adId)
            .subscribe({ result ->
                ad = result

            }, { error ->
                toast(handleError(error))
            })

        return ad
    }
}
