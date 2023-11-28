package co.edu.uniandes.miswmobile.vinilosapp

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import co.edu.uniandes.miswmobile.vinilosapp.ui.MainActivity
import org.hamcrest.core.AllOf.allOf
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
    fun mainActivityAlbumsEmptyTest() {

        var itemCount: Int

        val buttonVisitor = onView(withId(R.id.buttonVisitor))
        buttonVisitor.check(matches(isDisplayed()))
        buttonVisitor.perform(click())

        val buttonAlbums = onView(withId(R.id.buttonAlbums))
        buttonAlbums.check(matches(isDisplayed()))
        buttonAlbums.perform(click())
        //val actionBar = onView(withId(R.id.action_bar))
        //actionBar.check(matches(allOf(withText("Álbumes"), isDisplayed())))

        val rvAlbums = onView(withId(R.id.albumsRv))
        rvAlbums.check(matches(isCompletelyDisplayed()))
        rvAlbums.check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter!!.itemCount
        }

        rvAlbums.perform(
            RecyclerViewActions.actionOnItemAtPosition<ViewHolder>(
                0,
                click()
            )
        )
    }
}

