package com.rever.moodtrack.relationMethods

import com.rever.moodtrack.data.relationCollection
import com.rever.moodtrack.data.questionCollection
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * This object has been inspired by Thomas Nields "linear_regression_gradient_decent.kts"
 * Link:https://gist.github.com/thomasnield/fbe2e2205233388577e6abe6f5bbe897#file-linear_regression_gradient_descent-kts
 *
 * Major modification has been done to fit with this program and make it more optimal for its purpose
 */
object Regression {

    //Linear regression to get function "f(x)=m*x + n"
    fun regression(yList: List<Double>, xList:List<Double>):List<Double>{
        val learningRate = 0.001 //Tuning of learning rate
        val epochs = 10_000      //Number of iterations
        var m = 0.0 //Start position of tilt
        var n = 0.0 //Start position of start

        /**
         * Find Average deviancy between prediction and given list
         */
        fun averageDeviancy(predictionList: List<Double>):Double{
            var tot = 0.0
            (0..predictionList.size-1).forEach {
                tot += (predictionList[it] - yList[it]).pow(2)
            }
            return sqrt(tot)/predictionList.size //Returns avg deviancy of function
        }

        (0..epochs).forEach{

            //Calculate for all
            val calculationList = mutableListOf<List<Double>>()
            val listOfChanges =  listOf(
                listOf(m,n), //Default
                //Change only one variable
                listOf(m+learningRate,n),
                listOf(m-learningRate,n),
                listOf(m,n+learningRate),
                listOf(m,n-learningRate),
                //Change both variables
                listOf(m+learningRate,n+learningRate),
                listOf(m+learningRate,n-learningRate),
                listOf(m-learningRate,n-learningRate),
                listOf(m-learningRate,n+learningRate),
            )
            //-------CALCULATE-AVERAGE-DEVIANCY-------------------
            listOfChanges.forEach { vars ->
                calculationList.add(listOf(averageDeviancy(xList.map {(vars[0] * it) + vars[1]}), vars[0], vars[1]))
            }

            //-------COMPARE-AND-SET-TO-BEST-DEVIANCY-------------
            var best = 1000.0
            calculationList.forEach{
                if(it[0] < best){ //If the best, do a change
                    best = it[0]
                    m    = it[1]
                    n    = it[2]
                }
            }
            if(best == calculationList[0][0]) //If current is optimal, return it
                return listOf(m,n)
        }
        //Debug: print the formula, looks nice :3
        //println("f(x)= ${String.format("%.3f",m)}x + ${String.format("%.3f",n)}  = ${String.format("%.3f",averageDeviancy(xList.map {(m * it) + n }))}")
        return listOf(m,n)
    }

    //Removes missing values from both list, missing values are indicated as < 0
    fun removeMissingValues(list1: List<Double>, list2: List<Double>): List<List<Double>>{
        if(list1.size != list2.size)
            return emptyList()
        val list1New = mutableListOf<Double>()
        val list2New = mutableListOf<Double>()
        for(i in 0..list1.size-1){
            if(list1[i] >= 0 && list2[i] >= 0){
                list1New.add(list1[i])
                list2New.add(list2[i])
            }
        }
        return listOf(list1New, list2New)
    }

    //Get list of  wants in given list
    private fun getPrimaryTitels(list : List<questionCollection>):List<String>{
        val testTitles = mutableListOf<String>()
        list.forEach {
            it.qList.forEach {
                if(it.type == 1)
                    testTitles.add(it.title)
            }
        }
        return testTitles
    }

    //Checks where a string is in a list, if not it'll be size of string
    private fun getIndexInList(string: String, list: List<relationCollection>): Int{
        for(i in 0..list.size-1)
            if(list[i].id == string )
                return i
        return list.size
    }

    fun collection2ListList(list : List<questionCollection>):List<relationCollection> {
        val listOfRatings = mutableListOf<relationCollection>()
        var numberLists = 0
        list.forEach {
            //Sort makes it easier to use in the future
            it.qList.sortBy {it.title}
            it.qList.sortBy{it.type}

            it.qList.forEach {
                val index =  getIndexInList(it.title, listOfRatings)
                if(listOfRatings.size == index){ //If not seen add to list
                    listOfRatings.add(relationCollection(it.title))
                    for(i in 0..numberLists-1) //Fill previous inputs not in list as NA (-1)
                        listOfRatings[index].rateList.add(-1.0)
                }
                listOfRatings[index].rateList.add(it.rate.toDouble())
            }
            numberLists++ //Counts number of inputs stored
            listOfRatings.forEach {
                while(it.rateList.size < numberLists) //If not added anything this iteration, add empty
                    it.rateList.add(-1.0)
            }

        }
        return listOfRatings
    }

    fun doRegression(list : List<questionCollection>): List<relationCollection>{
        val result = mutableListOf<relationCollection>()
        val structuredList = collection2ListList(list) // get question list in a sorted list of list based on questionTitles
        val testTitles = getPrimaryTitels(list) //Get Question that to test against

        structuredList.forEach {firstIt ->
            if(firstIt.id in testTitles) { //If want
                result.add(relationCollection(firstIt.id))
                structuredList.forEach { secondIt ->
                    if (firstIt != secondIt) { //No need of comparing to itself
                        result[result.size - 1].titleList.add(secondIt.id)
                        val listRmMissing = removeMissingValues(secondIt.rateList, firstIt.rateList)
                        val regressionScore = regression(listRmMissing[0], listRmMissing[1])
                        result[result.size - 1].rateList.add(regressionScore[0])
                    }
                }
            }
        }
        return result
    }



    fun regressionOld(yList: List<Double>, xList:List<Double>):List<Double>{
        var learningRate = 0.01 //Tuning of learning rate
        val epochs = 1000      //Number of iterations
        var m = 0.0 //Start position of tilt
        var n = 0.0 //Start position of start

        /**
         * Find Average deviancy between prediction and given list
         */
        fun averageDeviancy(predictionList: List<Double>):Double{
            var tot = 0.0
            (0..predictionList.size-1).forEach {
                tot += (predictionList[it] - yList[it]).pow(2)
            }
            return sqrt(tot)/predictionList.size //Returns avg deviancy of function
        }

        (0..epochs).forEach{
            //-------CHANGE 'm'-------------------
            var standard = averageDeviancy(xList.map {(m * it) + n})                    //Default
            val tiltUp = averageDeviancy(xList.map {((m + learningRate) * it) + n})     //Increase *x
            val tiltDown = averageDeviancy(xList.map {((m - learningRate) * it) + n})   //Decrease *x

            //tilt if it makes it better
            if(tiltUp < standard)
                m += learningRate
            else if(tiltDown <standard)
                m -= learningRate

            //-------CHANGE 'n'-------------------
            standard = averageDeviancy(xList.map {(m * it) + n })                   //Default
            val increase = averageDeviancy(xList.map {(m * it) + n + learningRate}) //Increase
            val decrease = averageDeviancy(xList.map {(m * it) + n - learningRate}) //Decrease

            //Increase start
            if(increase < standard)
                n += learningRate
            else if (decrease < standard)
                n -= learningRate
        }
        //Debug: print the formula, looks nice :3
        //println("f(x)= ${String.format("%.3f",m)}x + ${String.format("%.3f",n)}  = ${String.format("%.3f",averageDeviancy(xList.map {(m * it) + n }))}")
        return listOf(m,n)
    }
}