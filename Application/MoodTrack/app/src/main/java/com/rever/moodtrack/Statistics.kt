package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.StatisticsAdapter
import kotlinx.android.synthetic.main.activity_statistics.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Statistics : AppCompatActivity() {
    private lateinit var qqList: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val mUserViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        qqList = StatisticsAdapter(mutableListOf())
        //qqList.newInput("Day1")
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y/M/d H:m:ss")).toString()
        tvStat.text =time

        var check = true //Make sure it only runs this once
        mUserViewModel.readAllData.observe(this, Observer {
            if (check) {
                it.forEach {
                    check =false
                    qqList.addStat(it)
                }
                rvStatistics.adapter = qqList
                rvStatistics.layoutManager = LinearLayoutManager(this)
            }
        })

        rvStatistics.layoutManager = LinearLayoutManager(this)

        //TODO make a recycle view to display collected data simlesly (with delete)

        val actionBar = supportActionBar
        actionBar!!.title = "Satistics"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}