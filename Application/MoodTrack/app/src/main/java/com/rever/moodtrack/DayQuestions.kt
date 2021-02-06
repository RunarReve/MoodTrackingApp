package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_day_questions.*

class DayQuestions : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_questions)

        questionAdapter = QuestionAdapter(mutableListOf())
        questionAdapter.addQuestion(Question("Sleep"))
        questionAdapter.addQuestion(Question("Movement"))
        questionAdapter.addQuestion(Question("Social"))
        rvQuestionItems.adapter = questionAdapter
        rvQuestionItems.layoutManager = LinearLayoutManager(this)


        val actionBar = supportActionBar
        actionBar!!.title = "Needs fulfilled"
        actionBar.setDisplayHomeAsUpEnabled(true)

        btnNext.setOnClickListener {
            val intent = Intent(this, Statistics::class.java)
            startActivity(intent)
        }

    }
}