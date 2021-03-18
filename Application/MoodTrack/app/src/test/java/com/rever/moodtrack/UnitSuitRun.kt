package com.rever.moodtrack

import com.rever.moodtrack.relationMethods.RegressionTest
import com.rever.moodtrack.relationMethods.pearsonCorrelationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        pearsonCorrelationTest::class,
        RegressionTest::class
)
class UnitSuitRun