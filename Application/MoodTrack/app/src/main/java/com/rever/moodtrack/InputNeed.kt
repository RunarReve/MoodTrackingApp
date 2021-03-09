package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.QuestionAdapter
import com.rever.moodtrack.data.NeedStore.NeedViewModel
import com.rever.moodtrack.data.QuestionStore.Question
import com.rever.moodtrack.data.QuestionStore.QuestionViewModel
import kotlinx.android.synthetic.main.activity_day_need.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InputNeed : AppCompatActivity() {
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_need)

        val actionBar = supportActionBar
        actionBar!!.title = "Rate need fulfillment"
        actionBar.setDisplayHomeAsUpEnabled(true)

        questionAdapter = QuestionAdapter(mutableListOf())
        rvQuestionItems.adapter = questionAdapter //Display
        rvQuestionItems.layoutManager = LinearLayoutManager(this)

        //Preset constant Need
        questionAdapter.addQuestion("Sleep")
        questionAdapter.addQuestion("Movement")
        questionAdapter.addQuestion("Social")
        //Get custom needs goals from DB
        val needViewModel = ViewModelProvider(this).get(NeedViewModel::class.java)
        needViewModel.readAllData.observe(this, Observer {
            it.forEach {
                if(it.isPrimary == 0) //Normally not much data, -> OK to just pull all
                    questionAdapter.addNeed(it)
            }
        })

        btnNext.setOnClickListener {
            val questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y/MM/dd H:mm:ss")).toString()

            for(i in 0 until questionAdapter.getSize()) {
                val q = Question(0, time,"TEMP2", questionAdapter.getTitle(i), questionAdapter.getrate(i),0)
                questionViewModel.addQuestion(q)
            }

            //All questions from previous
            val moodQuestion = intent.getStringArrayExtra("moodQ")
            moodQuestion?.forEach {
                val split = it.split(',')
                val q = Question(1, time, "TEMP2", split[0], split[1].toInt(),0)
                questionViewModel.addQuestion(q)
            }

            val intent = Intent(this, Statistics::class.java)
            startActivity(intent)
        }

    }
}