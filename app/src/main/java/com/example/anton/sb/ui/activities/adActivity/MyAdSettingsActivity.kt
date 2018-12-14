package com.example.anton.sb.ui.activities.adActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.adData
import com.example.anton.sb.service.deleteAd
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_my_ad_settings.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

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

        val token = readUserData("token", this)

        val title = find<TextView>(R.id.title_ad_settings)
        val city = find<TextView>(R.id.city_ad_settings)
        val description = find<TextView>(R.id.about_ad_settings)
        val price = find<TextView>(R.id.price_ad_settings)
        val photo = find<ImageView>(R.id.ad_photos)
        val button = find<Button>(R.id.delete_ad)

        doAsync {
            val ad = adData(adId, this@MyAdSettingsActivity)
            uiThread {
                progressBar_ad_settings.visibility = ProgressBar.INVISIBLE
                if (ad != null) {
                    setData(ad, title, city, description, price, photo)
                    actionBar.title = ad.title
                }
            }
        }

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
            deleteAd(adId, token, progressBar_ad_settings, this)
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
        startActivity<MyAdsActivity>()
        return true
    }

    /**
     * Setting data about ad in EditText field.
     *
     * @param ad ad
     * @param title field of EditText
     * @param city field of EditText
     * @param description field of EditText
     * @param price field of EditText
     * @param photo ad photo
     */
    private fun setData(
        ad: ResultAd,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView,
        photo: ImageView
    ) {
        title.text = ad.title
        city.text = ad.city
        description.text = ad.description_ad
        price.text = ad.price.toString()
        if (ad.ad_images.isNotEmpty()) {
            Picasso
                .with(this@MyAdSettingsActivity)
                .load(ad.ad_images[0])
                .placeholder(R.drawable.ic_image_ad)
                .error(R.drawable.ic_image_ad)
                .fit()
                .centerInside()
                .into(photo)
        }
    }
}