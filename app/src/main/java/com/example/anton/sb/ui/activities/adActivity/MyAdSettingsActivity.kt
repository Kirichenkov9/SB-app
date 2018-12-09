package com.example.anton.sb.ui.activities.adActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_my_ad_settings.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * A screen ad view of logged ih user
 *
 *@author Anton Kirichenkov
 */
class MyAdSettingsActivity : AppCompatActivity() {

    /**
     * @property token
     * @property keyToken
     * @property name
     * @property mail
     * @property adId
     */

    /**
     * saved session_id
     */
    private var token: String? = null

    /**
     * token key for SharedPreference
     */
    private val keyToken = "token"

    /**
     * username key for SharedPreference
     */
    private val name: String = "name"

    /**
     * email key for SharedPreference
     */
    private val mail: String = "email"

    /**
     *  ad id
     */
    private var adId: Long = 0

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_ad_settings)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        adId = intent.getLongExtra("adId", 0)

        val token = readData("token")

        val title = find<TextView>(R.id.title_ad_settings)
        val city = find<TextView>(R.id.city_ad_settings)
        val description = find<TextView>(R.id.about_ad_settings)
        val price = find<TextView>(R.id.price_ad_settings)
        val photo = find<ImageView>(R.id.ad_photos)
        val button = find<Button>(R.id.delete_ad)

        adData(adId, title, city, description, price, photo)

        progressBar_ad_settings.visibility = ProgressBar.VISIBLE

        title.setOnClickListener {
            startActivity<ChangeAdActivity>("adId" to adId)
        }

        city.setOnClickListener {
            startActivity<ChangeAdActivity>("adId" to adId)
        }

        description.setOnClickListener {
            startActivity<ChangeAdActivity>("adId" to adId)
        }

        price.setOnClickListener {
            startActivity<ChangeAdActivity>("adId" to adId)
        }

        button.setOnClickListener {
            deleteAd(token)
            progressBar_ad_settings.visibility = ProgressBar.VISIBLE
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
        val intent = Intent(this, MyAdsActivity::class.java)
        startActivity(intent)
        return true
    }

    /**
     * Get ad information. This method use [ApiService.getAd] and processing response from server.
     * If response is successful, then display information about ad, else - display error
     * processing by [handleError].
     *
     * @param id ad id
     * @param title title ad
     * @param city city of ad
     * @param description description of ad
     * @param price ad price
     * @param photo ad photo
     *
     * @see [ApiService.getAd]
     * @see [handleError]
     */
    private fun adData(
        id: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView,
        photo: ImageView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.getAd(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE

                title.text = result.title
                city.text = result.city
                description.text = result.description_ad
                price.text = result.price.toString()
                Picasso
                    .with(this@MyAdSettingsActivity)
                    .load(result.ad_images?.get(0))
                    .placeholder(R.drawable.ic_image_ad)
                    .error(R.drawable.ic_image_ad)
                    .fit()
                    .centerCrop()
                    .into(photo)

                actionBar?.title = title.text
            }, { error ->
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE

                toast(handleError(error))
            })
    }

    /**
     * Deleting ad. This method use [ApiService.deleteAd] and processing response from server.
     * If response is successful, then display message "Объявление удалено" and start MyAdsActivity, else - display error
     * processing by [handleError].
     *
     * @param token user session_id
     */
    private fun deleteAd(token: String?) {
        val apiService: ApiService = ApiService.create()

        apiService.deleteAd(adId, token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
                toast("Объявление удалено")

                this.finish()
                startActivity<MyAdsActivity>()
            }, { error ->
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE

                val errorStr = handleError(error)

                if (errorStr == "empty body") {
                    toast("Объявление удалено")

                    this.finish()

                    startActivity<MyAdsActivity>()
                } else if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeData()
                    startActivity<LoginActivity>()
                } else
                    toast(errorStr)
            })
    }

    /**
     * Method for remove user data from SharedPreference.
     *
     */
    private fun removeData() {
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = saveToken.edit()

        editor.remove(keyToken)
        editor.remove(name)
        editor.remove(mail)
        editor.clear()
        editor.apply()
    }

    /**
     * Reading information about user by key from SharedPreference.
     *
     * @param key is a key for data from SharedPreference
     */
    private fun readData(key: String): String? {
        var string: String? = null
        val read: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (read.contains(key)) {
            string = read.getString(key, " ")
        }
        return string
    }
}
