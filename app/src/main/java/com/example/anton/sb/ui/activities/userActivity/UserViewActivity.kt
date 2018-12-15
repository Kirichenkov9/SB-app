package com.example.anton.sb.ui.activities.userActivity

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.service.userViewData
import com.example.anton.sb.ui.activities.adActivity.AdViewActivity
import kotlinx.android.synthetic.main.activity_user_view.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.* // ktlint-disable no-wildcard-imports

/**
 * A screen information about user
 *
 * @author Anton Kirichenkov
 */
class UserViewActivity : AppCompatActivity() {

    /**
     * @property adId
     */

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
        setContentView(R.layout.activity_user_view)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val id = intent.getLongExtra("userId", 0)
        adId = intent.getLongExtra("adId", 0)

        val firstName = find<TextView>(R.id.first_user_name_view)
        val lastName = find<TextView>(R.id.last_user_name_view)
        val email = find<TextView>(R.id.user_email_view)
        val telephone = find<TextView>(R.id.user_phone_number_view)
        val about = find<TextView>(R.id.user_about_view)

        telephone.setOnClickListener {
            phoneAlert(phone)
        }

        email.setOnClickListener {
            emailAlert(email.text.toString())
        }

        doAsync {
            val user = userViewData(id, this@UserViewActivity)
            uiThread {
                progressBar_user_view.visibility = ProgressBar.INVISIBLE
                if (user != null) {
                    firstName.text = user.first_name
                    lastName.text = user.last_name
                    email.text = user.email
                    telephone.text = user.tel_number
                    about.text = user.about
                    actionBar.title = firstName.text.toString() + " " + lastName.text.toString()
                }
            }
        }
        progressBar_user_view.visibility = ProgressBar.VISIBLE
    }

    /**
     * Make phone call by number of owner ad.
     * This method check availability permission for making call.
     *
     * @param string phone number
     */
    private fun makePhoneCall(string: String) {
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        )
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE), 1)
        else makeCall(string)
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
        startActivity<AdViewActivity>("adId" to adId)
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
            positiveButton("Да") {
                makePhoneCall(string)
            }
            negativeButton("Нет") {}
        }.show()
    }

    /**
     * Display alertDialog. If user answer "Да", then start app for send email,
     * else nothing happens.
     *
     * @param string email address
     */
    private fun emailAlert(string: String) {
        alert(message = "Написать на email владельцу объявления?") {
            positiveButton("Да") {
                email(string)
            }
            negativeButton("Нет") {}
        }.show()
    }
}
