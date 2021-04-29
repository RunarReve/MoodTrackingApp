package com.rever.moodtrack

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.rever.moodtrack.Activities.LoginActivity
import kotlinx.android.synthetic.main.activity_settings.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LoginActivityTest {

    val waitDelay = 1300L

    @Before
    fun setUp() {
        val activityScenario = ActivityScenario.launch(LoginActivity::class.java)
    }

    @Test
    fun startupLoginInView(){
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Log In")).check(matches(isDisplayed()))
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))
        onView(withText("Register")).check(matches(isDisplayed()))
    }

    @Test
    fun goToRegisterAndBack(){
        //Log in activity
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Log In")).check(matches(isDisplayed()))
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))
        onView(withId(R.id.tvGoToLogin)).perform(click())

        //Register activity
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Already got an account:")).check(matches(isDisplayed()))
        onView(withText("Register Account")).check(matches(isDisplayed()))
        onView(withId(R.id.tvGoToLogin)).perform(click())

        //Log in activity
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Log In")).check(matches(isDisplayed()))
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))
    }

    @Test
    fun logInWithVariousFaults(){
        val email = "TEST@xssypw9oph.com"
        val password = "jhck3a7n8u"

        //Nothing
        logIn("","")
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //Only Email
        logIn(email, "")
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

       //Only Password
        logIn("",password)
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //Log in with non-existing user
        logIn(email,password)
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
    }

    @Test
    fun registerWithVariousFaults(){
        val email = "TEST@xssypw9oph2.com"
        val password = "jhck3a7n8u3"
        val emailFaulty = "xssypw9oph"
        val passwordShort = "jhck3"
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withId(R.id.tvGoToLogin)).perform(click())

        //Nothing
        register("","")
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //Only Email
        register(email,"")
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //Only Password
        register("", password)
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //To short password (less than 6 characters))
        register(email,passwordShort)
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //Email input not email format "e.g. not '@'"
        register(emailFaulty,password)
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
    }

    @Test
    fun makeNewAccountAndDelete(){
        val email = "TEST@xssypw9oph3.com"
        val password = "jhck3a7n8u3"

        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))

        //Try to log in to user not yet created
        logIn(email,password)
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))

        //MakeAccount
        registerUser(email,password)

        //Log Out and In
        onView(withId(R.id.btnLogOut)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something

        //Then log in again
        logIn(email, password)
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        deleteUser(email, password)
    }

    @Test
    fun checkBasicQuestionsIsDisplayed(){
        val email = "TEST@xcaypw9oph4.com"
        val password = "jhck3a7qs8u3"

        registerUser(email,password)

        //To enter new question and see if default question is available
        onView(withId(R.id.btnNewInput)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        onView(withText(R.string.defaultWant1)).check(matches(isDisplayed()))

        onView(withId(R.id.btnToAddNeed)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))
        onView(withText(R.string.defaultNeed1)).check(matches(isDisplayed()))
        onView(withText(R.string.defaultNeed2)).check(matches(isDisplayed()))
        onView(withText(R.string.defaultNeed3)).check(matches(isDisplayed()))

        //Go back to main menu
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        deleteUser(email, password)
    }

    @Test
    fun checkAddCustomQuestions(){
        val email = "TEST@xcrayw1poph4.com"
        val password = "jhck1sv7qs38"

        registerUser(email,password)

        //==============CHECK=IT'S=NOT=THERE====================

        //To enter new question and see Custom needs not displayed
        onView(withId(R.id.btnNewInput)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        onView(withText("Productivity")).check(doesNotExist())

        onView(withId(R.id.btnToAddNeed)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))
        onView(withText("Code")).check(doesNotExist())

        //Go back to main menu
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        //=============ADD=CUSTOM=QUESTIONS==============

        //Go to add custom needs
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))
        onView(withId(R.id.btnEditNeed)).perform(click())
        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))

        //Test cancel button
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.dialogAddNeed)).check(matches(isDisplayed()))
        onView(withId(R.id.btnCancel)).perform(click())
        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))

        addCustomNeed("DEL",false) //Add Need to delete
        onView(withText("DEL")).check(matches(isDisplayed()))
        onView(withId(R.id.ivDeleteNeed)).perform(click())
        onView(withText("DEL")).check(doesNotExist())
        addCustomNeed("Code",false) //Add Need
        addCustomNeed("Productivity",true) //Add Want

        //Go back to main menu
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something

        //==============CHECK=IT'S=ACTUALLY=DISPLAYED====================

        //Check if custom needs are displayed
        onView(withId(R.id.btnNewInput)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        onView(withText("Productivity")).check(matches(isDisplayed()))

        onView(withId(R.id.btnToAddNeed)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))
        onView(withText("Code")).check(matches(isDisplayed()))
        onView(withText("DEL")).check(doesNotExist())

        //Go back to main menu
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        //===================DESTROY=USER======================
        deleteUser(email, password)
    }

    @Test
    fun checkAddingNewInputsIsSaved(){
        val email = "TEST@xcrayks9oph4.com"
        val password = "jhca43gs7qs38"

        registerUser(email,password)

        //Initially starts with no inputs
        onView(withId(R.id.tvMainScreenText)).check(matches(withText("0")))

        //Input one data and see total input incremented by 1
        newArbitraryDataInput()
        onView(withId(R.id.tvMainScreenText)).check(matches(withText("1")))

        //DO IT AGAIN, and see it increment to 0
        newArbitraryDataInput()
        onView(withId(R.id.tvMainScreenText)).check(matches(withText("2")))

        deleteUser(email, password)
    }

    @Test
    fun checkAndChangeUserInfo(){
        val email = "TEST@xcraykss5c34.com"
        val password = "czca40gs7pl3s"

        registerUser(email,password)

        //Go to user information
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))
        onView(withId(R.id.btnEditUserData)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityUserInfoEdit)).check(matches(isDisplayed()))

        //Check old data is stored and change it
        onView(withText("TestLand")).check(matches(isDisplayed()))
        onView(withText("TestCode")).check(matches(isDisplayed()))
        onView(withId(R.id.etNationality)).perform(replaceText("Testistan"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.etPostCode)).perform(replaceText("CodeOfTest"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnDoneUserInfo)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        //Go to user information again
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))
        onView(withId(R.id.btnEditUserData)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityUserInfoEdit)).check(matches(isDisplayed()))

        //Check that data was updated
        onView(withText("Testistan")).check(matches(isDisplayed()))
        onView(withText("CodeOfTest")).check(matches(isDisplayed()))
        onView(withId(R.id.btnDoneUserInfo)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))

        deleteUser(email, password)
    }

    //======================FUNCTIONS===========================
    private fun logIn(email: String, password: String){
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withId(R.id.etLoginEmail)).perform(replaceText(email), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.etLoginPassword)).perform(replaceText(password), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnLogIn)).perform(click())
    }

    private fun register(email: String, password: String){
        onView(withText("Already got an account:")).check(matches(isDisplayed()))
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withId(R.id.etLoginEmail)).perform(replaceText(email), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.etLoginPassword)).perform(replaceText(password), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.btnLogIn)).perform(click())
    }

    private fun registerUser(email: String, password: String){
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))
        //Go to register and make account
        onView(withId(R.id.tvGoToLogin)).perform(click())
        register(email,password)
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityUserInfoEdit)).check(matches(isDisplayed()))

        //Try to continue without doing anything
        onView(withId(R.id.btnDoneUserInfo)).perform(click())
        onView(withId(R.id.activityUserInfoEdit)).check(matches(isDisplayed()))

        //Fill in arbitrary data
        onView(withId(R.id.rGenderNA)).perform(click())
        onView(withId(R.id.rEthnicityNA)).perform(click())
        onView(withId(R.id.rAgeNA)).perform(click())
        onView(withId(R.id.etNationality)).perform(replaceText("TestLand"), ViewActions.closeSoftKeyboard())
        onView(withId(R.id.etPostCode)).perform(replaceText("TestCode"), ViewActions.closeSoftKeyboard())

        onView(withId(R.id.btnDoneUserInfo)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }

    private fun deleteUser(email: String, password: String){
        //To settings and delete User
        onView(withId(R.id.btnSettings)).perform(click())
        onView(withId(R.id.activitySettings)).check(matches(isDisplayed()))
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.btnDeleteUser)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        dialogYes()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
        onView(withText("Do not have an account:")).check(matches(isDisplayed()))

        //Try to log into user again
        logIn(email,password)
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityLoginAndRegister)).check(matches(isDisplayed()))
    }

    private fun addCustomNeed(string:String, isWant: Boolean){
        onView(withId(R.id.fabAdd)).perform(click())
        onView(withId(R.id.dialogAddNeed)).check(matches(isDisplayed()))
        onView(withId(R.id.etNeedTitle)).perform(replaceText(
                string), ViewActions.closeSoftKeyboard())
        if(isWant)
            onView(withId(R.id.sIsSubjective)).perform(click()) //Toggle its a want
        onView(withId(R.id.btnAddNeed)).perform(click())
        onView(withId(R.id.activitySettingsNeedEdit)).check(matches(isDisplayed()))
        onView(withText(string)).check(matches(isDisplayed()))
    }

    private fun newArbitraryDataInput(){
        onView(withId(R.id.btnNewInput)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))

        onView(withId(R.id.btnToAddNeed)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))

        onView(withId(R.id.btnToStat)).perform(click())
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityStatistics)).check(matches(isDisplayed()))

        //Go back to main menu
        Espresso.pressBack()
        onView(withId(R.id.activityAddNeed)).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityAddWant)).check(matches(isDisplayed()))
        Espresso.pressBack()
        Thread.sleep(waitDelay) //Non optimal method to give it time to do something
        onView(withId(R.id.activityMain)).check(matches(isDisplayed()))
    }

    private fun dialogYes(){
        onView(withId(android.R.id.button1)).perform(click())
    }
    private fun dialogNo(){
        onView(withId(android.R.id.button2)).perform(click())
    }
}