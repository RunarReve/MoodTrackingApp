package com.rever.moodtrack.data.LastStep

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant.now
import java.time.LocalDate.now
import java.time.LocalTime.now
import java.util.*

class StepViewModel(application: Application):AndroidViewModel(application) {

    private val repository: StepRepository
    //private val lastStep : LiveData<LastStep>
    private var steps : Int = 0

    init{
        val stepDao = StepDatabase.getDatabase(application).stepDao()
        repository = StepRepository(stepDao)

    }

    fun updateStep(step: Int){
        viewModelScope.launch(Dispatchers.IO) {
            deleteLocalData()
            val stepItem = LastStep(step, 0 )
            repository.addNeed(stepItem)
        }
    }

    fun deleteLocalData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocalData()
        }
    }
}