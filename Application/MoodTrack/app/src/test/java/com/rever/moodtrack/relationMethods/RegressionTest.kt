package com.rever.moodtrack.relationMethods

import com.google.common.truth.Truth.assertThat
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.data.Question
import org.junit.Test

class RegressionTest{
    private val allowedDif = 0.2 // allowed difference from expected


    //-------------TEST-REGRESSION-----------------
    @Test
    fun simplePositiveRegression(){
        val yList = listOf(1.0,2.0,3.0)
        val xList = listOf(1.0,2.0,3.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= 1.00x + 0.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(1 + allowedDif)//Is the tilt somewhat correct
        assertThat(result[0]).isAtLeast(1 - allowedDif)

        assertThat(result[1]).isAtMost(0 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(0 - allowedDif)
    }

    @Test
    fun simplePositiveRegressionMoreValues(){
        val yList = listOf(1.0,2.0,3.0,4.0,5.0)
        val xList = listOf(1.0,2.0,3.0,4.0,5.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= 1.00x + 0.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(1 + allowedDif)
        assertThat(result[0]).isAtLeast(1- allowedDif) //Is the tilt somewhat correct

        assertThat(result[1]).isAtMost(0 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(0 - allowedDif)
    }

    @Test
    fun emptyLists(){
        val yList = listOf<Double>()
        val xList = listOf<Double>()
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= 1.00x + 0.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(0 + allowedDif)//Is the tilt somewhat correct
        assertThat(result[0]).isAtLeast(0 - allowedDif)

        assertThat(result[1]).isAtMost(0 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(0 - allowedDif)
    }

    @Test
    fun simpleNoChange(){
        val yList = listOf(2.0,2.0,2.0)
        val xList = listOf(1.0,2.0,3.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= 0.00x + 2.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtLeast(0 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0]).isAtMost(0 + allowedDif)

        assertThat(result[1]).isAtMost(2 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(2 - allowedDif)
    }
    @Test
    fun simpleNegativeValuesRegression(){
        val yList = listOf(-1.0,-2.0,-3.0)
        val xList = listOf(1.0,2.0,3.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= -1.00x + 0.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(-1 + allowedDif)
        assertThat(result[0]).isAtLeast(-1 - allowedDif)

        assertThat(result[1]).isAtMost(0 + allowedDif)
        assertThat(result[1]).isAtLeast(0 - allowedDif)
    }

    @Test
    fun simpleNegativeRegression(){
        val yList = listOf(3.0,2.0,1.0)
        val xList = listOf(1.0,2.0,3.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= -1.00x + 4.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(-1 + allowedDif)
        assertThat(result[0]).isAtLeast(-1 - allowedDif)

        assertThat(result[1]).isAtMost(4 + allowedDif)
        assertThat(result[1]).isAtLeast(4 - allowedDif)
    }

    @Test
    fun randomTestForRealScenario(){
        val yList = listOf(4.0,5.0,6.0)
        val xList = listOf(4.0,5.0,6.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= 1.00x + 0.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(1 + allowedDif)
        assertThat(result[0]).isAtLeast(1- allowedDif) //Is the tilt somewhat correct

        assertThat(result[1]).isAtMost(0 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(0 - allowedDif)
    }

    @Test
    fun randomTestForRealScenarioNegative(){
        val yList = listOf(6.0,5.0,4.0)
        val xList = listOf(4.0,5.0,6.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= -1.00x + 10.00 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(-1 + allowedDif)
        assertThat(result[0]).isAtLeast(-1- allowedDif) //Is the tilt somewhat correct

        assertThat(result[1]).isAtMost(10 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(10 - allowedDif)
    }

    @Test
    fun randomTestForRealScenarioNoCorrelation(){
        val yList = listOf(6.0,5.0,6.0)
        val xList = listOf(4.0,5.0,6.0)
        val result = Regression.regression(yList, xList)

        println("Expected:  f(x)= 0.00x + 5.67 ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0])}x + ${String.format("%.3f",result[1])}")

        assertThat(result[0]).isAtMost(0 + allowedDif)
        assertThat(result[0]).isAtLeast(0- allowedDif) //Is the tilt somewhat correct

        assertThat(result[1]).isAtMost(5.67 + allowedDif) //Is the constant somewhat correct
        assertThat(result[1]).isAtLeast(5.67 - allowedDif)
    }
    //--------------------TEST-QUESTION-LIST-2-LIST--------------------------

    @Test
    fun missingValueRemoval(){
        val list1 = listOf(1.0,2.0,3.0)
        val list2 = listOf(3.0,-1.0,1.0)

        val result = Regression.removeMissingValues(list1, list2)
        assertThat(result[0]).isEqualTo(listOf(1.0,3.0))
        assertThat(result[1]).isEqualTo(listOf(3.0,1.0))
    }
    @Test
    fun missingValueRemovalNoMissing(){
        val list1 = listOf(1.0,2.0,3.0)
        val list2 = listOf(3.0,2.0,1.0)

        val result = Regression.removeMissingValues(list1, list2)
        assertThat(result[0]).isEqualTo(listOf(1.0,2.0,3.0))
        assertThat(result[1]).isEqualTo(listOf(3.0,2.0,1.0))
    }

    @Test
    fun questionList2ListList(){
        val list = mutableListOf<questionCollection>()
        for (i in 1..3) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, i))
            list.add(newDay)
        }
        val result = Regression.collection2ListList(list)
        assertThat(result.size).isEqualTo(2)
        assertThat(result[0].rateList.size).isEqualTo(3)
        assertThat(result[0].rateList).isEqualTo(listOf(1.0,2.0,3.0))
    }
    @Test
    fun questionList2ListListLarge(){
        val list = mutableListOf<questionCollection>()
        for (i in 1..20) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, i))
            list.add(newDay)
        }
        val result = Regression.collection2ListList(list)

        assertThat(result.size).isEqualTo(2)
        assertThat(result[0].rateList.size).isEqualTo(20)
    }

    @Test
    fun questionList2ListListMissingValues(){
        val list = mutableListOf<questionCollection>()
        for (i in 1..3) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            if(i != 2)
                newDay.qList.add(Question("Need1",0, i))
            list.add(newDay)
        }
        val result = Regression.collection2ListList(list)

        assertThat(result.size).isEqualTo(2)
        assertThat(result[0].rateList).isEqualTo(listOf(1.0,-1.0,3.0))
    }

    //--------------------TEST-REGRESSION-ON-QUESTION-LISTS------------------

    @Test
    fun simpleQuestion2Reg(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, i))

            list.add(newDay)
        }
        val result = Regression.doRegression(list)

        println("Expected:  f(x)= 1.00x + ANY ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(1)

        assertThat(result[0].id).isEqualTo("Want1")     //Check Name locations
        assertThat(result[0].titleList[0]).isEqualTo("Need1")

        assertThat(result[0].rateList[0]).isAtLeast(1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(1 + allowedDif)
    }

    @Test
    fun simpleQuestion2RegNegative(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, 5-i))
            list.add(newDay)
        }
        val result = Regression.doRegression(list)

        println("Expected:  f(x)= -1.00x + ANY ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(1)

        assertThat(result[0].id).isEqualTo("Want1")     //Check Name locations
        assertThat(result[0].titleList[0]).isEqualTo("Need1")

        assertThat(result[0].rateList[0]).isAtLeast(-1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(-1 + allowedDif)
    }
    @Test
    fun simpleQuestion2RegNoChange(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, 3))
            list.add(newDay)
        }
        val result = Regression.doRegression(list)

        println("Expected:  f(x)= 0.00x + ANY ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(1)

        assertThat(result[0].id).isEqualTo("Want1")     //Check Name locations
        assertThat(result[0].titleList[0]).isEqualTo("Need1")

        assertThat(result[0].rateList[0]).isAtLeast(0- allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(0 + allowedDif)
    }

    @Test
    fun simpleQuestion2RegMoreValues(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..20) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, i))
            list.add(newDay)
        }
        val result = Regression.doRegression(list)

        println("Expected:  f(x)= 1.00x + ANY ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(1)

        assertThat(result[0].id).isEqualTo("Want1")     //Check Name locations
        assertThat(result[0].titleList[0]).isEqualTo("Need1")

        assertThat(result[0].rateList[0]).isAtLeast(1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(1 + allowedDif)
    }



    @Test
    fun simpleQuestion2RegMissingValues(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..20) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            if(i % 2 == 0)
                newDay.qList.add(Question("Need1",0, i))
            list.add(newDay)
        }
        val result = Regression.doRegression(list)

        println("Expected:  f(x)= 1.00x + ANY ")
        println("Predicted: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(1)

        assertThat(result[0].id).isEqualTo("Want1")     //Check Name locations
        assertThat(result[0].titleList[0]).isEqualTo("Need1")

        assertThat(result[0].rateList[0]).isAtLeast(1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(1 + allowedDif)
    }

    @Test
    fun manyQuestion2Reg(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, i))
            newDay.qList.add(Question("Need1",0, i))
            newDay.qList.add(Question("Need2",0, 5-i))
            newDay.qList.add(Question("Need3",0, 2))
            list.add(newDay)
        }
        val result = Regression.doRegression(list)
        println("Expected1:  f(x)= 1.00x + ANY ")
        println("Predicted1: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")
        println("Expected2:  f(x)= -1.00x + ANY ")
        println("Predicted2: f(x)= ${String.format("%.3f",result[0].rateList[1])}x + ANY")
        println("Expected3:  f(x)= 0.00x + ANY ")
        println("Predicted3: f(x)= ${String.format("%.3f",result[0].rateList[2])}x + ANY")

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(3)

        assertThat(result[0].rateList[0]).isAtLeast(1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(1 + allowedDif)

        assertThat(result[0].rateList[1]).isAtLeast(-1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[1]).isAtMost(-1 + allowedDif)

        assertThat(result[0].rateList[2]).isAtLeast(0 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[2]).isAtMost(0 + allowedDif)
    }

    @Test
    fun multiCaseTest() {
        val list = mutableListOf<questionCollection>()
        for (i in 0..3) {
            val newDay = questionCollection("Day${i}")
            newDay.qList.add(Question("Want1",1, 4 + i))
            newDay.qList.add(Question("Need1",0, 4 + 0))
            newDay.qList.add(Question("Need2",0, 4 - i))
            newDay.qList.add(Question("Need3",0, 4 + i))
            list.add(newDay)
        }
        val result = Regression.doRegression(list)

        assertThat(result.size).isEqualTo(1)
        assertThat(result[0].titleList.size).isEqualTo(3)

        println("Expected1:  f(x)= 0.00x + ANY ")
        println("Predicted1: f(x)= ${String.format("%.3f",result[0].rateList[0])}x + ANY")
        assertThat(result[0].rateList[0]).isAtLeast(0 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[0]).isAtMost(0 + allowedDif)

        println("Expected2:  f(x)= -1.00x + ANY ")
        println("Predicted2: f(x)= ${String.format("%.3f",result[0].rateList[1])}x + ANY")
        assertThat(result[0].rateList[1]).isAtLeast(-1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[1]).isAtMost(-1 + allowedDif)

        println("Expected3:  f(x)= +1.00x + ANY ")
        println("Predicted3: f(x)= ${String.format("%.3f",result[0].rateList[2])}x + ANY")
        assertThat(result[0].rateList[2]).isAtLeast(1 - allowedDif) //Is the tilt somewhat correct
        assertThat(result[0].rateList[2]).isAtMost(1 + allowedDif)
    }

}