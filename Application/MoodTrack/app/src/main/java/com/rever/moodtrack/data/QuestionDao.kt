package com.rever.moodtrack.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface QuestionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addQuestion(questionStore: QuestionStore)

    @Delete
    suspend fun deleteQuestion(questionStore: QuestionStore)

    @Query("SELECT * FROM question_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<QuestionStore>>

    //TODO add get all same name
}