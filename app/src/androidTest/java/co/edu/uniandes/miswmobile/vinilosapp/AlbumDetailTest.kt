package co.edu.uniandes.miswmobile.vinilosapp

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
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
    fun mainActivityAlbumDetailTest() {
        val buttonVisitor = Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.buttonVisitor),
                ViewMatchers.isDisplayed()
            )
        )
        buttonVisitor.perform(ViewActions.click())

        val buttonAlbums = Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.buttonAlbums),
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

        val rvAlbumes = Espresso.onView(
            allOf(
                ViewMatchers.withId(R.id.albumsRv),
                ViewMatchers.isDisplayed()
            )
        )

        rvAlbumes.perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                0,
                ViewActions.click()
            )
        )
        // campo album cover - imagen caratula miniatura
        Espresso.onView(ViewMatchers.withId(R.id.imageView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // campo album cover - imagen caratula
        Espresso.onView(ViewMatchers.withId(R.id.imageView2))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // campo album name
        Espresso.onView(ViewMatchers.withId(R.id.textView6))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // campo album genero
        Espresso.onView(ViewMatchers.withId(R.id.textView5))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // campo album fecha lanzamiento
        Espresso.onView(ViewMatchers.withId(R.id.textView7))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // campo album sello discografico
        Espresso.onView(ViewMatchers.withId(R.id.textView12))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // campo album descripcion
        Espresso.onView(ViewMatchers.withId(R.id.textView9))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // boton canciones
        Espresso.onView(ViewMatchers.withId(R.id.buttonSongs))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        // boton comentarios
        Espresso.onView(ViewMatchers.withId(R.id.buttonComments))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}