package com.example.anton.sb.ui.activities.AdActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class MySettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ad_settings)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val idAd = intent.getLongExtra("adId", 0)


        val title = find<TextView>(R.id.title_ad_settings)
        val city = find<TextView>(R.id.city_ad_settings)
        val description = find<TextView>(R.id.about_ad_settings)
        val price = find<TextView>(R.id.price_ad_settings)

        doAsync {
            adData(idAd, title, city, description, price)
            uiThread { actionBar.title = title.text }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, MyAdActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun adData(
        id: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.getAd(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                title.text = result.body()!!.title
                city.text = result.body()!!.city
                description.text = result.body()!!.description
                price.text = result.body()!!.price.toString()

            }, { error ->
                // handleError(error, "")
            })
    }
}
