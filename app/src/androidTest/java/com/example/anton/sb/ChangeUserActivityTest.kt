package com.example.anton.sb

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.ui.activities.userActivity.ChangeUserActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import junit.framework.TestCase
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ChangeUserActivityInstrumentationTest {

    val login = "qwerty@mail.ru"
    val password = "123456"
    val fistName = "Vasya"
    val lastName = "Pupkin"
    val phoneNumber = "8800553535"

    @Rule
    @JvmField
    val rule = ActivityTestRule(ChangeUserActivity::class.java)

    @Test
    fun change_user_information_view() {
        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_first_name)))
            .check(ViewAssertions.matches(ViewMatchers.withText(fistName)))
        Espresso.onView((ViewMatchers.withId(R.id.change_last_name)))
            .check(ViewAssertions.matches(ViewMatchers.withText(lastName)))
        Espresso.onView((ViewMatchers.withId(R.id.change_tel_number)))
            .check(ViewAssertions.matches(ViewMatchers.withText(phoneNumber)))
    }

    @Test
    fun empty_field_change_user_information() {
        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_first_name)))
            .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.change_user))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(R.id.change_first_name))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Поле должно быть заполнено")))
    }

    @Test
    fun change_user_information_answer_not() {
        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_first_name)))
            .perform(ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.change_user))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withText("Сохранить измнения?"))
            .inRoot(RootMatchers.isDialog())
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun change_user_information_success() {
        Intents.init()

        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_first_name)))
            .perform(ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.change_user))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).
            perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Данные измнены"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
            .check(ViewAssertions.doesNotExist())

        Intents.intended(IntentMatchers.hasComponent(UserSettingsActivity::class.java.name))

        Intents.release()
    }
}

