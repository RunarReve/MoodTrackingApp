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

}
class pearsonCorrelationQuestionTest{
    @Test
    fun emptyQuestionCollection(){
        val list = mutableListOf<QuestionCollection>()
        val result = pearsonCorrelation.questionCol2PearsonCor(list)

        assertThat(result).isEqualTo(listOf(0.0))
    }
    @Test
    fun inequalNOfQuestion(){
        val list = mutableListOf<QuestionCollection>()
        var qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(1,"NULL","Day1","Mood1", 5, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait1", 4, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait2", 2, 0))
        list.add(qcoll)

        qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(1,"NULL","Day1","Mood1", 6, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait1", 5, 0))
        list.add(qcoll)

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0, 0.0))
    }
    @Test
    fun shuffeledQuestion(){
        val list = mutableListOf<QuestionCollection>()
        var qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(1,"NULL","Day1","Mood1", 5, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait1", 4, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait2", 2, 0))
        list.add(qcoll)

        qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(1,"NULL","Day1","Mood1", 6, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait2", 1, 0))
        qcoll.qList.add(Question(0,"NULL","Day1","Trait1", 5, 0))
        list.add(qcoll)

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0, -1.0))
    }

    @Test
    fun oenTraitFullyCorrelatedQuestion(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1,"NULL","Day${i}","Mood1", i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait1", i, 0))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0))
    }

    @Test
    fun fiveFullyCorrelatedQuestion(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1,"NULL","Day${i}","Mood1", i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait1", i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait2", i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait3", i, 0))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0,1.0,1.0))
    }

    @Test
    fun positiveAndNegativeCollection(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1,"NULL","Day${i}","Mood1", 6+i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait1", i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait2", 8-i, 0))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0,-1.0))
    }

    @Test
    fun getTitleArray(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            val qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(1,"NULL","Day${i}","Mood1", 6+i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait1", i, 0))
            qcoll.qList.add(Question(0,"NULL","Day${i}","Trait2", 8-i, 0))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.getQuestionTitle(list)
        assertThat(result).isEqualTo(listOf("Trait1", "Trait2"))
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
}