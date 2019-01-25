package com.example.anton.sb

import android.content.Intent
import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.model.ResultAd
import com.example.anton.sb.extensions.readUserData
import com.example.anton.sb.service.getUserAd
import com.example.anton.sb.ui.activities.adActivity.ChangeAdActivity
import com.example.anton.sb.ui.activities.adActivity.MyAdSettingsActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChangeAdActivity {

    @Rule
    @JvmField
    val rule = ActivityTestRule(ChangeAdActivity::class.java)

    @Test
    fun ad_information_view() {
        val intent = Intent()
        val userId = readUserData("id", rule.activity)!!.toLong()
        val ads: ArrayList<ResultAd> = getUserAd(userId, rule.activity)
        val ad: ResultAd = ads[0]

        intent.putExtra("adId", ad.id)
        rule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_title)))
            .check(ViewAssertions.matches(ViewMatchers.withText(ad.title)))
        Espresso.onView((ViewMatchers.withId(R.id.change_city)))
            .check(ViewAssertions.matches(ViewMatchers.withText(ad.city)))
        Espresso.onView((ViewMatchers.withId(R.id.change_description)))
            .check(ViewAssertions.matches(ViewMatchers.withText(ad.description_ad)))
    }

    @Test
    fun change_ad() {
        Intents.init()
        val intent = Intent()
        val userId = readUserData("id", rule.activity)!!.toLong()
        val ads: ArrayList<ResultAd> = getUserAd(userId, rule.activity)
        val ad: ResultAd = ads[0]

        intent.putExtra("adId", ad.id)
        rule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_title)))
            .perform(ViewActions.clearText(), ViewActions.typeText("Building"), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.change_ad))
            .perform(ViewActions.click())

        Espresso.onView(ViewMatchers.withId(android.R.id.button1)).perform(ViewActions.click())

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Объявление измнено"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
            .check(ViewAssertions.doesNotExist())

        Intents.intended(IntentMatchers.hasComponent(MyAdSettingsActivity::class.java.name))

        Espresso.onView(ViewMatchers.withId(R.id.title_ad_settings))
            .check(ViewAssertions.matches(ViewMatchers.withText("Building")))

        Intents.release()
    }

    @Test
    fun check_empty_field() {
        val intent = Intent()
        val userId = readUserData("id", rule.activity)!!.toLong()
        val ads: ArrayList<ResultAd> = getUserAd(userId, rule.activity)
        val ad: ResultAd = ads[0]

        intent.putExtra("adId", ad.id)
        rule.launchActivity(intent)

        Thread.sleep(2000)

        Espresso.onView((ViewMatchers.withId(R.id.change_title)))
            .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())

        Espresso.onView(ViewMatchers.withId(R.id.change_ad))
            .perform(ViewActions.click())

        Espresso.onView((ViewMatchers.withId(R.id.change_title)))
            .check(ViewAssertions.matches(ViewMatchers.hasErrorText("Поле должно быть заполнено")))
    }
}