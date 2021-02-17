package com.rever.moodtrack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT * FROM question_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Question>>

    @Query("SELECT * FROM question_table ORDER BY id ASC")
    fun readAllDataList():List<Question>


    @Query("DELETE FROM question_table")
    fun deleteLocalData()

    //TODO add get all same name
}