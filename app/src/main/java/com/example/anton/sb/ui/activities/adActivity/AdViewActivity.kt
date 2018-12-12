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
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.ui.activities.userActivity.UserViewActivity
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_ad_view.*
import org.jetbrains.anko.find
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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

        adData(adId, title, city, description, price, username, telephone, photo)

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
                toast("Нет разрешения совершать звонки")
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
     * Get ad information. This method use [ApiService.getAd] and processing response from server.
     * If response is successful, then display information about ad, else - display error
     * processing by [handleError].
     *
     * @param adId ad id
     * @param title title ad
     * @param city city of ad
     * @param description description of ad
     * @param price ad price
     * @param username full name of owner ad
     * @param telephone phone number of owner ad
     * @param photo ad photo
     *
     * @see [ApiService.getAd]
     * @see [handleError]
     */
    private fun adData(
        adId: Long,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView,
        username: TextView,
        telephone: TextView,
        photo: ImageView
    ) {
        val apiService: ApiService = ApiService.create()

        apiService.getAd(adId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_ad_view.visibility = ProgressBar.INVISIBLE

                title.text = result.title
                city.text = result.city
                description.text = result.description_ad
                price.text = result.price.toString()
                username.text = (result.owner_ad.first_name + " " + result.owner_ad.last_name)
                telephone.text = result.owner_ad.tel_number
                userId = result.owner_ad.id
                phone = result.owner_ad.tel_number
                if (result.ad_images.isEmpty()) {
                    Picasso
                        .with(this@AdViewActivity)
                        .load(result.ad_images[0])
                        .placeholder(R.drawable.ic_image_ad)
                        .error(R.drawable.ic_image_ad)
                        .fit()
                        .centerCrop()
                        .into(photo)
                }
                actionBar?.title = title.text
            }, { error ->
                progressBar_ad_view.visibility = ProgressBar.INVISIBLE

                toast(handleError(error))
            })
    }
}
