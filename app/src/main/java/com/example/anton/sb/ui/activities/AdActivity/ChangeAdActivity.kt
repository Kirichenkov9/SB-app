package com.example.anton.sb.ui.activities.AdActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_ad.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class ChangeAdActivity : AppCompatActivity() {

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

        val title = find<EditText>(R.id.change_title)
        val city = find<EditText>(R.id.change_city)
        val description = find<EditText>(R.id.change_description)
        val price = find<EditText>(R.id.change_price)
        val button = find<Button>(R.id.change_ad)

        adData(adId, title, city, description, price)
        progressBar_ad_change.visibility = ProgressBar.VISIBLE

        button.setOnClickListener {
            adChange(
                adId,
                title.text.toString(),
                city.text.toString(),
                description.text.toString(),
                price.text.toString().toInt()
            )
            progressBar_ad_change.visibility = ProgressBar.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, MyAdSettingsActivity::class.java)
        intent.putExtra("adId", adId)
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
            .subscribe({
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                toast("Объявление изменено")

                this.finish()

                val intent = Intent(this, MyAdSettingsActivity::class.java)
                intent.putExtra("adId", adId)
                startActivity(intent)
            }, { error ->
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "empty body") {

                    toast("Объявление изменено")

                    this.finish()

                    val intent = Intent(this, MyAdSettingsActivity::class.java)
                    intent.putExtra("adId", adId)
                    startActivity(intent)
                } else
                    toast("$errorStr")
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
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                title.text = result.title
                city.text = result.city
                description.text = result.description_ad
                price.text = result.price.toString()

            }, { error ->
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "empty body")
                    toast("Нет соединения с сервером")
                else
                    toast("$errorStr")
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
