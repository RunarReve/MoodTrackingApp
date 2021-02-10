package com.rever.moodtrack

data class QuestionCollection(
    var id : String,
    val qList: MutableList<Question> = mutableListOf()
){
    fun getSize(): Int{
        return qList.size
    }
}
