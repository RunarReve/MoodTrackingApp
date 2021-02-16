package com.rever.moodtrack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuestion(question: Question)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("SELECT * FROM question_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Question>>


    @Query("DELETE FROM question_table")
    fun deletLocalData()

    //TODO add get all same name
}