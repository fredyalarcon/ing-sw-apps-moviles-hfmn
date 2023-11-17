package co.edu.uniandes.miswmobile.vinilosapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PerformerDetailTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(
        AccessActivity::class.java
    )

    @Test
    fun mainActivityPerformerDetailTest()
    {
        val buttonVisitor = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.buttonVisitor),
                ViewMatchers.withText("Visitante"),
                ViewMatchers.isDisplayed()
            )
        )
        buttonVisitor.perform(ViewActions.click())

        val buttonArtists = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.buttonArtists),
                ViewMatchers.withText("Artistas"),
                ViewMatchers.isDisplayed()
            )
        )
        buttonArtists.perform(ViewActions.click())

        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.action_bar),
                ViewMatchers.withText("Artistas"),
                ViewMatchers.isDisplayed()
            )
        )

        val rvArtists = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.artistsRv),
                ViewMatchers.isDisplayed()
            )
        )

        val recyclerView = rvArtists as RecyclerView

        MatcherAssert.assertThat(recyclerView.adapter!!.itemCount, Matchers.greaterThan(0) )
        rvArtists.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
    }
}