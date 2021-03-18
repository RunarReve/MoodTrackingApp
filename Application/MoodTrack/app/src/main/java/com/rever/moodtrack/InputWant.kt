package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.QuestionAdapter
import com.rever.moodtrack.data.NeedStore.NeedViewModel
import kotlinx.android.synthetic.main.activity_day_want.*

class InputWant : AppCompatActivity() {
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_want)

        val actionBar = supportActionBar
        actionBar!!.title = "Rate want fulfillment"
        actionBar.setDisplayHomeAsUpEnabled(true)

        questionAdapter = QuestionAdapter(mutableListOf())
        rvWantRateList.adapter = questionAdapter
        rvWantRateList.layoutManager = LinearLayoutManager(this)

        //-------------Setting up the Questions being asked
        //Preset constant Need goals
        questionAdapter.addQuestionPrimary("Happiness")
        //Get custom needs goals from DB
        val needViewModel = ViewModelProvider(this).get(NeedViewModel::class.java)
        needViewModel.readAllData.observe(this, Observer {
            it.forEach {
                if(it.isPrimary == 1) //Normally not much data, -> OK to just pull all
                    questionAdapter.addNeed(it)
            }
        })

        //-------------Setting up the Questions being asked

        btnToAddNeed.setOnClickListener {
            val moodQuestion = Array(questionAdapter.getSize()){""}
            for(i in 0 until questionAdapter.getSize())
                moodQuestion[i] =  questionAdapter.getItemCount2(i)

            val intent = Intent(this, InputNeed::class.java)
            intent.putExtra("moodQ", moodQuestion)
            startActivity(intent)
        }
    }
}