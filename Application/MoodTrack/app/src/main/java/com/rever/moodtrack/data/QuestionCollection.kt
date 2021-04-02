package com.rever.moodtrack.data

import com.rever.moodtrack.data.QuestionStore.Question

data class questionCollection(
    var id : String,
    val qList: MutableList<FireQuestion> = mutableListOf()
)


