package com.rever.moodtrack

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.rever.moodtrack.data.QuestionDatabase
import com.rever.moodtrack.data.QuestionRepository
import com.rever.moodtrack.data.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuestionViewModel(application: Application):AndroidViewModel(application){

    val readAllData: LiveData<List<Question>>
    private val repository: QuestionRepository

    init {
        val questionDao = QuestionDatabase.getDatabase(application).questionDao()
        repository = QuestionRepository(questionDao)
        readAllData = repository.readAllData
    }

    fun addQuestion(question: Question){
        viewModelScope.launch(Dispatchers.IO){
            repository.addQuestion(question)
        }
    }
    fun deleteQuestion(question: Question){
        viewModelScope.launch(Dispatchers.IO){
            repository.deleteQuestion(question)
        }
    }

    fun deleteLocalData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocalData()
        }
    }

}