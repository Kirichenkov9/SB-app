package com.example.anton.sb.ui.activities.AdActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.UserActivity.UserViewActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
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


        doAsync {
                adData(adId, title, city, description, price, username, telephone)
            uiThread { actionBar.title = title.text }
        }

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
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val zero: Long = 0
        if (preUserId == zero) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, UserAdActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("preAdId", preAdId)
            startActivity(intent)
        }

        return true
    }

    private fun adData(
        adId: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView,
        username: TextView,
        telephone: TextView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.getAd(adId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                title.text = result.body()!!.title
                city.text = result.body()!!.city
                description.text = result.body()!!.description_ad
                price.text = result.body()!!.price.toString()
                username.text = (result.body()!!.owner_ad.first_name + " " + result.body()!!.owner_ad.last_name)
                telephone.text = result.body()!!.owner_ad.tel_number
                userId = result.body()!!.owner_ad.id

            }, { error ->
                // handleError(error, "")
            })
    }
}
