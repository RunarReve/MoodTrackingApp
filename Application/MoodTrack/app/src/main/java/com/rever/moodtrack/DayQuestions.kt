package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.QuestionAdapter
import com.rever.moodtrack.data.QuestionStore
import kotlinx.android.synthetic.main.activity_day_questions.*

class DayQuestions : AppCompatActivity() {

    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_questions)

        questionAdapter = QuestionAdapter(mutableListOf())
        questionAdapter.addQuestion("Sleep")
        questionAdapter.addQuestion("Movement")
        questionAdapter.addQuestion("Social")
        rvQuestionItems.adapter = questionAdapter
        rvQuestionItems.layoutManager = LinearLayoutManager(this)


        btnNext.setOnClickListener {
            val mUserViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

            val needQuestion = Array<String>(questionAdapter.getSize()){""}
            for(i in 0 until questionAdapter.getSize()) {
                needQuestion[i] = questionAdapter.getItemCount2(i)
                val q = QuestionStore(0,1,"TEMP",
                    questionAdapter.getTitle(i), questionAdapter.getrate(i)
                )
                mUserViewModel.addQuestion(q)
            }
            val intentOld = intent

            val intent = Intent(this, Statistics::class.java)
            intent.putExtra("moodQ", intentOld.getStringArrayExtra("moodQ"))
            intent.putExtra("needQ", needQuestion)
            startActivity(intent)
        }

        val actionBar = supportActionBar
        actionBar!!.title = "Needs fulfilled"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}