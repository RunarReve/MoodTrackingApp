package com.rever.moodtrack.data.LastStep

import androidx.lifecycle.LiveData

class StepRepository(
        private val stepDao: StepDao
) {
    val readAllData: LiveData<List<LastStep>> = stepDao.readAllData()
    val getLastStep: LastStep = stepDao.getLastStep()

    /*fun getLastStep(): LastStep {
        return stepDao.getLastStep()
    }*/

    //Add a new need
    suspend fun addNeed(need: LastStep){
        stepDao.addNeed(need)
    }

    //Delete all local custom needs
    fun deleteLocalData(){
        stepDao.deleteLocalData()
    }


}