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
import com.example.anton.sb.ui.activities.adActivity.AddAdActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdsActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AddAdActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(AddAdActivity::class.java)

    @Test
    fun add_ad_success() {
        Intents.init()
        Espresso.onView(ViewMatchers.withId(R.id.name))
            .perform(ViewActions.typeText("Build"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.about))
            .perform(ViewActions.typeText("Building"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.city))
            .perform(ViewActions.typeText("Moscow"), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.button_ad_add))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Объявление добавлено"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Intents.intended(IntentMatchers.hasComponent(MyAdsActivity::class.java.name))

        Intents.release()
    }

    @Test
    fun error_add_ad() {
        Espresso.onView(ViewMatchers.withId(R.id.name))
            .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.button_ad_add))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.name))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Поле должно быть заполнено")))
        Espresso.onView(ViewMatchers.withId(R.id.about))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Поле должно быть заполнено")))
        Espresso.onView(ViewMatchers.withId(R.id.city))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Поле должно быть заполнено")))
    }
}
