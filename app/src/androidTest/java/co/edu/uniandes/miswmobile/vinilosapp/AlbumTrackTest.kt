package co.edu.uniandes.miswmobile.vinilosapp

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumTrackTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(
        AccessActivity::class.java
    )

    @Test
    fun mainActivityAlbumTrack() {

        val buttonVisitor = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.buttonVisitor),
                ViewMatchers.isDisplayed()
            )
        )
        buttonVisitor.perform(ViewActions.click())

        val buttonAlbums = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.buttonAlbums),
                ViewMatchers.isDisplayed()
            )
        )
        buttonAlbums.perform(ViewActions.click())

        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.action_bar),
                ViewMatchers.withText("Álbumes"),
                ViewMatchers.isDisplayed()
            )
        )

        val rvAlbumes = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.albumsRv),
                ViewMatchers.isDisplayed()
            )
        )

        rvAlbumes.perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
    }
}