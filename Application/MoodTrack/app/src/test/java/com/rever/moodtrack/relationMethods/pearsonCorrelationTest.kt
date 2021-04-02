package com.rever.moodtrack.relationMethods

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.data.Question
import org.junit.Test

class PearsonCorrelationTest {

    //-------BASIC-PEARSON-FUNCTIONALITY-----
    @Test
    fun emptyArrayGiven() {
        val list1 = listOf<Double>()
        val result = Correlation.pearsonCorrelation(list1, list1)

        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun differentArrayGiven() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 2.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun correlatedLists() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 4.0, 5.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun negativeCorrelatedLists() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 2.0, 1.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun complicatedCorrelation() {
        val list1 = listOf(17.0, 13.0, 12.0, 15.0, 16.0, 14.0, 16.0, 16.0, 18.0, 19.0)
        val list2 = listOf(94.0, 73.0, 59.0, 80.0, 93.0, 85.0, 66.0, 79.0, 77.0, 91.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        assertThat(result).isAtLeast(0.596)
        assertThat(result).isAtMost(0.597)
    }

    @Test
    fun equalInputCorrelation() {
        val list1 = listOf(5.0, 5.0, 5.0, 5.0)
        val list2 = listOf(4.0, 4.0, 4.0, 4.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        //LastStep to be variation in data to be correlation
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun oneEqualInputCorrelation() {
        val list1 = listOf(1.0, 1.0, 2.0, 3.0)
        val list2 = listOf(4.0, 4.0, 4.0, 4.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        //LastStep to be variation in data to be correlation
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun emptyListGiving(){
        val list = mutableListOf<questionCollection>()
        val result = Correlation.doPearson(list)
        assertThat(result).isEmpty()
    }

    @Test
    fun twoListNotNormalized() {
        val list1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val list2 = listOf(2.0, 9.0, 43.0, 82.0, 90.0)
        val result = Correlation.pearsonCorrelation(list1, list2)

        //Used to make sure pearson and spearman methods are different
        assertThat(result).isAtMost(0.974)
        assertThat(result).isAtLeast(0.973)
    }

    //-------APP-EXAMPLE-INPUTS-------

    @Test
    fun oneSubjectiveGiving(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1",1,6+i))
            qcoll.qList.add(Question("Trait1",0, i))
            qcoll.qList.add(Question("Trait2",0,8 - i))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        assertThat(result.size).isEqualTo(1)
    }
    @Test
    fun oneSubjectiveGivingLog(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1",1,6+i))
            qcoll.qList.add(Question("Trait1",0, i*i))
            qcoll.qList.add(Question("Trait2",0,8 - i*i))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        println(result[0].rateList)
        assertThat(result[0].rateList[0]).isAtMost(0.96)
        assertThat(result[0].rateList[0]).isAtLeast(0.95)

        assertThat(result[0].rateList[1]).isAtMost(-0.96)
        assertThat(result[0].rateList[1]).isAtLeast(-0.97)
    }


    @Test
    fun twoSubjectiveGiving(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1",1,6+i))
            qcoll.qList.add(Question("Mood2",1, i))
            qcoll.qList.add(Question("Trait1",0, i))
            qcoll.qList.add(Question("Trait2",0,8 - i))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun twoSubjectivGivenComparisonCount(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1" ,1,6+i))
            qcoll.qList.add(Question("Mood2" ,1, i))
            qcoll.qList.add(Question("Trait1",0, i))
            qcoll.qList.add(Question("Trait2",0,8 - i))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        assertThat(result[0].rateList.size).isEqualTo(3)
    }

    @Test
    fun unbalancedComparison(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1",1,6+i))
            qcoll.qList.add(Question("Mood2",1, i))
            qcoll.qList.add(Question("Trait1",0, i))
            qcoll.qList.add(Question("Trait2",0,8 - i))
            if(i == 2)
                qcoll.qList.add(Question("TraitOnce", 0, i, ))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        println(result)
        assertThat(result[0].rateList.size).isEqualTo(4)
    }

    @Test
    fun correctInputAllTablesInTact(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1",1,6+i))
            qcoll.qList.add(Question("Trait1",0, i))
            list.add(qcoll)
        }
        val result = Correlation.fillCorrelationList(list)
        //Get list of all given values
        val resultTitle = mutableListOf<String>()
        result.forEach {
            resultTitle.add(it.id)
        }
        assertThat(resultTitle).contains("Mood1")
        assertThat(resultTitle).contains("Trait1")
        assertThat(resultTitle.size).isEqualTo(2)
        //Make sure all needs contain same number of rates
        for(i in 0..2-1)
            for(x in 0..2 -1)
                assertThat(result[i].rateList.size).isEqualTo(result[x].rateList.size)
    }
    @Test
    fun unbalancedInput(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1",1,6+i))
            qcoll.qList.add(Question("Trait1",0, i))
            if(i == 2)
                qcoll.qList.add(Question("Trait2", 0, i))
            list.add(qcoll)
        }
        val result = Correlation.fillCorrelationList(list)//Get list of all given values
        val resultTitle = mutableListOf<String>()
        result.forEach {
            resultTitle.add(it.id)
        }
        assertThat(resultTitle).contains("Mood1")
        assertThat(resultTitle).contains("Trait1")
        assertThat(resultTitle).contains("Trait2")
        assertThat(resultTitle.size).isEqualTo(3)
        //Make sure all needs contain same number of rates
        for(i in 0..3-1)
            for(x in 0..3-1)
                assertThat(result[i].rateList.size).isEqualTo(result[x].rateList.size)
    }

}