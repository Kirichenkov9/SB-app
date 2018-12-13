package com.example.anton.sb.ui.activities.userActivity

import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.anton.sb.R
import com.example.anton.sb.extensions.handleError
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.extensions.removeUserData
import com.example.anton.sb.service.ApiService
import com.example.anton.sb.service.delete
import com.example.anton.sb.service.logout
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.adActivity.AddAdActivity
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_user_settings.*
import kotlinx.android.synthetic.main.app_bar_other.*
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * A screen user settings
 */
class UserSettingsActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    /**
     * @property token
     * @property keyToken
     * @property name
     * @property mail
     */

    /**
     * saved session_id
     */
    private var token: String? = null

    /**
     * token key for SharedPreference
     */
    private val keyToken = "token"

    /**
     * username key for SharedPreference
     */
    private val name: String = "name"

    /**
     * email key for SharedPreference
     */
    private val mail: String = "email"

    /**
     * @suppress
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_settings)
        setSupportActionBar(toolbar_settings)

        token = readUserData(keyToken, this)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout_settings, toolbar_settings,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout_settings.addDrawerListener(toggle)
        toggle.syncState()

        nav_view_settings.setNavigationItemSelectedListener(this)
        val header = find<NavigationView>(R.id.nav_view_settings).getHeaderView(0)

        val nameUser = header.find<TextView>(R.id.user_first_name)
        val userEmail = header.find<TextView>(R.id.mail)
        val navViewHeader = header.find<View>(R.id.nav_view_header)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }

        nameUser.text = readUserData(name, this)
        userEmail.text = readUserData(mail, this)

        val firstName = find<TextView>(R.id.first_user_name)
        val lastName = find<TextView>(R.id.last_user_name)
        val email = find<TextView>(R.id.user_email)
        val telephone = find<TextView>(R.id.user_phone_number)
        val about = find<TextView>(R.id.user_about)

        userData(firstName, lastName, email, telephone, about)
        progressBar_user_settings.visibility = ProgressBar.VISIBLE

        firstName.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        lastName.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        telephone.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        about.setOnClickListener {
            startActivity<ChangeUserActivity>()
        }

        exit_account.setOnClickListener {
            logout(token, progressBar_user_settings, this)
            progressBar_user_settings.visibility = ProgressBar.VISIBLE
        }

        delete_account.setOnClickListener {
            delete(token, progressBar_user_settings, this)
            progressBar_user_settings.visibility = ProgressBar.VISIBLE
        }
    }

    /**
     * @suppress
     */
    override fun onBackPressed() {
        if (drawer_layout_settings.isDrawerOpen(GravityCompat.START)) {
            drawer_layout_settings.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * @suppress
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_ad -> {
                startActivity<AddAdActivity>()
            }
            R.id.account -> {
                startActivity<UserSettingsActivity>()
            }
            R.id.search -> {
                startActivity<MainActivity>()
            }
            R.id.my_ads -> {
                startActivity<MyAdsActivity>()
            }
            R.id.about_app -> {
                startActivity<AboutApp>()
            }
        }
        drawer_layout_settings.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Get user information. This method use [ApiService.getUserData] and processing response from server.
     * If response is successful, then display user information, else - display error
     * processing by [handleError].
     *
     * @param firstName user first name
     * @param lastName user last name
     * @param telephone user phone number
     * @param about information about user
     *
     *
     * @see [ApiService.getUserData]
     * @see [handleError]
     */
    private fun userData(
        firstName: TextView,
        lastName: TextView,
        email: TextView,
        telephone: TextView,
        about: TextView
    ) {

        val apiService: ApiService = ApiService.create()

        apiService.getUserData(token.toString())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE

                firstName.text = result.first_name
                lastName.text = result.last_name
                email.text = result.email
                telephone.text = result.tel_number
                about.text = result.about
            }, { error ->
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE

                val errorStr = handleError(error)

                if (errorStr == "empty body") {
                    toast("Объявление удалено")

                    startActivity<MyAdsActivity>()
                } else if (errorStr == "Что-то пошло не так... Попробуйте войти в аккаунт заново") {
                    removeUserData(this)
                    startActivity<LoginActivity>()
                } else
                    toast(errorStr)
            })
    }
}
