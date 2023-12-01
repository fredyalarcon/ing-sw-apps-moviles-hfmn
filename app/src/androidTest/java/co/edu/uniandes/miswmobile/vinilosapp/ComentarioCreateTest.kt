package co.edu.uniandes.miswmobile.vinilosapp


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
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
class ComentarioCreateTest {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(AccessActivity::class.java)

    @Test
    fun comentarioCreateTest() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        var selectionText = "Manolo Bellon"
        onView(withId(R.id.spinner)).perform(click())
        onData(allOf(`is`(Matchers.instanceOf(String::class.java)), `is`(selectionText))).perform(click())

        /*val buttonAlbums = Espresso.onView(
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
        )*/
        Thread.sleep(1000)

        val linearLayout = onView(
            allOf(
                withId(R.id.buttonAlbums),
                childAtPosition(
                    allOf(
                        withId(R.id.linearLayout),
                        childAtPosition(
                            withClassName(`is`("androidx.constraintlayout.widget.ConstraintLayout")),
                            0
                        )
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        linearLayout.perform(click())

        Thread.sleep(1000)

        val recyclerView = onView(
            allOf(
                withId(R.id.albumsRv),
                childAtPosition(
                    withClassName(`is`("android.widget.FrameLayout")),
                    1
                )
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(3, click()))

        val materialButton = onView(
            allOf(
                withId(R.id.buttonComments), withText("Comentarios"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.linearLayout2),
                        4
                    ),
                    1
                )
            )
        )
        materialButton.perform(scrollTo(), click())

        val actionMenuItemView = onView(
            allOf(
                withId(R.id.add_comentario), withContentDescription("Agregar comentario"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.my_toolbar),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        actionMenuItemView.perform(click())

        val textInputEditText = Espresso.onView(
            AllOf.allOf(
                withId(R.id.rating),
                isDisplayed()
            )
        )

        textInputEditText.perform(replaceText("3"), closeSoftKeyboard())

        val textInputEditText2 = Espresso.onView(
            AllOf.allOf(
                withId(R.id.comentario),
                isDisplayed()
            )
        )

        textInputEditText2.perform(replaceText("prueba"), closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(
                withId(R.id.saveComentario), withText("Guardar"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.FrameLayout")),
                        1
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        materialButton2.perform(click())

        val button = onView(
            allOf(
                withId(android.R.id.button1), withText("ACEPTAR"),
                withParent(withParent(IsInstanceOf.instanceOf(android.widget.ScrollView::class.java))),
                isDisplayed()
            )
        )
        button.check(matches(isDisplayed()))
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
