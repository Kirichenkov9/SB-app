package com.example.anton.sb.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import com.example.anton.sb.R
import com.example.anton.sb.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_settings.*

class UserSettingsActivity : AppCompatActivity() {

    internal lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        exit_account.setOnClickListener {
            logout()
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home -> {
                this.finish()
            }
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        return true
    }

    private fun logout() {
        val apiService: ApiService = ApiService.create()

        apiService.logout_user(token)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe ({
                    result ->
                Log.d("Result", "Success.")
                removeFile()
                showToast(this)
            }, { error ->
                error.printStackTrace()
            })
    }
    private fun removeFile(){}
    private fun showToast(view: UserSettingsActivity) {
        // Create and show message
        val toast = Toast.makeText(
            applicationContext,
            getString(R.string.exit_user),
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.BOTTOM, 0, 20)
        toast.show()
    }

    }



