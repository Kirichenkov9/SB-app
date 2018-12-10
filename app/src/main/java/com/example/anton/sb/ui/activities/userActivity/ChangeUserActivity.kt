package com.example.anton.sb.ui.activities.userActivity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.extensions.handleError
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_change_user.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

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

        val firstName = find<EditText>(R.id.change_first_name)
        val lastName = find<EditText>(R.id.change_last_name)
        val telNumber = find<EditText>(R.id.change_tel_number)
        val about = find<EditText>(R.id.change_about)

        userData(firstName, lastName, telNumber, about)
        progressBar_user_change.visibility = ProgressBar.VISIBLE

        change_user.setOnClickListener {
            changeUser()
            progressBar_user_change.visibility = ProgressBar.VISIBLE
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
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView?.requestFocus()
        } else {
            // Show a progress spinner and to
            // perform the user login attempt.
            changeData(
                firstNameStr.toString(), lastNameStr.toString(),
                telephoneStr.toString(), aboutStr.toString()
            )
        }
    }

    /**
     * Changing user information. This method use [ApiService.changeUser] and processing response from server.
     * If response is successful, then display message "Данные изменены", else - display error
     * processing by [handleError].
     *
     * @param firstName user first name
     * @param lastName user last name
     * @param telephone user phone number
     * @param about information about user
     *
     *
     * @see [ApiService.changeUser]
     * @see [handleError]
     */
    private fun changeData(
        firstName: String,
        lastName: String,
        telephone: String,
        about: String
    ) {

        token = readToken()

        val apiService: ApiService = ApiService.create()

        apiService.changeUser(
            token.toString(), firstName,
            lastName, telephone,
            about
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({
                progressBar_user_change.visibility = ProgressBar.INVISIBLE
                toast("Данные изменены")

                changeUser(firstName + " " + lastName)

                this.finish()

                val intent = Intent(this, UserSettingsActivity::class.java)
                startActivity(intent)
            },
                { error ->
                    progressBar_user_change.visibility = ProgressBar.INVISIBLE

                    val errorString = handleError(error)

                    if (errorString == "empty body") {
                        toast("Данные изменены")
                        changeUser(firstName + " " + lastName)

                        this.finish()
                        startActivity<UserSettingsActivity>()
                    } else
                        toast(errorString)
                })
    }

    /**
     * Get user information. This method use [ApiService.getUserData] and processing response from server.
     * If response is successful, then display user information, else - display error
     * processing by [handleError].
     *
     * @param firstName user first name
     * @param lastName user last name
     * @param telNumber user phone number
     * @param about information about user
     *
     *
     * @see [ApiService.getUserData]
     * @see [handleError]
     */
    private fun userData(
        firstName: TextView,
        lastName: TextView,
        telNumber: TextView,
        about: TextView
    ) {
        token = readToken()

        val apiService: ApiService = ApiService.create()

        apiService.getUserData(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_user_change.visibility = ProgressBar.INVISIBLE
                firstName.text = result.first_name
                lastName.text = result.last_name
                telNumber.text = result.tel_number
                about.text = result.about
            }, { error ->
                progressBar_user_change.visibility = ProgressBar.INVISIBLE
                toast(handleError(error))
            })
    }

    /**
     * Reading user session_id  from SharedPreference.
     *
     * @return [String]
     */
    private fun readToken(): String? {
        var string: String? = null
        val saveToken: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)

        if (saveToken.contains("token")) {
            string = saveToken.getString("token", null)
        }

        return string
    }

    /**
     * Changing user name in SharedPreference.
     *
     *@param name user full name
     */
    private fun changeUser(name: String) {
        val save: SharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = save.edit()
        editor.remove(name)
        editor.putString("name", name)
        editor.apply()
    }
}
