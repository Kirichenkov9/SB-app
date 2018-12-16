package com.example.anton.sb

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import junit.framework.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    val login = "qwerty@mail.ru"
    val password = "123456"
    val fistName = "Vasya"
    val lastName = "Pupkin"
    val phoneNumber = "8800553535"

    @Rule
    @JvmField
    val rule = ActivityTestRule(LoginActivity::class.java)

    private val invalidLogin = "werty.ru"
    private val invalidPassword = "1223"

    @Test
    fun log_in_error() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(login))
        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Неверный логин или пароль"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
            .check(ViewAssertions.doesNotExist())
    }

    @Test
    fun password_error() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(login))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(invalidPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Слишком короткий пароль")))
    }

    @Test
    fun login_error() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(invalidLogin))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Некоректный логин")))
    }

    @Test
    fun login_success() {
        Espresso.onView((ViewMatchers.withId(R.id.email)))
            .perform(ViewActions.typeText(login))

        Espresso.onView(ViewMatchers.withId(R.id.password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Thread.sleep(2000)
        TestCase.assertTrue(rule.activity.isFinishing)
    }
}