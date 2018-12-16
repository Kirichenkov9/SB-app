package com.example.anton.sb

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.data.ResultAd
import com.example.anton.sb.extensions.updateDataList
import com.example.anton.sb.ui.activities.adActivity.AdViewActivity
import com.example.anton.sb.ui.activities.adActivity.UserAdActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AdViewActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(AdViewActivity::class.java)

    @Test
    fun check_ad_view() {
        Intents.init()

        val intent = Intent()
        val ads: ArrayList<ResultAd> = ArrayList()
        updateDataList(ads, rule.activity)

        if (ads.isNotEmpty()) {
            val ad = ads[0]
            intent.putExtra("adId", ad.id)
            rule.launchActivity(intent)

            Thread.sleep(2000)

            Espresso.onView((ViewMatchers.withId(R.id.title_ad)))
                .check(ViewAssertions.matches(ViewMatchers.withText(ad.title)))
            Espresso.onView((ViewMatchers.withId(R.id.city_ad)))
                .check(ViewAssertions.matches(ViewMatchers.withText(ad.city)))

            Espresso.onView((ViewMatchers.withId(R.id.about_ad)))
                .check(ViewAssertions.matches(ViewMatchers.withText(ad.description_ad)))

            Espresso.onView(ViewMatchers.withId(R.id.go_to_user))
                .perform(ViewActions.scrollTo(), ViewActions.click())

            Intents.intended(IntentMatchers.hasComponent(UserAdActivity::class.java.name))
        }
            Intents.release()
    }
}
