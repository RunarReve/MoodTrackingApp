package com.rever.moodtrack.data.QuestionStore

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

    //Just count all needTitles for 'Mood' as every instance will have it
    @Query("SELECT count(*) FROM question_table WHERE questionTitle ='Mood' ")
    fun getNumberOfInputSets(): LiveData<Int>



    @Query("DELETE FROM question_table")
    fun deleteLocalData()

    //TODO add get all same name
}