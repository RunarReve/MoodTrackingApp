package com.rever.moodtrack.data.LastStep

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "step_table")
data class LastStep(
    val steps: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int,
)