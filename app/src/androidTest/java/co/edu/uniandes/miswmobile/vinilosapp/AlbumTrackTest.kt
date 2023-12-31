package co.edu.uniandes.miswmobile.vinilosapp

import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity
import org.hamcrest.Matchers.greaterThan
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
    fun ListAlbumTrackAsVisitorTest() {

        var itemCount = 0
        var randomItemAlbum = -1

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
            randomItemAlbum = (0 until itemCount).random()
        }
        assertThat(itemCount, greaterThan(0))

        rvAlbums.perform(scrollToPosition<ViewHolder>(randomItemAlbum))
        rvAlbums.perform(actionOnItemAtPosition<ViewHolder>(randomItemAlbum, click()))

        // boton canciones
        val buttonSongs = onView(withId(R.id.buttonSongs))
        buttonSongs.perform(scrollTo())
        buttonSongs.check(matches(isDisplayed()))
        buttonSongs.perform(click())

        val rvAlbumTracks = onView(withId(R.id.albumsTrackRv))
        rvAlbumTracks.check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun AddAlbumTrackAsCollectorTest() {

        var itemCount = 0
        var randomItemAlbum = -1

        val toolbar2 = onView(withId(R.id.toolbar2))
        toolbar2.check(matches(isDisplayed()))

        val buttonCollectors = onView(withId(R.id.buttonCollector))
        buttonCollectors.check(matches(isDisplayed()))

        var randomItem: Int
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

        // obtener numero de items mostrados, debe ser mayor a cero
        rvAlbums.check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter!!.itemCount
            randomItemAlbum = (0 until itemCount).random()
        }
        assertThat(itemCount, greaterThan(0))

        rvAlbums.perform(scrollToPosition<ViewHolder>(randomItemAlbum))
        rvAlbums.perform(actionOnItemAtPosition<ViewHolder>(randomItemAlbum, click()))

        // boton canciones
        val buttonSongs = onView(withId(R.id.buttonSongs))
        buttonSongs.perform(scrollTo())
        buttonSongs.check(matches(isDisplayed()))
        buttonSongs.perform(click())

        val rvAlbumTracks = onView(withId(R.id.albumsTrackRv))
        rvAlbumTracks.check(matches(isCompletelyDisplayed()))

        val buttonAddTrack = onView(withId(R.id.add_track))
        buttonAddTrack.check(matches(isDisplayed()))
        buttonAddTrack.perform(click())

        val inputTextTrackName = onView(withId(R.id.name))
        inputTextTrackName.perform(scrollTo(), replaceText("Track 12312"), closeSoftKeyboard())

        val inputTextTrackDuration = onView(withId(R.id.duration_track))
        inputTextTrackDuration.perform(scrollTo(), replaceText("1:23"), closeSoftKeyboard())

        val pickerMinutes = onView(withId(R.id.minutesPicker))
        pickerMinutes.perform(scrollTo())

        val pickerSeconds = onView(withId(R.id.secondsPicker))
        pickerSeconds.perform(scrollTo())

        val buttonSaveTrack = onView(withId(R.id.saveTrack))
        buttonSaveTrack.perform(click())
    }
}