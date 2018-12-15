import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.RootMatchers.isDialog
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.R
import com.example.anton.sb.ui.activities.userActivity.ChangeUserActivity
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import com.example.anton.sb.ui.activities.userActivity.RegistrationActivity
import com.example.anton.sb.ui.activities.userActivity.UserSettingsActivity
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

val login = "qwerty@mail.ru"
val password = "123456"
val fistName = "Vasya"
val lastName = "Pupkin"
val phoneNumber = "8800553535"

@RunWith(AndroidJUnit4::class)
class RegistrationActivityInstrumentationTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(RegistrationActivity::class.java)

    @Test
    fun repeat_password_empty_field() {
        Espresso.onView(withId(R.id.repeat_password_registration)).perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.button_registration))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.repeat_password_registration))
            .check(matches(hasErrorText("Поле должно быть заполнено")))
    }

    @Test
    fun different_password() {
        val pass1 = "1234567890"
        val pass2 = "123456"

        Espresso.onView((withId(R.id.password_registration)))
            .perform(ViewActions.typeText(pass1), ViewActions.closeSoftKeyboard())
        Espresso.onView((withId(R.id.repeat_password_registration)))
            .perform(ViewActions.typeText(pass2), ViewActions.closeSoftKeyboard())

        Espresso.onView(withId(R.id.button_registration))
            .perform(ViewActions.click())
        Espresso.onView(withId(R.id.password_registration))
            .check(matches(hasErrorText("Пароли не совпадают")))
    }

    @Test
    fun success_registration() {

        Espresso.onView(withId(R.id.first_name_registration))
            .perform(ViewActions.typeText(fistName), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.last_name_registration))
            .perform(ViewActions.typeText(lastName), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.phone_number_registration))
            .perform(ViewActions.typeText(phoneNumber), ViewActions.closeSoftKeyboard())
        Espresso.onView((withId(R.id.email_registration)))
            .perform(ViewActions.typeText(login), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.password_registration))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView((withId(R.id.repeat_password_registration)))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.button_registration))
            .perform(ViewActions.click())

        Thread.sleep(2000) // ¯\_(ツ)_/¯
        assertTrue(rule.activity.isFinishing)
    }
}

@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentationTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(LoginActivity::class.java)

    private val invalidLogin = "werty.ru"
    private val invalidPassword = "1223"

    @Test
    fun log_in_error() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(login))
        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText("1234567"), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        onView(withText("Неверный логин или пароль"))
            .inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
            .check(doesNotExist())
    }

    @Test
    fun password_error() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(login))

        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(invalidPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.password))
            .check(matches(hasErrorText("Слишком короткий пароль")))
    }

    @Test
    fun login_error() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(invalidLogin))

        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView((withId(R.id.email)))
            .check(matches(hasErrorText("Некоректный логин")))
    }

    @Test
    fun login_success() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(login))

        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Thread.sleep(2000) // ¯\_(ツ)_/¯
        assertTrue(rule.activity.isFinishing)
    }
}

@RunWith(AndroidJUnit4::class)
class UserSettingsActivityInstrumentationTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(UserSettingsActivity::class.java)

    @Test
    fun user_information_view() {
        Thread.sleep(2000) // ¯\_(ツ)_/¯

        Espresso.onView((withId(R.id.first_user_name)))
            .check(matches(withText(fistName)))
        Espresso.onView((withId(R.id.last_user_name)))
            .check(matches(withText(lastName)))
        Espresso.onView((withId(R.id.user_phone_number)))
            .check(matches(withText(phoneNumber)))
        Espresso.onView((withId(R.id.user_email)))
            .check(matches(withText(login)))
    }

    @Test
    fun user_change_activity() {
        Intents.init()
        Thread.sleep(2000) // ¯\_(ツ)_/¯
        Espresso.onView((withId(R.id.first_user_name)))
            .perform(ViewActions.click())

        intended(hasComponent(ChangeUserActivity::class.java.name))

        Intents.release()
    }
}

@RunWith(AndroidJUnit4::class)
class ChangeUserActivityInstrumentationTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(ChangeUserActivity::class.java)

    @Test
    fun change_user_information_view() {
        Thread.sleep(2000) // ¯\_(ツ)_/¯

        Espresso.onView((withId(R.id.change_first_name)))
            .check(matches(withText(fistName)))
        Espresso.onView((withId(R.id.change_last_name)))
            .check(matches(withText(lastName)))
        Espresso.onView((withId(R.id.change_tel_number)))
            .check(matches(withText(phoneNumber)))
    }

    @Test
    fun empty_field_change_user_information() {
        Thread.sleep(2000) // ¯\_(ツ)_/¯

        Espresso.onView((withId(R.id.change_first_name)))
            .perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())

        Espresso.onView(withId(R.id.change_user))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.change_first_name))
            .check(matches(hasErrorText("Поле должно быть заполнено")))
    }

    @Test
    fun change_user_information_answer_not() {
        Thread.sleep(2000) // ¯\_(ツ)_/¯

        Espresso.onView((withId(R.id.change_first_name)))
            .perform(ViewActions.closeSoftKeyboard())

        Espresso.onView(withId(R.id.change_user))
            .perform(ViewActions.click())

        onView(withText("Сохранить измнения?"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun change_user_information_success() {
        Thread.sleep(2000) // ¯\_(ツ)_/¯

        Espresso.onView((withId(R.id.change_first_name)))
            .perform(ViewActions.closeSoftKeyboard())

        Espresso.onView(withId(R.id.change_user))
            .perform(ViewActions.click())

        onView(withId(android.R.id.button1)).
            perform(ViewActions.click())

        Thread.sleep(2000) // ¯\_(ツ)_/¯
        assertTrue(rule.activity.isFinishing)
    }
}


