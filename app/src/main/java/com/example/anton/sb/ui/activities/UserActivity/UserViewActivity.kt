package com.example.anton.sb.ui.activities.UserActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.data.Extensions.handleError
import com.example.anton.sb.ui.activities.AdActivity.AdViewActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_user_view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.email
import org.jetbrains.anko.find
import org.jetbrains.anko.makeCall
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class UserViewActivity : AppCompatActivity() {

    private var adId: Long = 0

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
            userData(firstName, lastName, email, telephone, about, id)
            uiThread { actionBar.title = firstName.text.toString() + " " + lastName.text.toString() }
        }
        progressBar_user_view.visibility = ProgressBar.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        startActivity<AdViewActivity>("adId" to adId)
        return true
    }

    private fun userData(
        first_name: TextView,
        last_name: TextView,
        email: TextView,
        telephone: TextView,
        about: TextView,
        id: Long
    ) {
        val apiService: ApiService = ApiService.create()

        apiService.getUser(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->
                progressBar_user_view.visibility = ProgressBar.INVISIBLE

                first_name.text = result.first_name
                last_name.text = result.last_name
                email.text = result.email
                telephone.text = result.tel_number
                about.text = result.about
            }, { error ->
                progressBar_user_view.visibility = ProgressBar.INVISIBLE

                toast(handleError(error))
            })
    }
}
