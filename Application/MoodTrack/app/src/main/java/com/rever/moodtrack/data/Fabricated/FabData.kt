package com.rever.moodtrack.data.Fabricated

import com.rever.moodtrack.data.QuestionStore.Question
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.data.Fabricated.Data4Fab.listO

object FabData {

    fun addData(): List<questionCollection> {
        val list = mutableListOf<questionCollection>()
        var i = 0
        listO.forEach {
            val newDay = questionCollection("Fabricated_${i}")
            newDay.qList.add(Question(1, "0FabricatedInput${i}", "Fabricated", "Happiness", it[0], 0))
            newDay.qList.add(Question(0, "0FabricatedInput${i}", "Fabricated", "Sleep", it[1], 0))
            newDay.qList.add(Question(0, "0FabricatedInput${i}", "Fabricated", "Movement", it[2], 0))
            newDay.qList.add(Question(0, "0FabricatedInput${i}", "Fabricated", "Social", it[3], 0))
            list.add(newDay)
            i += 1
        }
        return list
    }
}