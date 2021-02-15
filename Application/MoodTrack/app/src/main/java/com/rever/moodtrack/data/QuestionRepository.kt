package com.rever.moodtrack.data

import androidx.lifecycle.LiveData

class QuestionRepository(
        private val questionDao: QuestionDao
){

    val readAllData: LiveData<List<QuestionStore>> = questionDao.readAllData()

    suspend fun addQuestion(questionStore: QuestionStore){
        questionDao.addQuestion(questionStore)
    }
    suspend fun deleteQuestion(questionStore: QuestionStore){
        questionDao.deleteQuestion(questionStore)
    }

    fun readAllData() = questionDao.readAllData()

}