package com.ssimon.cyclesactivity.ui;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.ssimon.cyclesactivity.DatabaseTestUtils;
import com.ssimon.cyclesactivity.data.CoffeeDaoTest;
import com.ssimon.cyclesactivity.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CoffeesActivityTest {
    @Rule
    public ActivityTestRule<CoffeesActivity> mActivityTestRule = new ActivityTestRule<>(CoffeesActivity.class);

    @Test
    public void coffeesActivityTest() {
        CoffeesActivityTest cat = new CoffeesActivityTest();
        ViewInteraction appCompatButton = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction appCompatButton4 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction appCompatButton5 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction appCompatButton6 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton6.perform(click());

        ViewInteraction appCompatButton7 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton7.perform(click());

        ViewInteraction appCompatButton8 = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton8.perform(click());

    }

}
