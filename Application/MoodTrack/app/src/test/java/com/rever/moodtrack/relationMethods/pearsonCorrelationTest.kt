package com.rever.moodtrack.relationMethods


import com.google.common.truth.Truth.assertThat
import com.rever.moodtrack.data.QuestionCollection
import com.rever.moodtrack.data.QuestionStore.Question
import org.junit.Test

class pearsonCorrelationTest {

    @Test
    fun emptyArrayGiven() {
        val list1 = listOf<Double>()
        val result = pearsonCorrelation.pearsonCorrelation(list1, list1)

        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun differentArrayGiven() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 2.0)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun correlatedLists() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 4.0, 5.0)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun negativeCorrelatedLists() {
        val list1 = listOf(1.0, 2.0, 3.0)
        val list2 = listOf(3.0, 2.0, 1.0)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun complicatedCorrelation() {
        val list1 = listOf(17.0, 13.0, 12.0, 15.0, 16.0, 14.0, 16.0, 16.0, 18.0, 19.0)
        val list2 = listOf(94.0, 73.0, 59.0, 80.0, 93.0, 85.0, 66.0, 79.0, 77.0, 91.0)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isAtLeast(0.596)
        assertThat(result).isAtMost(0.597)
    }

    @Test
    fun equalInputCorrelation() {
        val list1 = listOf(5.0, 5.0, 5.0, 5.0)
        val list2 = listOf(4.0, 4.0, 4.0, 4.0)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        //Need to be variation in data to be correlation
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun oneEqualInputCorrelation() {
        val list1 = listOf(1.0, 1.0, 2.0, 3.0)
        val list2 = listOf(4.0, 4.0, 4.0, 4.0)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        //Need to be variation in data to be correlation
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun emptyListGiving(){
        val list = mutableListOf<QuestionCollection>()
        val result = pearsonCorrelation.doPearson(list)
        assertThat(result).isEmpty()
    }

    @Test
    fun oneSubjectiveGiving(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5) {
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i, 0))
            list.add(qcoll)
        }
        val result = pearsonCorrelation.doPearson(list)
        assertThat(result.size).isEqualTo(1)
    }

    @Test
    fun twoSubjectiveGiving(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5) {
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood2",  i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i, 0))
            list.add(qcoll)
        }
        val result = pearsonCorrelation.doPearson(list)
        assertThat(result.size).isEqualTo(2)
    }

    @Test
    fun twoSubjectivGivenComparisonCount(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5) {
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood2",  i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i, 0))
            list.add(qcoll)
        }
        val result = pearsonCorrelation.doPearson(list)
        assertThat(result[0].rateList.size).isEqualTo(3)
    }

    @Test
    fun unbalancedComparison(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5) {
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood2",  i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", 8 - i, 0))
            if(i == 2)
                qcoll.qList.add(Question(0, "NULL", "Day${i}", "TraitOnce", i, 0))
            list.add(qcoll)
        }
        val result = pearsonCorrelation.doPearson(list)
        println(result)
        assertThat(result[0].rateList.size).isEqualTo(4)
    }

    @Test
    fun correctInputAllTablesInTact(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5) {
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            list.add(qcoll)
        }
        val result = pearsonCorrelation.questionCollection2PearsonCollection(list)
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
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5) {
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1, "NULL", "Day${i}", "Mood1", 6 + i, 0))
            qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait1", i, 0))
            if(i == 2)
                qcoll.qList.add(Question(0, "NULL", "Day${i}", "Trait2", i, 0))
            list.add(qcoll)
        }
        val result = pearsonCorrelation.questionCollection2PearsonCollection(list)//Get list of all given values
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
            for(x in 0..3 -1)
                assertThat(result[i].rateList.size).isEqualTo(result[x].rateList.size)
    }

}