package com.rever.moodtrack.data

import androidx.lifecycle.LiveData

class QuestionRepository(
        private val questionDao: QuestionDao
){
    val readAllData: LiveData<List<Question>> = questionDao.readAllData()

    //Add a new question
    suspend fun addQuestion(question: Question){
        questionDao.addQuestion(question)
    }

    //Delete a select question
    suspend fun deleteQuestion(question: Question){
        questionDao.deleteQuestion(question)
    }

    //Delete all local data
    fun deleteLocalData(){
        questionDao.deleteLocalData()
    }

}