package com.example.anton.sb.ui.activities.adActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.adChange
import com.example.anton.sb.service.adData
import kotlinx.android.synthetic.main.activity_change_ad.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.uiThread

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

        button.setOnClickListener {
            adChange(
                adId,
                title.text.toString(),
                city.text.toString(),
                description.text.toString(),
                price.text.toString().toInt(),
                photo,
                token.toString(),
                progressBar_ad_change,
                this
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
}