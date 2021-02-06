package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_day_questions.*

class DayQuestions : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_questions)

        val actionBar = supportActionBar
        actionBar!!.title = "Needs fulfilled"
        actionBar.setDisplayHomeAsUpEnabled(true)

        btnNext.setOnClickListener {
            val intent = Intent(this, Statistics::class.java)
            startActivity(intent)
        }

    }
}