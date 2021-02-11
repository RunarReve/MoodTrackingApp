package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.StatisticsAdapter
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    private lateinit var qqList: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val intent = intent
        val moodQuestion = intent.getStringArrayExtra("moodQ")
        val needQuestion = intent.getStringArrayExtra("needQ")

        var str = ""
        qqList = StatisticsAdapter(mutableListOf())
        qqList.newInput("TEMP")

        moodQuestion?.forEach {
            qqList.addStat(Question(it.split(',')[0], it.split(',')[1].toInt()), qqList.getSize()-1)
            str += "\n" + it.replace(",", " ")
        }
        str += "\n"
        needQuestion?.forEach {
            qqList.addStat(Question(it.split(',')[0], it.split(',')[1].toInt()), qqList.getSize()-1)
            str += "\n" + it.replace(",", " ")
        }
        rvStatistics.adapter = qqList
        rvStatistics.layoutManager = LinearLayoutManager(this)

        tvStat.text = str


        //TODO make a recycle view to display collected data simlesly (with delete)


        //TODO make Database to store previously saved instances

        val actionBar = supportActionBar
        actionBar!!.title = "Satistics"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}