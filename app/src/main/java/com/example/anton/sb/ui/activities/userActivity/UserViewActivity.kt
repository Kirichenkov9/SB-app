package com.example.anton.sb.ui.activities.userActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.service.userData
import com.example.anton.sb.service.userViewData
import com.example.anton.sb.ui.activities.adActivity.AdViewActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.email
import org.jetbrains.anko.find
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

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
            this.makeCall(telephone.text.toString())
        }

        email.setOnClickListener {
            this.email(email.text.toString())
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
}
