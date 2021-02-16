package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.QuestionAdapter
import com.rever.moodtrack.data.Question
import kotlinx.android.synthetic.main.activity_day_questions.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DayQuestions : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_questions)

        //Hardcoded questions To be asked
        questionAdapter = QuestionAdapter(mutableListOf())
        questionAdapter.addQuestion("Sleep")
        //questionAdapter.addQuestion("Movement")
        //questionAdapter.addQuestion("Social")
        rvQuestionItems.adapter = questionAdapter //Display
        rvQuestionItems.layoutManager = LinearLayoutManager(this)


        btnNext.setOnClickListener {
            val mUserViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

            val needQuestion = Array(questionAdapter.getSize()){""}
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y/M/d H:m:ss")).toString()

            for(i in 0 until questionAdapter.getSize()) {
                needQuestion[i] = questionAdapter.getItemCount2(i)
                val q = Question(0,2, time,"TEMP",
                    questionAdapter.getTitle(i), questionAdapter.getrate(i)
                )
                mUserViewModel.addQuestion(q)
            }

            //All questions from previous
            val moodQuestion = intent.getStringArrayExtra("moodQ")
            moodQuestion?.forEach {
                val split = it.split(',')
                val q = Question(0,1, time, "TEMP", split[0], split[1].toInt())
                mUserViewModel.addQuestion(q)
            }

            val intent = Intent(this, Statistics::class.java)
            startActivity(intent)
        }

        val actionBar = supportActionBar
        actionBar!!.title = "Needs fulfilled"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}