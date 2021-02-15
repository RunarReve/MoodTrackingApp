package com.rever.moodtrack

import com.rever.moodtrack.data.QuestionStore

data class QuestionCollection(
    var id : String,
    val qList: MutableList<QuestionStore> = mutableListOf()
){
    fun getSize(): Int{
        return qList.size
    }
}
