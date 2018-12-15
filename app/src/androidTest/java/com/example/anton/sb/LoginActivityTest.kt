import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.doesNotExist
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.RootMatchers.withDecorView
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.anton.sb.R
import com.example.anton.sb.ui.activities.userActivity.LoginActivity
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class LoginActivityInstrumentationTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(LoginActivity::class.java)

    private val correctLogin = "aKirichenkov99@gmail.com"
    private val login = "werty.ru"
    private val correctPassword = "123456444"
    private val password = "1234"

    @Test
    fun log_in_error() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(correctLogin))

        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(correctPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        onView(withText("Неверный логин или пароль")).inRoot(withDecorView(not(rule.getActivity().getWindow().getDecorView())))
            .check(doesNotExist())
    }

    @Test
    fun password_error() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(correctLogin))

        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(password), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView(withId(R.id.password)).check(matches(hasErrorText("Слишком короткий пароль")))
    }

    @Test
    fun login_error() {
        Espresso.onView((withId(R.id.email)))
            .perform(ViewActions.typeText(login))

        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.typeText(correctPassword), ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.email_sign_in_button))
            .perform(ViewActions.click())

        Espresso.onView((withId(R.id.email))).check(matches(hasErrorText("Некоректный логин")))
    }
}
