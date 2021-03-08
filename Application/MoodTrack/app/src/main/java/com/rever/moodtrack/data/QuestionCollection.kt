package com.rever.moodtrack.data

import com.rever.moodtrack.data.QuestionStore.Question

data class QuestionCollection(
    var id : String,
    val qList: MutableList<Question> = mutableListOf()
)


