package com.rever.moodtrack.data

data class questionCollection(
    var id : String,
    val qList: MutableList<com.rever.moodtrack.data.Question> = mutableListOf()
)


