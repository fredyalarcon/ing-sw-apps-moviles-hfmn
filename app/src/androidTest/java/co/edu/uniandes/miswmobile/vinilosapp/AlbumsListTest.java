package co.edu.uniandes.miswmobile.vinilosapp;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import co.edu.uniandes.miswmobile.vinilosapp.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity;
import kotlin.jvm.Throws;

@RunWith(AndroidJUnit4.class)
public class AlbumsListTest {

    @Rule
    public ActivityScenarioRule<AccessActivity> mActivityTestRule = new ActivityScenarioRule<>(AccessActivity.class);


    @Test
    public void mainActivityAlbumsEmptyTest() {
        ViewInteraction buttonVisitor = onView(allOf(withId(R.id.buttonVisitor), isDisplayed()));
        buttonVisitor.perform(click());

        ViewInteraction buttonAlbums = onView(allOf(withId(R.id.buttonAlbums), isDisplayed()));
        buttonAlbums.perform(click());

        onView(allOf(withId(R.id.action_bar), withText("Álbumes"), isDisplayed()));

        onView(allOf(withId(R.id.albumsRv), isDisplayed()));
    }
}
