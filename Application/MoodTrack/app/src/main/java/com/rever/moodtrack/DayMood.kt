package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_day_mood.*
import kotlinx.android.synthetic.main.activity_main.*

class DayMood : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_mood)

        val actionBar = supportActionBar
        actionBar!!.title = "Enter Mood of day"
        actionBar.setDisplayHomeAsUpEnabled(true)


        btnNext.setOnClickListener {
            val intent = Intent(this, DayQuestions::class.java)
            startActivity(intent)
        }


    }
}