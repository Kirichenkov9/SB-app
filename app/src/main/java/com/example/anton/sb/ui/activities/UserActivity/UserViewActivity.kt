package com.example.anton.sb.ui.activities.UserActivity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import com.example.anton.sb.ui.activities.AdActivity.AdViewActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread

class UserViewActivity : AppCompatActivity() {

    private var ad_id: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_view)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        val id = intent.getLongExtra("user_id", 0)
        ad_id = intent.getLongExtra("ad_id", 0)


        val first_name = find<TextView>(R.id.first_user_name_view)
        val last_name = find<TextView>(R.id.last_user_name_view)
        val email = find<TextView>(R.id.user_email_view)
        val telephone = find<TextView>(R.id.user_phone_number_view)
        val about = find<TextView>(R.id.user_about_view)

        doAsync {
            userData(first_name, last_name, email, telephone, about, id)
            uiThread { actionBar.title = first_name.text.toString() + " " + last_name.text.toString()}
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, AdViewActivity::class.java)
        intent.putExtra("ad_id", ad_id)
        startActivity(intent)
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

        apiService.get_user(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ result ->

                first_name.text = result.first_name
                last_name.text = result.last_name
                email.text = result.email
                telephone.text = result.tel_number
                about.text = result.about

            }, { error ->
                //
                // handleError(error, "")
            })
    }
}
