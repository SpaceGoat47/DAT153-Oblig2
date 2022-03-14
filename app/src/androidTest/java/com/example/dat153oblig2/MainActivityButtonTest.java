package com.example.dat153oblig2;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.junit.Assert.*;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.*;


public class MainActivityButtonTest {

    @Rule public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_isActivityActive(){
        onView(withId(R.id.parentMainActivity)).check(matches(isDisplayed()));
    }

    @Test
    public void test_navDatabaseActivity() {
        onView(withId(R.id.btnDatabase)).perform(click());
        onView(withId(R.id.activityDatabase)).check(matches(isDisplayed()));
    }
}