package com.example.anton.sb.ui.activities.AdActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class ChangeAdActivity : AppCompatActivity() {

    private val keyToken = "token"

    private var token: String? = null
    private var adId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_ad)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        token = readToken()

        val intent = intent
        adId = intent.getLongExtra("adId", 0)

        toast("${adId}")

        val title = find<EditText>(R.id.change_title)
        val city = find<EditText>(R.id.change_city)
        val description = find<EditText>(R.id.change_description)
        val price = find<EditText>(R.id.change_price)
        val button = find<Button>(R.id.change_ad)

        adData(adId, title, city, description, price)

        button.setOnClickListener {
            adChange(
                adId,
                title.text.toString(),
                city.text.toString(),
                description.text.toString(),
                price.text.toString().toInt()
            )
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

    private fun adChange(
        adId: Long,
        title: String,
        city: String,
        description: String,
        price: Int
    ) {
        val apiService: ApiService = ApiService.create()

        apiService.changeAd(adId, token.toString(), title, price, city, description)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                val intent = Intent(this, MyAdActivity::class.java)
                startActivity(intent)
            }, { error ->
                val intent = Intent(this, MyAdActivity::class.java)
                startActivity(intent)


                // handleError(error, "")
            })
    }


    private fun adData(
        adId: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.getAd(adId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                title.text = result.body()!!.title
                city.text = result.body()!!.city
                description.text = result.body()!!.description_ad
                price.text = result.body()!!.price.toString()

            }, { error ->
                // handleError(error, "")
            })
    }

    private fun readToken(): String? {
        var string: String? = null
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (saveToken.contains("token")) {
            string = saveToken.getString("token", null)
        }

        return string
    }
}
