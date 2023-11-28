package co.edu.uniandes.miswmobile.vinilosapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Matcher
import org.hamcrest.core.AllOf
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

    @Test
    fun mainActivityAlbumsEmptyTest() {

        var itemCount: Int

        val buttonVisitor = Espresso.onView(
            ViewMatchers.withId(R.id.buttonVisitor)
        ).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        buttonVisitor.perform(ViewActions.click())
        val buttonAlbums = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.buttonAlbums),
                ViewMatchers.isDisplayed()
            )
        )
        buttonAlbums.perform(ViewActions.click())
        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.action_bar),
                ViewMatchers.withText("Álbumes"),
                ViewMatchers.isDisplayed()
            )
        )
        Espresso.onView(ViewMatchers.withId(R.id.albumsRv))
            .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.albumsRv)).check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter!!.itemCount
        }


        Espresso.onView(ViewMatchers.withId(R.id.albumsRv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
        )

        Espresso.onView(ViewMatchers.withId(R.id.albumsRv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                ViewActions.click()
            )
        )
    }

    fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}