package com.rever.moodtrack.data

data class User(
    var userId: String,
) {
    var ethnicity = ""
    var gender = ""
    var ageGroup = ""
    var nationality = ""
    var postCode = ""
    var allow4Study = "No"
}