package com.example.anton.sb

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.model.ResultAd
import com.example.anton.sb.extensions.updateSearchList
import com.example.anton.sb.ui.activities.adActivity.AdViewActivity
import com.example.anton.sb.ui.activities.adActivity.SearchActivity
import com.example.anton.sb.ui.adapters.SearchAdapter
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(SearchActivity::class.java)

    @Test
    fun searching() {
        Intents.init()

        val request: String = "build"
        val ads: ArrayList<ResultAd> = ArrayList()
        updateSearchList(ads, request, rule.activity)

        Espresso.onView(ViewMatchers.withId(R.id.search_edit_frame))
            .perform(ViewActions.typeText(request), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.start_search))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        if (ads.isNotEmpty()) {
            Espresso.onView(ViewMatchers.withId(R.id.search_list_ad))
                .perform(RecyclerViewActions.actionOnItemAtPosition<SearchAdapter.ViewHolder>(0, ViewActions.click()))

            Thread.sleep(2000)

            Espresso.onView(ViewMatchers.withId(R.id.title_ad))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

            Intents.intended(IntentMatchers.hasComponent(AdViewActivity::class.java.name))
        } else {
            Espresso.onView(ViewMatchers.withText("Объявления не найдены"))
                .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }

        Intents.release()
    }
}
