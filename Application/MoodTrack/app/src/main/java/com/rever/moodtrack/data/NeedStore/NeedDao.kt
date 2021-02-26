package com.rever.moodtrack.data.NeedStore

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface NeedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNeed(need: Need)

    @Delete
    suspend fun deleteNeed(need: Need)

    @Query("SELECT * FROM need_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<Need>>

    @Query("SELECT * FROM need_table ORDER BY id ASC")
    fun readAllDataList():List<Need>

    @Query("DELETE FROM need_table")
    fun deleteLocalData()
}