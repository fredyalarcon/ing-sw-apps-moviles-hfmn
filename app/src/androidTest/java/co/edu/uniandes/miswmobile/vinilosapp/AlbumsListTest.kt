package co.edu.uniandes.miswmobile.vinilosapp

import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Matchers.greaterThan
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AlbumsListTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule(
        AccessActivity::class.java
    )

    private val idlingResource = SimpleIdlingResource("albumsRv")

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDow() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun listAlbumsAsVisitorTest() {

        var itemCount: Int = 0
        var randomItemAlbum: Int = -1

        val toolbar2 = onView(withId(R.id.toolbar2))
        toolbar2.check(matches(isDisplayed()))

        val buttonVisitor = onView(withId(R.id.buttonVisitor))
        buttonVisitor.check(matches(isDisplayed()))
        buttonVisitor.perform(click())

        var actionBar = onView(withId(R.id.my_toolbar))
        actionBar.check(matches(isDisplayed()))

        val buttonAlbums = onView(withId(R.id.buttonAlbums))
        buttonAlbums.check(matches(isDisplayed()))
        buttonAlbums.perform(click())

        actionBar = onView(withId(R.id.my_toolbar))
        actionBar.check(matches(isDisplayed()))
        val rvAlbums = onView(withId(R.id.albumsRv))
        rvAlbums.check(matches(isCompletelyDisplayed()))

        // obtener numero de items mostrados, debe ser mayor a cero
        rvAlbums.check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter!!.itemCount
        }
        assertThat(itemCount, greaterThan(0))

    }

    @Test
    fun listAlbumsAsCollectorTest() {
        var itemCount: Int = 0
        var randomItemAlbum: Int = -1

        val toolbar2 = onView(withId(R.id.toolbar2))
        toolbar2.check(matches(isDisplayed()))

        val buttonCollectors = onView(withId(R.id.buttonCollector))
        buttonCollectors.check(matches(isDisplayed()))

        var randomItem = -1
        var itemSelected: Any = ""
        onView(withId(R.id.spinner)).check { view, _ ->
            val appCompatSpinner = view as Spinner
            assertThat(appCompatSpinner.count, greaterThan(0))
            randomItem = (0 until appCompatSpinner.count).random()
            itemSelected = appCompatSpinner.getItemAtPosition(randomItem)
        }
        onView(withId(R.id.spinner)).perform(click())
        onView(withText(itemSelected.toString())).perform(click())

        var actionBar = onView(withId(R.id.my_toolbar))
        actionBar.check(matches(isDisplayed()))

        val buttonAlbums = onView(withId(R.id.buttonAlbums))
        buttonAlbums.check(matches(isDisplayed()))
        buttonAlbums.perform(click())

        actionBar = onView(withId(R.id.my_toolbar))
        actionBar.check(matches(isDisplayed()))
        val rvAlbums = onView(withId(R.id.albumsRv))
        rvAlbums.check(matches(isCompletelyDisplayed()))

        // verificar boton de accion agregar album este presente
        val buttonAddAlbum = onView(withId(R.id.add_album))
        buttonAddAlbum.check(matches(isDisplayed()))

        // obtener numero de items mostrados, debe ser mayor a cero
        rvAlbums.check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter!!.itemCount
        }
        assertThat(itemCount, greaterThan(0))
    }

}

