package com.rever.moodtrack.relationMethods

import com.rever.moodtrack.data.PearsonCollection
import com.rever.moodtrack.data.QuestionCollection
import kotlin.math.pow
import kotlin.math.sqrt

object pearsonCorrelation {

    fun pearsonCorrelation(list1: List<Double>, list2: List<Double>): Double{
        if(list1.size != list2.size){
            return 0.0
        }
        if(list1.size == 0){ //because previous if list2 has to be same size as list1
            return 0.0
        }
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

    fun questionCol2PearsonCor(list : List<QuestionCollection>): List<Double>{
        if(list.size == 0)
            return listOf(0.0)

        var mainList = mutableListOf<Double>()
        var listOfLists = mutableListOf<Double>()

        //Find list of mood
        //TODO method of having two moods
        list.forEach {
            it.qList.forEach{
                if(it.isPrimary == 1)
                    mainList.add(it.rate.toDouble())
            }
        }

        val result = arrayListOf<Double>()
        //test each question
        for (question in 0..list[0].qList.size-1) {
            var currentQTitle = list[0].qList[question].questionTitle //Current question title
            listOfLists.clear()
            list.forEach {
                it.qList.forEach {
                    if (it.isPrimary != 1 && it.questionTitle == currentQTitle)
                        listOfLists.add(it.rate.toDouble())
                }
            }
            if (listOfLists.size != 0){
                result.add(pearsonCorrelation(mainList, listOfLists))
            }
        }
        return result
    }


    fun getQuestionTitle(list : List<QuestionCollection>): List<String>{
        if(list.size == 0)
            return listOf("")
        var titleList = mutableListOf<String>()
        list[0].qList.forEach{
            if (it.isPrimary == 0)
                titleList.add(it.questionTitle)
        }
        return titleList
    }

    fun doAll(list : List<QuestionCollection>): MutableList<PearsonObject>{
        val titles = getQuestionTitle(list)
        val scores = questionCol2PearsonCor(list)
        return merge2Object(titles,scores)
    }

    fun merge2Object(titles: List<String>, scores: List<Double> ): MutableList<PearsonObject>{
        val result = mutableListOf<PearsonObject>()
        for (i in titles.indices){
            val new = PearsonObject(titles[i],scores[i],0)
            result.add(new)
        }
        result.sortedBy{it.title}
        return result
    }

    //Checks where a string is in a list, if not it'll be size of string
    private fun checkStringInList(string: String, list: List<PearsonCollection>): Int{
        for(i in 0..list.size-1)
            if(list[i].id == string )
                return i
        return list.size
    }

    fun doPearson(list : List<QuestionCollection>): List<PearsonCollection>{
        val pearsonList = mutableListOf<PearsonCollection>()
        val prePearsonList = mutableListOf<PearsonCollection>()
        val testTitles = mutableListOf<String>()
        var numberLists = 0
        list.forEach {
            it.qList.forEach {
                val index = checkStringInList(it.questionTitle, prePearsonList)
                if(prePearsonList.size == index){
                    prePearsonList.add(PearsonCollection(it.questionTitle))
                    if(it.isPrimary == 1)
                        testTitles.add(it.questionTitle)
                    for(i in 0..numberLists-1){ //If not empty values before, add now
                        prePearsonList[index].rateList.add(-1.0)
                    }
                }
                prePearsonList[index].rateList.add(it.rate.toDouble())
            }
            numberLists++ //Counts amount of question gone through, in case new get added
        }
        prePearsonList.forEach{
            val current = it
            if(current.id in testTitles) {
                pearsonList.add(PearsonCollection(current.id))

                prePearsonList.forEach {
                    if (current != it) {
                        pearsonList[pearsonList.size - 1].rateList.add(pearsonCorrelation(current.rateList, it.rateList))
                        pearsonList[pearsonList.size - 1].titleList.add(it.id)
                    }
                }
            }
        }
        return pearsonList
    }

}