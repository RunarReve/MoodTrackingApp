package com.rever.moodtrack.data.Fabricated

import com.rever.moodtrack.data.Question
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.data.Fabricated.Data4Fab.listO

object FabData {
    fun addData(): List<questionCollection> {
        val list = mutableListOf<questionCollection>()
        var i = 0
        listO.forEach {
            val newDay = questionCollection("Fabricated_${i}")
            newDay.qList.add(Question("Happiness",  1, it[0]))
            newDay.qList.add(Question("Sleep",      0, it[1]))
            newDay.qList.add(Question("Movement",   0, it[2]))
            newDay.qList.add(Question("Social",     0, it[3]))
            list.add(newDay)
            i += 1
        }
        return list
    }
}