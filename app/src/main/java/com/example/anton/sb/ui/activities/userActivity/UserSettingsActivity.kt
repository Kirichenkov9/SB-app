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
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.delete
import com.example.anton.sb.service.logout
import com.example.anton.sb.service.userData
import com.example.anton.sb.ui.activities.AboutApp
import com.example.anton.sb.ui.activities.adActivity.AddAdActivity
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdsActivity
import kotlinx.android.synthetic.main.activity_user_settings.*
import kotlinx.android.synthetic.main.app_bar_other.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar

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

    private lateinit var header: View
    private lateinit var nameUser: TextView
    private lateinit var userEmail: TextView
    private lateinit var navViewHeader: View
    private lateinit var firstName: TextView
    private lateinit var lastName: TextView
    private lateinit var email: TextView
    private lateinit var telephone: TextView
    private lateinit var about: TextView
    private lateinit var time: TextView


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
        header = find<NavigationView>(R.id.nav_view_settings).getHeaderView(0)

        nameUser = header.find(R.id.user_first_name)
        userEmail = header.find(R.id.mail)
        navViewHeader = header.find(R.id.nav_view_header)

        navViewHeader.setOnClickListener {
            if (token.isNullOrEmpty()) {
                startActivity<LoginActivity>()
            } else {
                startActivity<UserSettingsActivity>()
            }
        }

        nameUser.text = readUserData(name, this)
        userEmail.text = readUserData(mail, this)

        firstName = find(R.id.first_user_name)
        lastName = find(R.id.last_user_name)
        email = find(R.id.user_email)
        telephone = find(R.id.user_phone_number)
        about = find(R.id.user_about)
        time = find(R.id.my_reg_time)

        doAsync {
            val user = userData(token.toString(), this@UserSettingsActivity)
            uiThread {
                progressBar_user_settings.visibility = ProgressBar.INVISIBLE
                if (user != null) {
                    firstName.text = user.first_name
                    lastName.text = user.last_name
                    email.text = user.email
                    telephone.text = user.tel_number
                    about.text = user.about
                    time.text = user.reg_time.toLocaleString()
                }
            }
        }
        progressBar_user_settings.visibility = ProgressBar.VISIBLE

        firstName.setOnClickListener {
            if (!firstName.text.isNullOrEmpty())
                startActivity<ChangeUserActivity>()
            else if (progressBar_user_settings.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }

        lastName.setOnClickListener {
            if (!firstName.text.isNullOrEmpty())
                startActivity<ChangeUserActivity>()
            else if (progressBar_user_settings.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }

        telephone.setOnClickListener {
            if (!firstName.text.isNullOrEmpty())
                startActivity<ChangeUserActivity>()
            else if (progressBar_user_settings.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }

        about.setOnClickListener {
            if (!firstName.text.isNullOrEmpty())
                startActivity<ChangeUserActivity>()
            else if (progressBar_user_settings.visibility == ProgressBar.INVISIBLE)
                it.snackbar("Не удалось загрузить данные")
        }

        exit_account.setOnClickListener {
            logOutAlert(token, progressBar_user_settings)
        }

        delete_account.setOnClickListener {
            deleteAlert(token, progressBar_user_settings)
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
     * Display alertDialog. If user answer "Да", then called [logout]
     * and user log out, else nothing happens.
     *
     * @param token user session_id
     * @param progressBar_user_settings ProgressBar
     *
     * @see logout
     */
    private fun logOutAlert(token: String?, progressBar_user_settings: ProgressBar) {
        alert(message = "Вы уверены, что хотите выйти из аккаунта?") {
            positiveButton("Да") {
                logout(token, progressBar_user_settings, this@UserSettingsActivity)
                progressBar_user_settings.visibility = ProgressBar.VISIBLE
            }
            negativeButton("Нет") {}
        }.show()
    }

    /**
     * Display alertDialog. If user answer "Да", then called [delete]
     * and user is delete, else nothing happens.
     *
     * @param token user session_id
     * @param progressBar_user_settings ProgressBar
     *
     * @see delete
     */
    private fun deleteAlert(token: String?, progressBar_user_settings: ProgressBar) {
        alert(message = "Вы уверены, что хотите удалить аккаунт?") {
            positiveButton("Да") {
                delete(token, progressBar_user_settings, this@UserSettingsActivity)
                progressBar_user_settings.visibility = ProgressBar.VISIBLE
            }
            negativeButton("Нет") {}
        }.show()
    }
}
