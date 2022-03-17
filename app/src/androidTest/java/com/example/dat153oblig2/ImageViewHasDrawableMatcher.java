package com.example.dat153oblig2;

import android.view.View;
import android.widget.ImageView;

import androidx.test.espresso.matcher.BoundedDiagnosingMatcher;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * A Matcher for Espresso that checks if an ImageView has a drawable applied to it.
 */

public class ImageViewHasDrawableMatcher {

    public static Matcher<View> hasDrawable() {
        return new BoundedDiagnosingMatcher<View, ImageView>(ImageView.class) {
            @Override
            protected void describeMoreTo(Description description) {
                description.appendText("has drawable");
            }

            @Override
            protected boolean matchesSafely(ImageView imageView, Description mismatchDescription) {
                return imageView.getDrawable() != null;
            }
        };
    }
}
