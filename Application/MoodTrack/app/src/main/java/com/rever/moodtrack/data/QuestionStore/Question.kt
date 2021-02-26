package com.rever.moodtrack.data.QuestionStore

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class Question (
    val isPrimary: Int, //isPrimary is checking if the question is a primary asked question, aka mood
    val time: String,
    val name: String,
    val questionTitle: String,
    var rate: Int,
     @PrimaryKey(autoGenerate = true)
    val id: Int,
    )