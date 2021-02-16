package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.QuestionAdapter
import kotlinx.android.synthetic.main.activity_day_mood.btnNext
import kotlinx.android.synthetic.main.activity_day_mood.rvQuestionItems

class DayMood : AppCompatActivity() {
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_mood)
        val actionBar = supportActionBar
        actionBar!!.title = "Enter Mood of day"
        actionBar.setDisplayHomeAsUpEnabled(true)

        //-------------Setting up the Questions being asked
        questionAdapter = QuestionAdapter(mutableListOf())
        questionAdapter.addQuestion("Mood")
        rvQuestionItems.adapter = questionAdapter
        rvQuestionItems.layoutManager = LinearLayoutManager(this)
        //-------------Setting up the Questions being asked

        btnNext.setOnClickListener {
            val moodQuestion = Array<String>(questionAdapter.getSize()){""}
            for(i in 0 until questionAdapter.getSize())
                moodQuestion[i] =  questionAdapter.getItemCount2(i)

            val intent = Intent(this, DayQuestions::class.java)
            intent.putExtra("moodQ", moodQuestion)
            startActivity(intent)
        }
    }
}