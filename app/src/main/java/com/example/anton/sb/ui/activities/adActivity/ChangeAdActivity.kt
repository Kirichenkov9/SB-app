package com.example.anton.sb.ui.activities.adActivity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.extensions.handleError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_ad.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * A screen changing ad
 *
 * @author Anton Kirichenkov
 */

class ChangeAdActivity : AppCompatActivity() {

    /**
     * @property token
     * @property adId
     */

    /**
     * user session_id
     */
    private var token: String? = null

    /**
     * ad id
     */
    private var adId: Long = 0

    /**
     * @suppress
     */
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
        val photo: ArrayList<String> = ArrayList()

        adData(adId, title, city, description, price, photo)

        progressBar_ad_change.visibility = ProgressBar.VISIBLE

        button.setOnClickListener {
            adChange(
                adId,
                title.text.toString(),
                city.text.toString(),
                description.text.toString(),
                price.text.toString().toInt(),
                photo
            )
            progressBar_ad_change.visibility = ProgressBar.VISIBLE
        }
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
        startActivity<MyAdSettingsActivity>("adId" to adId)
        return true
    }

    /**
     * Change ad information. This method use [ApiService.changeAd] and processing response from server.
     * If response is successful, then display "Объявление изменено" and start MyAdSettingsActivity,
     * else - display error processing by [handleError].
     *
     * @param adId ad id
     * @param title title ad
     * @param city city of ad
     * @param description description of ad
     * @param price ad price
     *
     * @see [handleError]
     * @see [ApiService.changeAd]
     */
    private fun adChange(
        adId: Long,
        title: String,
        city: String,
        description: String,
        price: Int,
        photo: ArrayList<String>
    ) {
        val apiService: ApiService = ApiService.create()

        apiService.changeAd(adId, token.toString(), title, price, city, description, photo)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                toast("Объявление изменено")

                this.finish()

                startActivity<MyAdSettingsActivity>("adId" to adId)
            }, { error ->
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                val errorStr = handleError(error)
                if (errorStr == "empty body") {

                    toast("Объявление изменено")

                    this.finish()

                    startActivity<MyAdSettingsActivity>("adId" to adId)
                } else
                    toast(errorStr)
            })
    }

    /**
     * Get ad information. This method use [ApiService.getAd] and processing response from server.
     * If response is successful, then display information about ad, else - display error
     * processing by [handleError].
     *
     * @param adId ad id
     * @param title title ad
     * @param city city of ad
     * @param description description of ad
     * @param price ad price
     *
     * @see [handleError]
     * @see [ApiService.getAd]
     */
    private fun adData(
        adId: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView,
        photo: ArrayList<String>
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
                photo.addAll(result.ad_images!!)
            }, { error ->
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE

                val errorStr = handleError(error)
                if (errorStr == "empty body")
                    toast("Нет соединения с сервером")
                else
                    toast(errorStr)
            })
    }

    /**
     * Reading user  session_id from SharedPreference.
     *
     * @return [String]
     */
    private fun readToken(): String? {
        var sessionId: String? = null
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (saveToken.contains("token")) {
            sessionId = saveToken.getString("token", null)
        }

        return sessionId
    }
}
