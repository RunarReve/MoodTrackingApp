package com.rever.moodtrack.relationMethods

import com.google.common.truth.Truth
import com.rever.moodtrack.data.QuestionStore.Question
import com.rever.moodtrack.data.questionCollection
import org.junit.Test

class SpearmanCorrelationTest {

    //-------BASIC-PEARSON-FUNCTIONALITY-----

    @Test
    fun rankedTestEmpty(){
        val list1 = listOf<Double>()
        val result = Correlation.rank(list1)
        Truth.assertThat(result).isEmpty()
    }

    @Test
    fun rankedTestSimple(){
        val list1 = listOf(3.0, 2.0, 1.0)
        val result = Correlation.rank(list1)
        Truth.assertThat(result).isEqualTo(listOf(3.0, 2.0, 1.0))
    }

    @Test
    fun rankedTestShuffle(){
        val list1 = listOf(3.0, 1.0, 2.0)
        val result = Correlation.rank(list1)
        Truth.assertThat(result).isEqualTo(listOf(3.0, 1.0, 2.0))
    }

    @Test
    fun rankedTestOneDuplicate(){
        val list1 = listOf(4.0, 1.0, 1.0)
        val result = Correlation.rank(list1)
        Truth.assertThat(result).isEqualTo(listOf(3.0, 1.0, 1.0))
    }

    @Test
    fun rankedTestManyValues(){
        val list1 = listOf(4.0, 1.0, 2.0, 1.0,10.0,1.0)
        val result = Correlation.rank(list1)

        Truth.assertThat(result).isEqualTo(listOf(5.0, 1.0,4.0, 1.0, 6.0, 1.0))
    }

    @Test
    fun emptyArrayGiven() {
        val list1 = listOf<Double>()
        val result = Correlation.spearmanCorrelation(list1, list1)

        Truth.assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun differentArrayGiven() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 2.0)
        val result = Correlation.spearmanCorrelation(list1, list2)

        Truth.assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun correlatedLists() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 4.0, 5.0)
        val result = Correlation.spearmanCorrelation(list1, list2)

        Truth.assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun twoListNotNormalized() {
        val list1 = listOf(1.0, 2.0, 3.0, 4.0, 5.0)
        val list2 = listOf(2.0, 9.0, 43.0, 82.0, 90.0)
        val result = Correlation.spearmanCorrelation(list1, list2)

        //Used to make sure pearson and spearman methods are different
        Truth.assertThat(result).isEqualTo(1.0)
    }

    //-------APP-EXAMPLE-INPUTS-------

    @Test
    fun oneSubjectiveGivingLinear(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i, 0))
            list.add(qcoll)
        }
        var result = Correlation.doSpearman(list)
        Truth.assertThat(result.size).isEqualTo(1)
        result = Correlation.doPearson(list)
    }

    @Test
    fun oneSubjectiveGivingLog(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i*i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i*i, 0))
            list.add(qcoll)
        }
        val result = Correlation.doSpearman(list)
        Truth.assertThat(result[0].rateList[0]).isEqualTo(1.0)
        Truth.assertThat(result[0].rateList[1]).isEqualTo(-1.0)
    }

    @Test
    fun twoSubjectiveGiving(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood2",  8-i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i, 0))
            list.add(qcoll)
        }
        val result = Correlation.doSpearman(list)
        Truth.assertThat(result.size).isEqualTo(2)

        Truth.assertThat(result[0].rateList[0]).isEqualTo(1.0)
        Truth.assertThat(result[0].rateList[1]).isEqualTo(-1.0)
        Truth.assertThat(result[0].rateList[1]).isEqualTo(-1.0)

        Truth.assertThat(result[1].rateList[0]).isEqualTo(-1.0)
        Truth.assertThat(result[1].rateList[1]).isEqualTo(1.0)
        Truth.assertThat(result[1].rateList[1]).isEqualTo(1.0)
    }

    @Test
    fun unbalancedComparison(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1",  i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", 8 - i, 0))
            if(i > 2)
                qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", i, 0))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        Truth.assertThat(result[0].rateList.size).isEqualTo(2)

        Truth.assertThat(result[0].rateList[0]).isEqualTo(-1.0)
        Truth.assertThat(result[0].rateList[1]).isEqualTo(1.0)
    }

}