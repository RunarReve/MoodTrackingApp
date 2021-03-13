package com.rever.moodtrack.relationMethods

import com.rever.moodtrack.data.PearsonCollection
import com.rever.moodtrack.data.QuestionCollection
import com.rever.moodtrack.data.regressionScore
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * This object has been inspired by Thomas Nields "linear_regression_gradient_decent.kts"
 * Link:https://gist.github.com/thomasnield/fbe2e2205233388577e6abe6f5bbe897#file-linear_regression_gradient_descent-kts
 *
 * Major modification has been done to fit with this program and make it more optimal for its purpose
 */
object Regression {

    fun regression(yList: List<Double>, xList:List<Double>):List<Double>{
        val learningRate = 0.01 //Tuning of learning rate
        val epochs = 1000      //Number of iterations
        var m = 0.0 //Start position of tilt
        var n = 2.0 //Start position of start

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
            var standard = averageDeviancy(xList.map {(m * it) + n })               //Default
            val tilt = averageDeviancy(xList.map {((m+ learningRate) * it) + n })   //Increase *x

            if(standard < tilt)
                m -= learningRate
            else
                m += learningRate

            //-------CHANGE 'n'-------------------
            standard = averageDeviancy(xList.map {(m * it) + n })                   //Default
            val rotation = averageDeviancy(xList.map {(m * it) + n + learningRate}) //Rotate

            if(standard < rotation)
                n -= learningRate
            else
                n += learningRate
        }
        //Debug: print the formula, looks nice :3
        //println("f(x)= ${String.format("%.3f",m)}x + ${String.format("%.3f",n)}")
        return listOf(m,n)
    }

    //Get list of  wants in given list
    fun getPrimaryTitels(list : List<QuestionCollection>):List<String>{
        val testTitles = mutableListOf<String>()
        list.forEach {
            it.qList.forEach {
                if(it.isPrimary == 1)
                    testTitles.add(it.questionTitle)
            }
        }
        return testTitles
    }

    //Checks where a string is in a list, if not it'll be size of string
    private fun getIndexinList(string: String, list: List<PearsonCollection>): Int{
        for(i in 0..list.size-1)
            if(list[i].id == string )
                return i
        return list.size
    }

    private fun questionCollection2Regression(list : List<QuestionCollection>):List<PearsonCollection> {
        val listOfRatings = mutableListOf<PearsonCollection>()
        var numberLists = 0
        list.forEach {
            //Sort makes it easier to use in the future
            it.qList.sortBy {it.name}
            it.qList.sortBy{it.isPrimary}

            it.qList.forEach {
                val index =  getIndexinList(it.questionTitle, listOfRatings)
                if(listOfRatings.size == index){ //If not seen add to list
                    listOfRatings.add(PearsonCollection(it.questionTitle))
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

    fun doRegression(list : List<QuestionCollection>): List<PearsonCollection>{
        val result = mutableListOf<PearsonCollection>()
        val structuredList = questionCollection2Regression(list) // get question list in a sorted list of list based on questionTitles
        val testTitles = getPrimaryTitels(list) //Get Question that to test against

        structuredList.forEach {firstIt ->
            if(firstIt.id in testTitles) { //If want
                result.add(PearsonCollection(firstIt.id))
                structuredList.forEach { secondIt ->
                    if (firstIt != secondIt) { //No need of comparing to itself
                        result[result.size - 1].titleList.add(secondIt.id)
                        val regressionScore = regression(secondIt.rateList, firstIt.rateList)
                        result[result.size - 1].rateList.add(regressionScore[0])
                    }
                }
            }
        }
        return result
    }
}