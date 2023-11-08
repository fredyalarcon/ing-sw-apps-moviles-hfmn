package co.edu.uniandes.miswmobile.vinilosapp

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumDetailTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(
        AccessActivity::class.java
    )

    @Test
    fun mainActivityAlbumDetailTest(){
        val buttonVisitor = Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.buttonVisitor),
                ViewMatchers.withText("Visitante"),
                ViewMatchers.isDisplayed()
            )
        )
        buttonVisitor.perform(ViewActions.click())

        val buttonAlbums = Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.buttonAlbums),
                ViewMatchers.withText("Álbumes"),
                ViewMatchers.isDisplayed()
            )
        )
        buttonAlbums.perform(ViewActions.click())

        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.action_bar),
                ViewMatchers.withText("Álbumes"),
                ViewMatchers.isDisplayed()
            )
        )

        Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.albumsRv),
                ViewMatchers.isDisplayed()
            )
        )
    }
}