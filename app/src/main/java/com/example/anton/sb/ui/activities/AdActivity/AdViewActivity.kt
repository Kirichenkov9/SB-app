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

    private var user_id: Long = 0
    private var pre_user_id: Long = 0
    private var pre_ad_id: Long = 0
    private var ad_id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ad_view)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        ad_id = intent.getLongExtra("ad_id", 0)
        pre_user_id = intent.getLongExtra("user_id", 0)
        pre_ad_id = intent.getLongExtra("pre_ad_id", 0)

        if (ad_id == 0L)
            ad_id = pre_ad_id

        val title = find<TextView>(R.id.title_ad)
        val city = find<TextView>(R.id.city_ad)
        val description = find<TextView>(R.id.about_ad)
        val price = find<TextView>(R.id.price_ad)
        val username = find<TextView>(R.id.username_ad)
        val telephone = find<TextView>(R.id.user_phone_number_ad)
        val button = find<Button>(R.id.go_to_user)


        doAsync {
                AdData(ad_id, title, city, description, price, username, telephone)
            uiThread { actionBar.title = title.text }
        }

        button.setOnClickListener {
            val intent = Intent(this, UserAdActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("pre_ad_id", ad_id)
            startActivity(intent)
        }

        username.setOnClickListener {
            val intent = Intent(this, UserViewActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("ad_id", ad_id)
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
        if (pre_user_id == zero) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, UserAdActivity::class.java)
            intent.putExtra("user_id", user_id)
            intent.putExtra("pre_ad_id", pre_ad_id)
            startActivity(intent)
        }

        return true
    }

    private fun AdData(
        ad_id: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView,
        username: TextView,
        telephone: TextView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.get_ad(ad_id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                title.text = result.title
                city.text = result.city
                description.text = result.description
                price.text = result.price.toString()
                username.text = (result.owner_ad.first_name + " " + result.owner_ad.last_name)
                telephone.text = result.owner_ad.tel_number
                user_id = result.owner_ad.id

            }, { error ->
                // handleError(error, "")
            })
    }
}
