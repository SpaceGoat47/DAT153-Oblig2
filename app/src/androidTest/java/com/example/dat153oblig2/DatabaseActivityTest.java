package com.example.dat153oblig2;

import static android.app.Activity.RESULT_OK;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.intending;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasCategories;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasData;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasType;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.Espresso.*;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;


import static com.example.dat153oblig2.ImageConverter.getUri;
import static com.example.dat153oblig2.ImageViewHasDrawableMatcher.hasDrawable;

import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;

import com.example.dat153oblig2.Room.Animal;
import com.example.dat153oblig2.Room.AnimalDAO;
import com.example.dat153oblig2.Room.AnimalDatabase;

import junit.framework.TestCase;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class DatabaseActivityTest{

    private AnimalDAO animalDAO;
    private AnimalDatabase animalDatabase;
    private List<Animal> allAnimalsList;

    @Rule
    public ActivityScenarioRule<AddEntryActivity> rule = new ActivityScenarioRule(AddEntryActivity.class);

    @Rule public GrantPermissionRule grantPermissionRule = GrantPermissionRule.grant(
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.READ_EXTERNAL_STORAGE"
            );

    @Before
    public void initializeIntent() throws Exception{
        Intents.init();
    }

    @Before
    public void setUp(){
        Context context = ApplicationProvider.getApplicationContext();
        animalDatabase = Room.inMemoryDatabaseBuilder(context, AnimalDatabase.class).build();
        animalDAO = animalDatabase.animalDAO();
    }

    @After
    public void closeDb(){
        animalDatabase.close();
    }

    @After
    public void closeIntents() throws Exception{
        Intents.release();
    }

    @Test
    public void test_writeAndReadAnimal(){
        Animal animal = new Animal("Test", getUri(R.drawable.cat));
        animalDAO.insert(animal);
        //allAnimalsList = animalDAO.getAllAnimalsList();
        //Assert.assertThat(allAnimalsList.get(allAnimalsList.size()-1).getName(), (animal.getName()));
        //assertThat(0, equalTo(5));

    }

    @Test
    public void test_sizeOfDbAfterAdding(){
        //GIVEN
        Matcher<Intent> expectedIntent = allOf(
            hasAction(Intent.ACTION_OPEN_DOCUMENT),
            hasData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        );
        Instrumentation.ActivityResult activityResult = createGalleryPickActivityResultStub();

        intending(expectedIntent).respondWith(activityResult);
        //onView(withId(R.id.imgImageAdd)).check(matches(not(hasDrawable())));
        onView(withId(R.id.btnChooseImage)).perform(click());

        //onView(withId(R.id.imgImageAdd)).check(matches(hasDrawable()));
        //onView(withId(R.id.edtName)).perform(typeText("Whale"));
        //onView(withId(R.id.saveNote)).perform(click());

        intended(expectedIntent);

        //onView(withId(R.id.btnChooseImage)).perform(click());
        //intended(expectedIntent);
    }

    private Instrumentation.ActivityResult createGalleryPickActivityResultStub(){
        //get access to a context in a UI test
        Resources resources = InstrumentationRegistry.getInstrumentation()
                .getTargetContext().getResources();
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