package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.PearsonAdapter
import com.rever.moodtrack.Adapters.StatisticsAdapter
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    private lateinit var qqList: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val mUserViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        val actionBar = supportActionBar
        actionBar!!.title = "Satistics"
        actionBar.setDisplayHomeAsUpEnabled(true)

        qqList = StatisticsAdapter(mutableListOf())
        //Get data stored in db
        var check = true //Loop once
        mUserViewModel.readAllData.observe(this, Observer {
            if (check) {
                it.forEach {
                    check =false
                    qqList.addStat(it)
                }
                rvStatistics.adapter = qqList

                val pearson = PearsonAdapter(mutableListOf(), mutableListOf())
                pearson.doPearson(qqList.getList())
                rvPearson.adapter = pearson
            }
        })

        rvStatistics.layoutManager = LinearLayoutManager(this)
        rvPearson.layoutManager = LinearLayoutManager(this)
        rvPearson?.layoutManager =
                LinearLayoutManager(rvPearson.context,
                        LinearLayoutManager.HORIZONTAL,
                        false)

        //TODO Add delete
    }
}