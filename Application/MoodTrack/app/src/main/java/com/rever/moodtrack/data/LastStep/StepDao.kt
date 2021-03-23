package com.rever.moodtrack.data.LastStep

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface StepDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNeed(step: LastStep)

    @Query("SELECT * FROM step_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<LastStep>>

    @Query("SELECT * from step_table order by ROWID DESC limit 1")
    fun getLastStep(): LastStep

    @Query("DELETE FROM step_table")
    fun deleteLocalData()


}