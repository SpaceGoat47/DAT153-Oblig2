package com.example.dat153oblig2;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static org.hamcrest.core.AllOf.allOf;

import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;

import androidx.activity.result.ActivityResult;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.dat153oblig2.Room.AnimalDAO;
import com.example.dat153oblig2.Room.AnimalDatabase;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;

public class DatabaseActivityTest {

    private AnimalDAO animalDAO;
    private AnimalDatabase animalDatabase;

    @Rule
    public ActivityScenarioRule rule = new ActivityScenarioRule(DatabaseActivity.class);

    @Test
    public void test_sizeOfDbAfterAdding(){
        ActivityScenario scenario = rule.getScenario();
        //GIVEN
        Matcher<Intent> expectedIntent = allOf(
            hasAction(Intent.ACTION_OPEN_DOCUMENT),
            hasType("image/*")
        );
        Instrumentation.ActivityResult activityResult = createGalleryPickActivityResultStub();
        intending(expectedIntent).respondWith(activityResult);
    }

    private Instrumentation.ActivityResult createGalleryPickActivityResultStub(){
        ActivityScenario scenario = rule.getScenario();
        //get access to a context in a UI test
        Resources resources = InstrumentationRegistry.getInstrumentation()
                .getContext().getResources();
        Uri imageUri = Uri.parse(
                ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(R.drawable.ic_launcher_background) + "/" +
                resources.getResourceTypeName(R.drawable.ic_launcher_background) + "/" +
                resources.getResourceEntryName(R.drawable.ic_launcher_background)
        );
        Intent resultIntent = new Intent();
        resultIntent.setData(imageUri);
        return new Instrumentation.ActivityResult(RESULT_OK, resultIntent);
    }

}