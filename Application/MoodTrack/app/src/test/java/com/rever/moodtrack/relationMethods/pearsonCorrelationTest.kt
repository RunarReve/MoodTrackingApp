package com.rever.moodtrack.relationMethods


import com.google.common.truth.Truth.assertThat
import com.rever.moodtrack.QuestionCollection
import com.rever.moodtrack.data.Question
import org.junit.Test

class pearsonCorrelationTest {

    @Test
    fun emptyArrayGiven() {
        val list1 = listOf<Int>()
        val result = pearsonCorrelation.pearsonCorrelation(list1, list1)

        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun differentArrayGiven() {
        val list1 = listOf(1, 2, 3)
        val list2 = listOf(3, 2)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun correlatedLists() {
        val list1 = listOf(1, 2, 3)
        val list2 = listOf(3, 4, 5)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(1.0)
    }

    @Test
    fun negativeCorrelatedLists() {
        val list1 = listOf(1, 2, 3)
        val list2 = listOf(3, 2, 1)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isEqualTo(-1.0)
    }

    @Test
    fun complicatedCorrelation() {
        val list1 = listOf(17, 13, 12, 15, 16, 14, 16, 16, 18, 19)
        val list2 = listOf(94, 73, 59, 80, 93, 85, 66, 79, 77, 91)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        assertThat(result).isAtLeast(0.596)
        assertThat(result).isAtMost(0.597)
    }

    @Test
    fun equalInputCorrelation() {
        val list1 = listOf(5, 5, 5, 5)
        val list2 = listOf(4, 4, 4, 4)
        val result = pearsonCorrelation.pearsonCorrelation(list1, list2)

        //Need to be variation in data to be correlation
        assertThat(result).isEqualTo(0.0)
    }

    @Test
    fun oneEqualInputCorrelation() {
        val list1 = listOf(1, 1, 2, 3)
        val list2 = listOf(4, 4, 4, 4)
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
        qcoll.qList.add(Question(0,1,"NULL","Day1","Mood1", 5))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait1", 4))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait2", 2))
        list.add(qcoll)

        qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(0,1,"NULL","Day1","Mood1", 6))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait1", 5))
        list.add(qcoll)

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0, 0.0))
    }
    @Test
    fun shuffeledQuestion(){
        val list = mutableListOf<QuestionCollection>()
        var qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(0,1,"NULL","Day1","Mood1", 5))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait1", 4))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait2", 2))
        list.add(qcoll)

        qcoll = QuestionCollection("Day1")
        qcoll.qList.add(Question(0,1,"NULL","Day1","Mood1", 6))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait2", 1))
        qcoll.qList.add(Question(0,0,"NULL","Day1","Trait1", 5))
        list.add(qcoll)

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0, -1.0))
    }

    @Test
    fun oenTraitFullyCorrelatedQuestion(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            var qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(0,1,"NULL","Day${i}","Mood1", i))
            qcoll.qList.add(Question(0,0,"NULL","Day${i}","Trait1", i))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0))
    }

    @Test
    fun fiveFullyCorrelatedQuestion(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            var qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(0,1,"NULL","Day${i}","Mood1", i))
            qcoll.qList.add(Question(0,0,"NULL","Day${i}","Trait1", i))
            qcoll.qList.add(Question(0,0,"NULL","Day${i}","Trait2", i))
            qcoll.qList.add(Question(0,0,"NULL","Day${i}","Trait3", i))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0,1.0,1.0))
    }

    @Test
    fun positiveAndNegativeCollection(){
        val list = mutableListOf<QuestionCollection>()
        for (i in 0..5){
            var qcoll = QuestionCollection("Day${i}")
            qcoll.qList.add(Question(0,1,"NULL","Day${i}","Mood1", 6+i))
            qcoll.qList.add(Question(0,0,"NULL","Day${i}","Trait1", i))
            qcoll.qList.add(Question(0,0,"NULL","Day${i}","Trait2", 8-i))
            list.add(qcoll)
        }

        val result = pearsonCorrelation.questionCol2PearsonCor(list)
        assertThat(result).isEqualTo(listOf(1.0,-1.0))
    }
}