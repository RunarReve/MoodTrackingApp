package com.rever.moodtrack.data.NeedStore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "need_table")
data class Need(
    val isPrimary: Int, // 0 is active need(sleep, movement) 1 is subjective need(mood, productivity)
    val needTitle: String,
    val userID: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)