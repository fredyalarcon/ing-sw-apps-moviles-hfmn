package co.edu.uniandes.miswmobile.vinilosapp

import android.provider.ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.junit.Rule
import org.junit.Test


class AddPerformerAlbumTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AccessActivity::class.java)

    @Test
    fun albumCreateTest() {
        var selectionText = "Manolo Bellon"
        Espresso.onView(ViewMatchers.withId(R.id.spinner)).perform(ViewActions.click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(Matchers.instanceOf(String::class.java)),
                Matchers.`is`(selectionText)
            )
        ).perform(ViewActions.click())

        val buttonArtists = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.buttonArtists),
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

        Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.artistsRv),
                ViewMatchers.isCompletelyDisplayed()
            )
        ).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.click()))

        val buttonAlbums = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.buttonAlbums),
                ViewMatchers.isDisplayed()
            )
        )
        buttonAlbums.perform(ViewActions.click())

        onView(ViewMatchers.isRoot()).perform(waitFor(5000))

        val actionMenuItemView = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.add_album_to_artist),
                ViewMatchers.isDisplayed()
            )
        )
        actionMenuItemView.perform(ViewActions.click())



        val autoCompleteAlbum = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.album),
                ViewMatchers.isDisplayed()
            )
        )
        autoCompleteAlbum.perform(
            ViewActions.click()
        )
        onData(Matchers.anything())
            .atPosition(0)
            .inRoot(RootMatchers.isPlatformPopup())
            .perform(ViewActions.click());


//        val buttonSave = Espresso.onView(
//            AllOf.allOf(
//                ViewMatchers.withId(R.id.saveAlbum),
//                ViewMatchers.isDisplayed()
//            )
//        )
//        buttonSave.perform(ViewActions.click())


    }

    private fun waitFor(delay: Long): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $delay milliseconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
