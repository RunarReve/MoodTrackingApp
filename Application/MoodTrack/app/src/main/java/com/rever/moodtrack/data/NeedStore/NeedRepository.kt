package com.rever.moodtrack.data.NeedStore

import androidx.lifecycle.LiveData

class NeedRepository(
        private val needDao: NeedDao
) {
    val readAllData: LiveData<List<Need>> = needDao.readAllData()

    //Add a new need
    suspend fun addNeed(need: Need){
        needDao.addNeed(need)
    }

    //Delete a select need
    suspend fun deleteNeed(need: Need){
        needDao.deleteNeed(need)
    }

    //Delete all local custom needs
    fun deleteLocalData(){
        needDao.deleteLocalData()
    }
}