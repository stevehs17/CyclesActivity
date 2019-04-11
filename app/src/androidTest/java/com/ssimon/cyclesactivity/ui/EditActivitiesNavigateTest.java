package com.ssimon.cyclesactivity.ui;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ssimon.cyclesactivity.R;
import com.ssimon.cyclesactivity.data.CoffeeDao;
import com.ssimon.cyclesactivity.data.DatabaseHelper;
import com.ssimon.cyclesactivity.data.DatabaseTestUtils;
import com.ssimon.cyclesactivity.model.Coffee;
import com.ssimon.cyclesactivity.util.ModelUtils;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EditActivitiesNavigateTest {

    @Rule
    public ActivityTestRule<CoffeeActivity> activityRule = new ActivityTestRule<>
            (CoffeeActivity.class, false, false);

    @BeforeClass
    static public void setupDatabase() {
        DatabaseTestUtils.setupDatabase();
    }

    @Before
    public void setupTables() {
        DatabaseTestUtils.setupTables();

        Context ctx = InstrumentationRegistry.getTargetContext();
        DatabaseHelper dh = DatabaseHelper.getInstance(ctx);
        SQLiteDatabase db = dh.getWritableDatabase();
        List<Coffee> cs = ModelUtils.createDefaultCoffeeTemplates();
        CoffeeDao.insertCoffees(db, cs);
        activityRule.launchActivity(null);
    }

    @Test
    public void navigateEditActivities2_Success() {
        descendToVolumes();
        descendToCycles();
        ascendToVolumes();
        ascendToCoffees();
    }

    private void descendToVolumes() {
        ViewInteraction appCompatButton = onView(
                allOf(withText("Edit"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());
    }

    private void descendToCycles() {
        ViewInteraction appCompatButton2 = onView(
                allOf(withText("Edit"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());
    }

    private void ascendToVolumes() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }

    private void ascendToCoffees() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        withParent(allOf(withId(R.id.action_bar),
                                withParent(withId(R.id.action_bar_container)))),
                        isDisplayed()));
        appCompatImageButton.perform(click());
    }
}
