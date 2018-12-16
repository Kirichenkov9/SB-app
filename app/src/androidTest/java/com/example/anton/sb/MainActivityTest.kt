package com.example.anton.sb

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.ui.activities.adActivity.AdViewActivity
import com.example.anton.sb.ui.activities.adActivity.MainActivity
import com.example.anton.sb.ui.activities.adActivity.SearchActivity
import com.example.anton.sb.ui.adapters.MainAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun click_child() {
        Intents.init()

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.ad_list))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Espresso.onView(ViewMatchers.withId(R.id.ad_list))
            .perform(RecyclerViewActions.actionOnItemAtPosition<MainAdapter.ViewHolder>(0, ViewActions.click()))

        Espresso.onView(ViewMatchers.withId(R.id.title_ad))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        Intents.intended(IntentMatchers.hasComponent(AdViewActivity::class.java.name))

        Intents.release()
    }

    @Test
    fun start_searching() {
        Intents.init()

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.search_button))
            .perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(SearchActivity::class.java.name))

        Intents.release()
    }
}