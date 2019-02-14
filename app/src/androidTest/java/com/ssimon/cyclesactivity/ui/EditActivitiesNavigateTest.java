package com.ssimon.cyclesactivity.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ssimon.cyclesactivity.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

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
    public ActivityTestRule<CoffeesActivity> mActivityTestRule = new ActivityTestRule<>(CoffeesActivity.class);

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
