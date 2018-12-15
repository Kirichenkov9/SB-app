package com.example.anton.sb.ui.activities.userActivity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.data.ResultUser
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.changeData
import com.example.anton.sb.service.userData
import kotlinx.android.synthetic.main.activity_change_user.* // ktlint-disable no-wildcard-imports
import org.jetbrains.anko.* // ktlint-disable no-wildcard-imports

/**
 * A screen changing user information
 *
 * @author Anton Kirichenkov
 */

class ChangeUserActivity : AppCompatActivity() {

    /**
     * @property token
     */

    /**
     * user session_id
     */
    private var token: String? = null

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_user)

        val actionBar = supportActionBar
        actionBar!!.setHomeButtonEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        token = readUserData("token", this)

        val firstName = find<EditText>(R.id.change_first_name)
        val lastName = find<EditText>(R.id.change_last_name)
        val telNumber = find<EditText>(R.id.change_tel_number)
        val about = find<EditText>(R.id.change_about)

        doAsync {
            val user = userData(token.toString(), this@ChangeUserActivity)
            uiThread {
                progressBar_user_change.visibility = ProgressBar.INVISIBLE
                if (user != null)
                    setUserData(user, firstName, lastName, telNumber, about)
            }
        }

        progressBar_user_change.visibility = ProgressBar.VISIBLE

        change_user.setOnClickListener {
            changeUser()
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
        startActivity<UserSettingsActivity>()
        return true
    }

    /**
     * Checking entered data and change data or display error.
     * This method called [changeData]
     */
    private fun changeUser() {
        var cancel = false
        var focusView: View? = null

        change_first_name.error = null
        change_last_name.error = null

        val firstNameStr: String? = change_first_name.text.toString()
        val lastNameStr: String? = change_last_name.text.toString()
        val telephoneStr: String? = change_tel_number.text.toString()
        val aboutStr: String? = change_about.text.toString()

        if (TextUtils.isEmpty(firstNameStr)) {
            change_first_name.error = getString(R.string.error_field_required)
            focusView = change_first_name
            cancel = true
        }

        if (TextUtils.isEmpty(lastNameStr)) {
            change_last_name.error = getString(R.string.error_field_required)
            focusView = change_first_name
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            changeUserAlert(
                token.toString(),
                firstNameStr.toString(),
                lastNameStr.toString(),
                telephoneStr.toString(),
                aboutStr.toString()
            )
        }
    }

    /**
     * Setting data about user in EditText field.
     *
     * @param user data about user
     * @param firstName field of EditText with user first name
     * @param lastName field of EditText with user last name
     * @param telNumber field of EditText with user phone number
     * @param about
     */
    private fun setUserData(
        user: ResultUser,
        firstName: TextView,
        lastName: TextView,
        telNumber: TextView,
        about: TextView
    ) {
        firstName.text = user.first_name
        lastName.text = user.last_name
        telNumber.text = user.tel_number
        about.text = user.about
    }

    /**
     * Display alertDialog. If user answer "Да", then called [changeData]
     * and user information is change, else nothing happens.
     *
     * @param token user session_id
     * @param firstNameStr user first name
     * @param lastNameStr user last name
     * @param telephoneStr user phone number
     * @param aboutStr information about user
     *
     * @see changeData
     */
    private fun changeUserAlert(
        token: String,
        firstNameStr: String,
        lastNameStr: String,
        telephoneStr: String,
        aboutStr: String
    ) {
        alert(message = "Сохранить измнения?") {
            positiveButton("Да") {
                changeData(
                    token,
                    firstNameStr,
                    lastNameStr,
                    telephoneStr,
                    aboutStr,
                    progressBar_user_change,
                    this@ChangeUserActivity
                )
                progressBar_user_change.visibility = ProgressBar.VISIBLE
            }
            negativeButton("Нет") {}
        }.show()
    }
}
