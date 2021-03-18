package com.rever.moodtrack


import android.view.View
import android.view.ViewGroup
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setUp() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun startupMainInView(){
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }

    @Test
    fun allButtonsThere(){
        onView(withId(R.id.btnNewInput)).check(matches(isDisplayed()))
        onView(withId(R.id.btnStat)).check(matches(isDisplayed()))
        onView(withId(R.id.btnSettings)).check(matches(isDisplayed()))
        onView(withId(R.id.btnAbout)).check(matches(isDisplayed()))
    }

    @Test
    fun checkDefaultNeedsShown(){
        onView(withId(R.id.btnNewInput)).perform(click())
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        onView(withText("Happiness")).check(matches(isDisplayed()))

        onView(withId(R.id.btnToAddNeed)).perform(click())
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))
        onView(withText("Sleep")).check(matches(isDisplayed()))
        onView(withText("Movement")).check(matches(isDisplayed()))
        onView(withText("Social")).check(matches(isDisplayed()))


        onView(withId(R.id.btnToStat)).perform(click())
        onView(withId(R.id.activityStatistics)).check(matches(isDisplayed()))

        pressBack()
        pressBack()
        pressBack()
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }

    @Test
    fun goToStatisticsAndBack(){
        onView(withId(R.id.btnStat)).perform(click())
        onView(withId(R.id.activityStatistics)).check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }

    @Test
    fun goToAboutAndBack(){
        onView(withId(R.id.btnAbout)).perform(click())
        onView(withId(R.id.activityAbout)).check(matches(isDisplayed()))

        pressBack()
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }


    @Test
    fun cancelCustomNeed(){
        go2EditNeed()

        //Open dialog and cancel
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.dialogAddNeed)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCancel)).perform(click())
        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))
    }

    @Test
    fun addCustomWant(){
        go2EditNeed()

        //Open dialog and ann want
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.dialogAddNeed)).check(matches(isDisplayed()))
        onView(withId(R.id.sIsSubjective)).perform(click())
        onView(withId(R.id.etNeedTitle)).perform(
            ViewActions.replaceText("Want1"),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.btnAddNeed)).perform(click())

        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))
        onView(withText("Want1")).check(matches(isDisplayed()))
        onView(withText("Want")).check(matches(isDisplayed()))

        pressBack()
        pressBack()
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        //See if in add new input
        onView(withId(R.id.btnNewInput)).perform(click())
        onView(withText("Want1")).check(matches(isDisplayed()))
        pressBack()

        //Go and delete values
        go2EditNeed()
        onView(Matchers.allOf(withId(R.id.ivDeleteNeed),
            childAtPosition(
                    childAtPosition(withClassName(Matchers.`is`(
                        "androidx.cardview.widget.CardView")), 0),
            2),
            isDisplayed())).perform(click())
    }

    @Test
    fun addCustomNeed(){
        go2EditNeed()

        //Open dialog and ann want
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.dialogAddNeed)).check(matches(isDisplayed()))
        onView(withId(R.id.etNeedTitle)).perform(
                ViewActions.replaceText("Need1"),
                ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.btnAddNeed)).perform(click())

        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))
        onView(withText("Need1")).check(matches(isDisplayed()))
        onView(withText("Need")).check(matches(isDisplayed()))

        pressBack()
        pressBack()
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        //See if in add new input
        onView(withId(R.id.btnNewInput)).perform(click())
        onView(withId(R.id.btnToAddNeed)).perform(click())
        onView(withText("Need1")).check(matches(isDisplayed()))
        pressBack()
        pressBack()

        //Go and delete values
        go2EditNeed()
        onView(Matchers.allOf(withId(R.id.ivDeleteNeed),
                childAtPosition(
                        childAtPosition(withClassName(Matchers.`is`(
                                "androidx.cardview.widget.CardView")), 0),
                        2),
                isDisplayed())).perform(click())
    }

    @Test
    fun deleteLocalDbAndSeeChanges() {
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))

        onView(withId(R.id.btnDeleteTable)).perform(click())
        pressBack()
        onView(withId(R.id.tvMainScreenText)).check(matches(withText("0\nDays")))

        checkDefaultNeedsShown() //It adds a instance
        onView(withId(R.id.tvMainScreenText)).check(matches(withText("1\nDays")))

        //Delete again to make sure it deletes
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))

        onView(withId(R.id.btnDeleteTable)).perform(click())
        pressBack()
        onView(withId(R.id.tvMainScreenText)).check(matches(withText("0\nDays")))
    }

    //Has a lot of tests in edit need, just to keep it simple
    private fun go2EditNeed(){
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))

        onView(withId(R.id.btnEditNeed)).perform(click())
        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))
    }

    //Function to select a child of RecycleView
    //Code generated by android studio test recorder
    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int,
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

    //Does not seem to be possible to automatically change the seekbars,
    //Or it is just really hard
/*
    @Test
    fun AddNeed(){
        onView(withId(R.id.btnNewInput)).perform(click())
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))

        //Set happiness to something

        onView(withId(R.id.btnToAddNeed)).perform(click())
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))

        //Set rest to something

        onView(withId(R.id.btnToStat)).perform(click())
        onView(withId(R.id.activityStatistics)).check(matches(isDisplayed()))
    }
*/
}
