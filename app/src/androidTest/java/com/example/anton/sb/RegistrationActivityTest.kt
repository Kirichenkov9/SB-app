import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.hasErrorText
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.R
import com.example.anton.sb.ui.activities.userActivity.RegistrationActivity
import junit.framework.TestCase.assertTrue
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegistrationActivityTest {

    val login = "qwerty@mail.ru"
    val password = "123456"
    val fistName = "Vasya"
    val lastName = "Pupkin"
    val phoneNumber = "8800553535"

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

        Thread.sleep(2000)

        Espresso.onView(ViewMatchers.withText("Пользователь зарегистрирован"))
            .inRoot(RootMatchers.withDecorView(Matchers.not(rule.activity.window.decorView)))
            .check(ViewAssertions.doesNotExist())

        assertTrue(rule.activity.isFinishing)
    }
}
