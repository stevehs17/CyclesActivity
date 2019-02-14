package com.ssimon.cyclesactivity.ui;


import android.content.Context;
import android.support.test.InstrumentationRegistry;
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
    final private Context context = InstrumentationRegistry.getTargetContext();

    @Rule
    public ActivityTestRule<CoffeesActivity> mActivityTestRule = new ActivityTestRule<>(CoffeesActivity.class);

    /*
    @Test
    public void coffeesActivityTest() {
        DatabaseTestUtils.addCoffeesToDb(context);
        ViewInteraction appCompatButton = onView(
                allOf(withText("Edit Coffee"),
                        withParent(allOf(withId(R.id.activity_managecoffees),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()));
        appCompatButton.perform(click());
    }
    */

    @Test
    public void coffeesActivityTest() {
        return;
    }
}
