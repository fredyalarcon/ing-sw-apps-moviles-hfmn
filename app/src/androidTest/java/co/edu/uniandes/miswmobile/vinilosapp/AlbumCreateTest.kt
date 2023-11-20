package co.edu.uniandes.miswmobile.vinilosapp


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.AllOf
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AlbumCreateTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AccessActivity::class.java)

    @Test
    fun albumCreateTest() {
        val buttonVisitor = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.buttonVisitor),
                ViewMatchers.isDisplayed()
            )
        )
        buttonVisitor.perform(ViewActions.click())

        val buttonArtists = Espresso.onView(
            AllOf.allOf(
                ViewMatchers.withId(R.id.buttonAlbums),
                ViewMatchers.isDisplayed()
            )
        )
        buttonArtists.perform(ViewActions.click())

        val actionMenuItemView = Espresso.onView(
            AllOf.allOf(
                withId(R.id.add_album), withContentDescription("Agregar album"),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textInputEditText = Espresso.onView(
            AllOf.allOf(
                withId(R.id.nombre),
                isDisplayed()
            )
        )


        textInputEditText.perform(replaceText("Album"), closeSoftKeyboard())

        val textInputEditText2 = Espresso.onView(
            AllOf.allOf(
                withId(R.id.cover),
                isDisplayed()
            )
        )

        textInputEditText2.perform(scrollTo(), replaceText("c"), closeSoftKeyboard())

        val textInputEditText3 = Espresso.onView(
            AllOf.allOf(
                withId(R.id.createDate),
                isDisplayed()
            )
        )
        textInputEditText3.perform(scrollTo(), replaceText("12/08/1989"), closeSoftKeyboard())

        val textInputEditText4 = Espresso.onView(
            AllOf.allOf(
                withId(R.id.genre),
                isDisplayed()
            )
        )
        textInputEditText4.perform(scrollTo(), replaceText("Rock"), closeSoftKeyboard())

        val textInputEditText5 = Espresso.onView(
            AllOf.allOf(
                withId(R.id.description),
                isDisplayed()
            )
        )
        textInputEditText5.perform(scrollTo(), replaceText("Dico 10"), closeSoftKeyboard())


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
