package co.edu.uniandes.miswmobile.vinilosapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerformerListTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(
        AccessActivity::class.java
    )

    @Test
    fun mainActivityArtistsEmptyTest() {
        val buttonVisitor = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.buttonVisitor),
                ViewMatchers.isCompletelyDisplayed()
            )
        )
        buttonVisitor.perform(ViewActions.click())
        val buttonArtists = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.buttonArtists),
                ViewMatchers.isCompletelyDisplayed()
            )
        )
        buttonArtists.perform(ViewActions.click())
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.action_bar),
                ViewMatchers.withText("Artistas"),
                ViewMatchers.isCompletelyDisplayed()
            )
        )
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.artistsRv),
                ViewMatchers.isCompletelyDisplayed()
            )
        )
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}