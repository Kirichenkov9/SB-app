package com.example.anton.sb.ui.activities.adActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.model.ResultAd
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.adChange
import com.example.anton.sb.service.adData
import kotlinx.android.synthetic.main.activity_change_ad.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.* // ktlint-disable no-wildcard-imports

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

        token = readUserData("token", this)

        val intent = intent
        adId = intent.getLongExtra("adId", 0)

        val title = find<EditText>(R.id.change_title)
        val city = find<EditText>(R.id.change_city)
        val description = find<EditText>(R.id.change_description)
        val price = find<EditText>(R.id.change_price)
        val button = find<Button>(R.id.change_ad)
        val photo: ArrayList<String> = ArrayList()

        doAsync {
            val ad = adData(adId, this@ChangeAdActivity)
            uiThread {
                progressBar_ad_change.visibility = ProgressBar.INVISIBLE
                if (ad != null) {
                    setData(ad, title, city, description, price)
                    photo.addAll(ad.ad_images)
                }
            }
        }
        progressBar_ad_change.visibility = ProgressBar.VISIBLE

        button.setOnClickListener { attemptForm(photo) }
    }

    /**
     * Checking entered data and change data or display error.
     * This method called [changeAdAlert].
     *
     * @param photo Array of photo URL
     */
    private fun attemptForm(photo: ArrayList<String>) {
        // Reset errors.
        change_title.error = null
        change_city.error = null
        change_description.error = null

        // Store values
        val nameStr = change_title.text.toString()
        val cityStr = change_city.text.toString()
        val aboutStr = change_description.text.toString()

        var cancel = false
        var focusView: View? = null

        // Field check
        if (TextUtils.isEmpty(nameStr)) {
            change_title.error = getString(R.string.error_field_required)
            focusView = change_title
            cancel = true
        }

        if (TextUtils.isEmpty(aboutStr)) {
            change_description.error = getString(R.string.error_field_required)
            focusView = change_description
            cancel = true
        }

        if (TextUtils.isEmpty(cityStr)) {
            change_city.error = getString(R.string.error_field_required)
            focusView = change_city
            cancel = true
        }

        if (cancel) {
            // There was an error
            focusView?.requestFocus()
        } else {
            // Ad add
            changeAdAlert(
                adId,
                nameStr,
                cityStr,
                aboutStr,
                change_price.text.toString().toInt(),
                photo,
                token.toString(),
                progressBar_ad_change
            )
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
     * Setting data about ad in EditText field.
     *
     * @param ad ad
     * @param title field of EditText
     * @param city field of EditText
     * @param description field of EditText
     * @param price field of EditText
     */
    private fun setData(
        ad: ResultAd,
        title: TextView,
        city: TextView,
        description: TextView,
        price: TextView
    ) {
        title.text = ad.title
        city.text = ad.city
        description.text = ad.description_ad
        price.text = ad.price.toString()
    }

    /**
     * Display alertDialog. If user answer "Да", then called [adChange]
     * and changing information about ad,
     * else nothing happens.
     *
     * @param adId ad id
     * @param title field of EditText with ad title
     * @param city field of EditText with ad city
     * @param description field of EditText with ad description
     * @param price field of EditText with ad price
     * @param photo ArrayList of ad photo URL
     * @param token user session_id
     * @param progressBar_ad_change ProgressBar
     *
     * @see adChange
     */
    private fun changeAdAlert(
        adId: Long,
        title: String,
        city: String,
        description: String,
        price: Int,
        photo: ArrayList<String>,
        token: String?,
        progressBar_ad_change: ProgressBar
    ) {
        alert(message = "Сохранить измнения?") {
            positiveButton("Да") {
                adChange(
                    adId,
                    title,
                    city,
                    description,
                    price,
                    photo,
                    token.toString(),
                    progressBar_ad_change,
                    this@ChangeAdActivity
                )
                progressBar_ad_change.visibility = ProgressBar.VISIBLE
            }
            negativeButton("Нет") {}
        }.show()
    }
}