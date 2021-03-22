package com.rever.moodtrack

import com.rever.moodtrack.relationMethods.RegressionTest
import com.rever.moodtrack.relationMethods.PearsonCorrelationTest
import com.rever.moodtrack.relationMethods.SpearmanCorrelationTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
        PearsonCorrelationTest::class,
        SpearmanCorrelationTest::class,
        RegressionTest::class
)
class UnitSuitRun