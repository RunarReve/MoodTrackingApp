package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.StatisticsAdapter
import com.rever.moodtrack.data.QuestionStore
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    private lateinit var qqList: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val mUserViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)

        var str = ""
        qqList = StatisticsAdapter(mutableListOf())
        qqList.newInput("TEMP")

        println("KKK " + mUserViewModel.getAllQuetionItems())
        var check = true
        mUserViewModel.readAllData.observe(this, Observer {
            if (check) { //Make sure it only runs this once
                it.forEach {
                    check =false
                    println("OWO $it")
                    qqList.addStat(it, 0)
                    rvStatistics.adapter = qqList
                    rvStatistics.layoutManager = LinearLayoutManager(this)
                }
            }
            println("HERE xXXXXx")
        })
        println("HERE2 xXXXXx")
        println("HERE3 xXXXXx")
        //mUserViewModel.nuke()

        val intent = intent
        val moodQuestion = intent.getStringArrayExtra("moodQ")
        val needQuestion = intent.getStringArrayExtra("needQ")

        moodQuestion?.forEach {
            qqList.addStat(Question(it.split(',')[0], it.split(',')[1].toInt()), qqList.getSize()-1)
            str += "\n" + it.replace(",", " ")
        }
        str += "\n"
        needQuestion?.forEach {
            //qqList.addStat(QuestionStore(0,"DEL", it.split(',')[0], it.split(',')[1].toInt()), qqList.getSize()-1)
            qqList.addStat(Question(it.split(',')[0], it.split(',')[1].toInt()), qqList.getSize()-1)
            str += "\n" + it.replace(",", " ")
        }
        rvStatistics.adapter = qqList
        println("HERE4 xXXXXx")
        rvStatistics.layoutManager = LinearLayoutManager(this)
        println("HERE5 xXXXXx")



        tvStat.text = str
        println("HERE6 xXXXXx")
        //TODO make a recycle view to display collected data simlesly (with delete)

        //TODO make Database to store previously saved instances

        val actionBar = supportActionBar
        actionBar!!.title = "Satistics"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}