package com.rever.moodtrack

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.forEach
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_day_mood.*
import kotlinx.android.synthetic.main.activity_day_mood.btnNext
import kotlinx.android.synthetic.main.activity_day_mood.rvQuestionItems
import kotlinx.android.synthetic.main.activity_day_questions.*
import kotlinx.android.synthetic.main.activity_main.*

class DayMood : AppCompatActivity() {
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_mood)

        questionAdapter = QuestionAdapter(mutableListOf())
        val q = Question("Mood")
        questionAdapter.addQuestion(q)
        rvQuestionItems.adapter = questionAdapter
        rvQuestionItems.layoutManager = LinearLayoutManager(this)

        val actionBar = supportActionBar
        actionBar!!.title = "Enter Mood of day"
        actionBar.setDisplayHomeAsUpEnabled(true)


        btnNext.setOnClickListener {
            var listo = mutableListOf(questionAdapter.getItemCount2(0))
            for(i in 1 until questionAdapter.getSize())
                listo.add(questionAdapter.getItemCount2(i))
            Toast.makeText(applicationContext,  "Lols ${listo}", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, DayQuestions::class.java)
            startActivity(intent)
        }

    }
}