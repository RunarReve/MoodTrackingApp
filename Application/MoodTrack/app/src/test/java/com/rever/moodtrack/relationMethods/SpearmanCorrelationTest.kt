package com.rever.moodtrack.relationMethods

import com.google.common.truth.Truth
import com.rever.moodtrack.data.Question
import com.rever.moodtrack.data.questionCollection
import org.junit.Test

class SpearmanCorrelationTest {

    //-------UNIT-TESTS-----

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

    //-------INTEGRATION-TESTS-------

    @Test
    fun oneSubjectiveGivingLinear(){
        val list = mutableListOf<questionCollection>()
        for (i in 0..5) {
            val qcoll = questionCollection("Day${i}")
            qcoll.qList.add(Question("Mood1", 1, 6 + i))
            qcoll.qList.add(Question("Trait1",0, i))
            qcoll.qList.add(Question("Trait2",0, 8 - i))
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
            qcoll.qList.add(Question("Mood1", 1, 6 + i))
            qcoll.qList.add(Question("Trait1",0, i*i))
            qcoll.qList.add(Question("Trait2",0, 8 - i*i))
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
            qcoll.qList.add(Question("Mood1", 1, 6 + i))
            qcoll.qList.add(Question("Mood2", 1, 8 - i))
            qcoll.qList.add(Question("Trait1",0, i))
            qcoll.qList.add(Question("Trait2",0, 8 - i))
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
            qcoll.qList.add(Question("Mood1", 1, i))
            qcoll.qList.add(Question("Trait1",0, 8 - i))
            if(i > 2)
                qcoll.qList.add(Question("Trait2",0, i))
            list.add(qcoll)
        }
        val result = Correlation.doPearson(list)
        Truth.assertThat(result[0].rateList.size).isEqualTo(2)

        Truth.assertThat(result[0].rateList[0]).isEqualTo(-1.0)
        Truth.assertThat(result[0].rateList[1]).isEqualTo(1.0)
    }

}