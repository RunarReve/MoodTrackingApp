package com.rever.moodtrack.relationMethods

import com.rever.moodtrack.QuestionCollection
import kotlin.math.pow
import kotlin.math.sqrt

object pearsonCorrelation {

    fun pearsonCorrelation(list1: List<Int>, list2: List<Int>): Double{
        if(list1.size != list2.size){
            return 0.0
        }
        if(list1.size == 0){ //because previous if list2 has to be same size as list1
            return 0.0
        }
        val mean1 = list1.sum().toDouble()/list1.size
        val mean2 = list2.sum().toDouble()/list2.size

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

    fun questionCol2PearsonCor(list : List<QuestionCollection>): List<Double>{
        if(list.size == 0)
            return listOf(0.0)

        var mainList = mutableListOf<Int>()
        var listOfLists = mutableListOf<Int>()

        //Find list of mood
        list.forEach {
            it.qList.forEach{
                if(it.isPrimary == 1)
                    mainList.add(it.rate)
            }
        }

        val result = arrayListOf<Double>()
        //test each question
        for (question in 0..list[0].qList.size-1) {
            var currentQTitle = list[0].qList[question].questionTitle
            listOfLists.clear()
            list.forEach {
                it.qList.forEach {
                    if (it.isPrimary != 1 && it.questionTitle == currentQTitle) {
                        listOfLists.add(it.rate)
                    }
                }
            }
            if (listOfLists.size != 0){
                result.add(pearsonCorrelation(mainList, listOfLists))
            }
        }
        return result
    }
}