package com.rever.moodtrack.relationMethods

import com.rever.moodtrack.data.PearsonCollection
import com.rever.moodtrack.data.QuestionCollection
import kotlin.math.pow
import kotlin.math.sqrt

object pearsonCorrelation {

    fun pearsonCorrelation(list1: List<Double>, list2: List<Double>): Double{
        if(list1.size != list2.size)
            return 0.0
        if(list1.size == 0) //because previous if list2 has to be same size as list1
            return 0.0
        val mean1 = list1.sum()/list1.size
        val mean2 = list2.sum()/list2.size

        var rUp = 0.0
        var rD1 = 0.0
        var rD2 = 0.0
        for(i in 0..list1.size-1){
            rUp += ((list1[i]-mean1)*(list2[i]-mean2))
            rD1 += (list1[i] - mean1).pow(2)
            rD2 += (list2[i] - mean2).pow(2)
        }
        val rD = sqrt(rD1 * rD2)

        //If there are no relation in the data, just return 0.0
        if (rD == 0.0)
            return 0.0
        return rUp/rD
    }

    //Checks where a string is in a list, if not it'll be size of string
    private fun getIndexinList(string: String, list: List<PearsonCollection>): Int{
        for(i in 0..list.size-1)
            if(list[i].id == string )
                return i
        return list.size
    }

    fun questionCollection2PearsonCollection(list : List<QuestionCollection>):List<PearsonCollection>{
        val pearsonList = mutableListOf<PearsonCollection>()
        var numberLists = 0

        //Store all the sets of data in a structure based of the different needs
        list.forEach {
            //Sort makes it easier to use in the future
            it.qList.sortBy {it.name}
            it.qList.sortBy{it.isPrimary}

            it.qList.forEach {
                val index = getIndexinList(it.questionTitle, pearsonList) //Get index of title
                if(pearsonList.size == index){  //If not seen add to list
                    pearsonList.add(PearsonCollection(it.questionTitle))
                    for(i in 0..numberLists-1) //If recently added...
                        pearsonList[index].rateList.add(-1.0) //... fill past data points
                }
                pearsonList[index].rateList.add(it.rate.toDouble())
            }
            numberLists++ //Counts number of iterations stored
            pearsonList.forEach {
                while(it.rateList.size < numberLists) //If not added anything this iteration, add empty
                    it.rateList.add(-1.0)
            }
        }
        //To get the list in ascending order
        pearsonList.forEach {
            it.rateList.reverse()
        }
        return pearsonList
    }

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

    fun doPearson(list : List<QuestionCollection>): List<PearsonCollection>{
        val pearsonList = mutableListOf<PearsonCollection>()
        val prePearsonList = questionCollection2PearsonCollection(list) // get question list in a sorted list of list based on questionTitles
        val testTitles = getPrimaryTitels(list) //Get Question that to test against

        //Do the Pearson test for between each subjective need against other needs
        prePearsonList.forEach{
            val current = it
            if(current.id in testTitles) { //Check if current is in need for testing
                pearsonList.add(PearsonCollection(current.id))

                prePearsonList.forEach {
                    if (current != it) {//No need to test against itself
                        pearsonList[pearsonList.size - 1].rateList.add(pearsonCorrelation(current.rateList, it.rateList))
                        pearsonList[pearsonList.size - 1].titleList.add(it.id)
                    }
                }
            }
        }
        return pearsonList
    }
}