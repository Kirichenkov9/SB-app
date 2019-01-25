package com.example.anton.sb

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.model.ResultAd
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.getUserAd
import com.example.anton.sb.ui.activities.adActivity.MyAdSettingsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import junit.framework.TestCase
import org.hamcrest.Matchers

@RunWith(AndroidJUnit4::class)
class MyAdSettingsActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MyAdSettingsActivity::class.java)

    @Test
    fun ad_view() {
        val intent = Intent()
        val userId = readUserData("id", rule.activity)!!.toLong()
        val ads: ArrayList<ResultAd> = getUserAd(userId, rule.activity)
        val ad: ResultAd = ads[0]

        intent.putExtra("adId", ad.id)
        rule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.title_ad_settings)))
            .check(ViewAssertions.matches(ViewMatchers.withText(ad.title)))
        Espresso.onView((ViewMatchers.withId(R.id.city_ad_settings)))
            .check(ViewAssertions.matches(ViewMatchers.withText(ad.city)))
        Espresso.onView((ViewMatchers.withId(R.id.about_ad_settings)))
            .check(ViewAssertions.matches(ViewMatchers.withText(ad.description_ad)))
    }

    @Test
    fun delete_ad() {
        val intent = Intent()
        val userId = readUserData("id", rule.activity)!!.toLong()
        val ads: ArrayList<ResultAd> = getUserAd(userId, rule.activity)
        val ad: ResultAd = ads[0]

        intent.putExtra("adId", ad.id)
        rule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withId(R.id.delete_ad))
            .perform(ViewActions.scrollTo(), ViewActions.click())

        Espresso.onView(ViewMatchers.withId(android.R.id.button1))
            .perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Объявление удалено"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
            .check(ViewAssertions.doesNotExist())

        TestCase.assertTrue(rule.activity.isFinishing)
    }
}