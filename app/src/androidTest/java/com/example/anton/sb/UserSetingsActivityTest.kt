package com.example.anton.sb

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.ui.activities.userActivity.ChangeUserActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UserSettingsActivityInstrumentationTest {

    val login = "qwerty@mail.ru"
    val password = "123456"
    val fistName = "Vasya"
    val lastName = "Pupkin"
    val phoneNumber = "8800553535"

    @Rule
    @JvmField
    val rule = ActivityTestRule(UserSettingsActivity::class.java)

    @Test
    fun user_information_view() {
        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.first_user_name)))
            .check(ViewAssertions.matches(ViewMatchers.withText(fistName)))
        Espresso.onView((ViewMatchers.withId(R.id.last_user_name)))
            .check(ViewAssertions.matches(ViewMatchers.withText(lastName)))
        Espresso.onView((ViewMatchers.withId(R.id.user_phone_number)))
            .check(ViewAssertions.matches(ViewMatchers.withText(phoneNumber)))
        Espresso.onView((ViewMatchers.withId(R.id.user_email)))
            .check(ViewAssertions.matches(ViewMatchers.withText(login)))
    }

    @Test
    fun user_change_activity() {
        Intents.init()
        Thread.sleep(2000)
        Espresso.onView((ViewMatchers.withId(R.id.first_user_name)))
            .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(ChangeUserActivity::class.java.name))

        Intents.release()
    }
}
