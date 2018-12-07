package com.example.anton.sb.ui.activities.adActivity

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.PermissionChecker.PERMISSION_GRANTED
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.data.ResponseClasses.ResultAd
import com.example.anton.sb.ui.activities.userActivity.UserViewActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_ad_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class AdViewActivity : AppCompatActivity() {

    private var userId: Long = 0
    private var preUserId: Long = 0
    private var preAdId: Long = 0
    private var adId: Long = 0
    private var phone: String = ""

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
            val ad: ResultAd? = adData(adId)
            uiThread {
                if (ad != null) {
                    progressBar_ad_view.visibility = ProgressBar.INVISIBLE
                    title.text = ad.title
                    city.text = ad.city
                    description.text = ad.description_ad
                    price.text = ad.price.toString()
                    username.text = (ad.owner_ad.first_name + " " + ad.owner_ad.last_name)
                    telephone.text = ad.owner_ad.tel_number
                    userId = ad.owner_ad.id
                    actionBar.title = title.text
                    phone = ad.owner_ad.tel_number
                    Picasso
                        .with(this@AdViewActivity)
                        .load(ad.images_url?.get(0))
                        .placeholder(R.drawable.ic_image_ad)
                        .error(R.drawable.ic_image_ad)
                        .fit()
                        .centerCrop()
                        .into(photo)
                }
            }
        }

        progressBar_ad_view.visibility = ProgressBar.VISIBLE

        button.setOnClickListener {
            startActivity<UserAdActivity>(
                "userId" to userId,
                "preAdId" to adId
            )
        }

        username.setOnClickListener {
            startActivity<UserViewActivity>(
                "userId" to userId,
                "adId" to adId
            )
        }

        telephone.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
            } else this.makeCall(phone)
        }
    }

    private fun makePhoneCall(string: String) {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        else this.makeCall(string)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                makeCall(phone)
            else
                toast("Нет разрешения совершать звонки")
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

    private fun adData(
        adId: Long
    ): ResultAd? {
        var ad: ResultAd? = null
        val apiService: ApiService = ApiService.create()

        apiService.getAd(adId)
            .subscribe({ result ->

                ad = result
                progressBar_ad_view.visibility = ProgressBar.INVISIBLE
            }, { error ->
                progressBar_ad_view.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
        return ad
    }
}
