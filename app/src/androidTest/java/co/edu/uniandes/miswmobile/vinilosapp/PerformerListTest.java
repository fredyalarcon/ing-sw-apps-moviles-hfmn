package co.edu.uniandes.miswmobile.vinilosapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import static kotlinx.coroutines.DelayKt.delay;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import co.edu.uniandes.miswmobile.vinilosapp.ui.AccessActivity;

@RunWith(AndroidJUnit4.class)
public class PerformerListTest {

    @Rule
    public ActivityScenarioRule<AccessActivity> mActivityTestRule = new ActivityScenarioRule<>(AccessActivity.class);

    @Test
    public void mainActivityArtistsEmptyTest() {
        ViewInteraction buttonVisitor = onView(allOf(withId(R.id.buttonVisitor), withText("Visitante"), isDisplayed()));
        buttonVisitor.perform(click());

        ViewInteraction buttonArtists = onView(allOf(withId(R.id.buttonArtists), withText("Artistas"), isDisplayed()));
        buttonArtists.perform(click());

        onView(allOf(withId(R.id.action_bar), withText("Artistas"), isDisplayed()));

        onView(allOf(withId(R.id.artistsRv), isDisplayed()));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
