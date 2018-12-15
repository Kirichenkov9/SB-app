package com.example.anton.sb

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.R
import com.example.anton.sb.ui.activities.userActivity.RegistrationActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RegistrationActivityInstrumentationTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(RegistrationActivity::class.java)

    @Test
    fun registration_repeat_password_empty_field() {
        Espresso.onView(withId(R.id.button_registration))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.repeat_password_registration)).check(matches(hasErrorText("Поле должно быть заполнено")))
    }
}
