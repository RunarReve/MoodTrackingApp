package com.rever.moodtrack

import com.rever.moodtrack.data.Question

data class QuestionCollection(
    var id : String,
    val qList: MutableList<Question> = mutableListOf()
) {
    fun getSize(): Int {
        return qList.size
    }
}


