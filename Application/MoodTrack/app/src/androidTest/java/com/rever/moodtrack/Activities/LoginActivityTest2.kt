package com.rever.moodtrack.Activities


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.rever.moodtrack.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest2 {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun loginActivityTest2() {
        val materialTextView = onView(
                allOf(withId(R.id.tvGoToLogin), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                7),
                        isDisplayed()))
        materialTextView.perform(click())

        val appCompatEditText = onView(
                allOf(withId(R.id.etLoginEmail),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                4),
                        isDisplayed()))
        appCompatEditText.perform(replaceText("t@t.com"), closeSoftKeyboard())

        val appCompatEditText2 = onView(
                allOf(withId(R.id.etLoginPassword),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()))
        appCompatEditText2.perform(replaceText("12345"), closeSoftKeyboard())

        val appCompatEditText3 = onView(
                allOf(withId(R.id.etLoginPassword), withText("12345"),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()))
        appCompatEditText3.perform(click())

        val appCompatEditText4 = onView(
                allOf(withId(R.id.etLoginPassword), withText("12345"),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()))
        appCompatEditText4.perform(replaceText("123456"))

        val appCompatEditText5 = onView(
                allOf(withId(R.id.etLoginPassword), withText("123456"),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                5),
                        isDisplayed()))
        appCompatEditText5.perform(closeSoftKeyboard())

        val materialButton = onView(
                allOf(withId(R.id.btnLogIn), withText("Register"),
                        childAtPosition(
                                allOf(withId(R.id.activityLoginAndRegister),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                3),
                        isDisplayed()))
        materialButton.perform(click())

        val materialRadioButton = onView(
                allOf(withId(R.id.rGenderNA), withText("N/A"),
                        childAtPosition(
                                allOf(withId(R.id.rgGender),
                                        childAtPosition(
                                                withId(R.id.activityUserInfoEdit),
                                                1)),
                                2),
                        isDisplayed()))
        materialRadioButton.perform(click())

        val materialRadioButton2 = onView(
                allOf(withId(R.id.rEthnicityNA), withText("N/A"),
                        childAtPosition(
                                allOf(withId(R.id.rgEthnicity),
                                        childAtPosition(
                                                withId(R.id.activityUserInfoEdit),
                                                3)),
                                4),
                        isDisplayed()))
        materialRadioButton2.perform(click())

        val materialRadioButton3 = onView(
                allOf(withId(R.id.rAgeNA), withText("N/A"),
                        childAtPosition(
                                allOf(withId(R.id.rgAge),
                                        childAtPosition(
                                                withId(R.id.activityUserInfoEdit),
                                                5)),
                                5),
                        isDisplayed()))
        materialRadioButton3.perform(click())

        val materialButton2 = onView(
                allOf(withId(R.id.btnDoneUserInfo), withText("Next"),
                        childAtPosition(
                                allOf(withId(R.id.activityUserInfoEdit),
                                        childAtPosition(
                                                withId(android.R.id.content),
                                                0)),
                                11),
                        isDisplayed()))
        materialButton2.perform(click())

        val textView = onView(
                allOf(withId(R.id.tvMainScreenText), withText("0"),
                        withParent(allOf(withId(R.id.activityMain),
                                withParent(withId(android.R.id.content)))),
                        isDisplayed()))
        textView.check(matches(withText("0")))
    }

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
}
