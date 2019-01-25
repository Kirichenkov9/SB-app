package com.example.anton.sb.ui.activities.adActivity

import android.content.pm.PackageManager
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.service.adData
import com.example.anton.sb.ui.activities.userActivity.UserViewActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ad_view.*// ktlint-disable no-wildcard-imports
import org.jetbrains.anko.*// ktlint-disable no-wildcard-imports
import org.jetbrains.anko.design.snackbar

/**
 * A screen of ad information
 *
 *@author Anton Kirichenkov
 */

class AdViewActivity : AppCompatActivity() {

    /**
     * @property userId
     * @property preUserId
     * @property preAdId
     * @property adId
     * @property phone
     */

    /**
     * id of owner ad
     */
    private var userId: Long = 0
    /**
     * this variable for check previous Activity
     */
    private var preUserId: Long = 0

    /**
     * id of previous ad
     */
    private var preAdId: Long = 0

    /**
     * ad id
     */
    private var adId: Long = 0

    /**
     *  phone number of owner ad
     */
    private var phone: String = ""

    /**
     * @suppress
     */
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
        val photo = find<ImageView>(R.id.ad_photos)
        val button = find<Button>(R.id.go_to_user)

        doAsync {
            val ad = adData(adId, this@AdViewActivity)
            uiThread {
                progressBar_ad_view.visibility = ProgressBar.INVISIBLE
                if (ad != null) {
                    title.text = ad.title
                    city.text = ad.city
                    description.text = ad.description_ad
                    price.text = ad.price.toString()
                    username.text = (ad.owner_ad.first_name + " " + ad.owner_ad.last_name)
                    telephone.text = ad.owner_ad.tel_number
                    phone = telephone.text.toString()
                    userId = ad.owner_ad.id
                    if (ad.ad_images.size != 0) {
                        Picasso
                            .with(this@AdViewActivity)
                            .load(ad.ad_images[0])
                            .placeholder(R.drawable.ic_image_ad)
                            .error(R.drawable.ic_image_ad)
                            .fit()
                            .centerInside()
                            .into(photo)
                    }
                    actionBar.title = title.text
                }
            }
        }
        progressBar_ad_view.visibility = ProgressBar.VISIBLE

        button.setOnClickListener {
            if (title.text != "")
                startActivity<UserAdActivity>(
                    "userId" to userId,
                    "preAdId" to adId
                )
            else if (progressBar_ad_view.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }

        username.setOnClickListener {
            if (title.text != "")
                startActivity<UserViewActivity>(
                    "userId" to userId,
                    "adId" to adId
                )
            else if (progressBar_ad_view.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }

        telephone.setOnClickListener {
            if (title.text != "")
                phoneAlert(phone)

            else if (progressBar_ad_view.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }
    }

    /**
     * Make phone call by number of owner ad.
     * This method check availability permission for making call.
     *
     * @param string phone number
     */
    private fun makePhoneCall(string: String) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        else this.makeCall(string)
    }

    /**
     * @suppress
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                makeCall(phone)
            else
                contentView?.snackbar("Нет разрешения совершать звонки")
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
        val zero: Long = 0
        if (preUserId == zero) {
            if (!intent.hasExtra("request")) {
                startActivity<MainActivity>()
            } else {
                val string: String = intent.getStringExtra("request")
                startActivity<SearchActivity>(
                    "request" to string
                )
            }
        } else {
            startActivity<UserAdActivity>(
                "userId" to userId,
                "preAdId" to preAdId
            )
        }
        return true
    }

    /**
     * Display alertDialog. If user answer "Да", then called [makePhoneCall],
     * else nothing happens.
     *
     * @param string phone number
     *
     * @see makePhoneCall
     */
    private fun phoneAlert(string: String) {
        alert(message = "Позвонить владельцу объявления?") {
            positiveButton("Да") { makePhoneCall(string) }
            negativeButton("Нет") {}
        }.show()
    }
}
