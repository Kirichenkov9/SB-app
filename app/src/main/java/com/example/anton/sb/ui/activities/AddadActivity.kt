package com.example.anton.sb.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.anton.sb.R
import kotlinx.android.synthetic.main.activity_addad.*


class AddadActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addad)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        // Adding photo to ad after pressing button
        button_ad_photo.setOnClickListener { }

        // Adding ad after pressing button
        button_ad_add.setOnClickListener { attemptForm() }

    }

    // Start MainActivity after press on home button
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

    // Check the form field for validity
    private fun attemptForm() {
        // Reset errors.
        name.error = null
        about.error = null
        price.error = null
        city.error = null

        // Store values
        val nameStr = name.text.toString()
        val cityStr = city.text.toString()

        var cancel = false
        var focusView: View? = null

        // Field check
        if (TextUtils.isEmpty(nameStr)) {
            name.error = getString(R.string.error_field_required)
            focusView = name
            cancel = true
        }

        if (TextUtils.isEmpty(cityStr)) {
            city.error = getString(R.string.error_field_required)
            focusView = city
            cancel = true
        }

        if (cancel) {
            // There was an error
            focusView?.requestFocus()
        } else {
            // Ad add
            // добавить отправку запроса на вход
            showToast(this)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun showToast(view: AddadActivity) {
        // Create and show message
        val toast = Toast.makeText(
            applicationContext,
            getString(R.string.ad_was_aded),
            Toast.LENGTH_SHORT
        )
        toast.setGravity(Gravity.BOTTOM, 0, 20)
        toast.show()
    }
}

