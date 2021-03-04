package com.rever.moodtrack.data.NeedStore

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NeedViewModel(application: Application):AndroidViewModel(application) {

    val readAllData: LiveData<List<Need>>

    private val repository: NeedRepository

    init{
        val needDao = NeedDatabase.getDatabase(application).needDao()
        repository = NeedRepository(needDao)
        readAllData = repository.readAllData
    }

    fun addNeed(need: Need){
        viewModelScope.launch(Dispatchers.IO) {
            repository.addNeed(need)
        }
    }
    fun deleteNeed(need: Need){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNeed(need)
        }
    }
    fun deleteLocalData(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteLocalData()
        }
    }


}