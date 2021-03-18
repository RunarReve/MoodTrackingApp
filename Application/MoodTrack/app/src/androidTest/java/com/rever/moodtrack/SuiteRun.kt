package com.rever.moodtrack

import com.rever.moodtrack.data.QuestionDaoTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
        QuestionDaoTest::class,
        MainActivityTest::class
)
class SuiteRun