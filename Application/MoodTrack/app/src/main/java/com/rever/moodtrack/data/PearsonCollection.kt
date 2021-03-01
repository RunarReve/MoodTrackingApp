package com.rever.moodtrack.data

import com.rever.moodtrack.data.QuestionStore.Question

data class PearsonCollection(
        var id: String,
        var titleList: MutableList<String> = mutableListOf(),
        var rateList: MutableList<Double> = mutableListOf()
)
