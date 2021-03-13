package com.rever.moodtrack.data

data class relationCollection(
        var id: String,
        var titleList: MutableList<String> = mutableListOf(),
        var rateList: MutableList<Double> = mutableListOf()
)
