package com.example.anton.sb.ui.activities.AdActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.ui.activities.UserActivity.LoginActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_ad_settings.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class MyAdSettingsActivity : AppCompatActivity() {

    var adId: Long = 0

    private val keyToken = "token"
    private val name: String = "name"
    private val mail: String = "email"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ad_settings)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        adId = intent.getLongExtra("adId", 0)

        val title = find<TextView>(R.id.title_ad_settings)
        val city = find<TextView>(R.id.city_ad_settings)
        val description = find<TextView>(R.id.about_ad_settings)
        val price = find<TextView>(R.id.price_ad_settings)
        val button = find<Button>(R.id.delete_ad)

        doAsync {
            adData(adId, title, city, description, price)
            uiThread { actionBar.title = title.text }
        }

        progressBar_ad_settings.visibility = ProgressBar.VISIBLE

        title.setOnClickListener {
            val intent = Intent(this, ChangeAdActivity::class.java)
            intent.putExtra("adId", adId)
            startActivity(intent)
        }

        city.setOnClickListener {
            val intent = Intent(this, ChangeAdActivity::class.java)
            intent.putExtra("adId", adId)
            startActivity(intent)
        }

        description.setOnClickListener {
            val intent = Intent(this, ChangeAdActivity::class.java)
            intent.putExtra("adId", adId)
            startActivity(intent)
        }

        price.setOnClickListener {
            val intent = Intent(this, ChangeAdActivity::class.java)
            intent.putExtra("adId", adId)
            startActivity(intent)
        }

        button.setOnClickListener {
            deleteAd()
            progressBar_ad_settings.visibility = ProgressBar.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, MyAdsActivity::class.java)
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
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
                title.text = result.title
                city.text = result.city
                description.text = result.description_ad
                price.text = result.price.toString()
            }, { error ->
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
    }

    private fun deleteAd() {
        val token = read("token")
        val apiService: ApiService = ApiService.create()

        apiService.deleteAd(adId, token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
                toast("Объявление удалено")

                this.finish()

                val intent = Intent(this, MyAdsActivity::class.java)
                startActivity(intent)
            }, { error ->
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "empty body") {
                    toast("Объявление удалено")

                    this.finish()

                    val intent = Intent(this, MyAdsActivity::class.java)
                    startActivity(intent)
                } else if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeToken()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                } else
                    toast(errorStr)
            })
    }

    private fun removeToken() {
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = saveToken.edit()

        editor.remove(keyToken)
        editor.remove(name)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }

    private fun read(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }
}
