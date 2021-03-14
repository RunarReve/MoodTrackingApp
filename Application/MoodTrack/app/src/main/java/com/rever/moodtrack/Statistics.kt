package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rever.moodtrack.Adapters.LinePlotAdapter
import com.rever.moodtrack.Adapters.PearsonAdapter
import com.rever.moodtrack.Adapters.RegressionAdapter
import com.rever.moodtrack.Adapters.StatisticsAdapter
import com.rever.moodtrack.data.QuestionStore.QuestionViewModel
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    private lateinit var statisticsAdapter: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        val actionBar = supportActionBar
        actionBar!!.title = "Statistics"
        actionBar.setDisplayHomeAsUpEnabled(true)

        statisticsAdapter = StatisticsAdapter(mutableListOf())
        //Get data stored in db
        var check = true //Loop once TODO fix this
        questionViewModel.readAllData.observe(this, Observer {
            if (check) {
                it.forEach {
                    check =false
                    statisticsAdapter.addStat(it)
                }

                //------Input-List------
                rvStatistics.adapter = statisticsAdapter
                rvStatistics.layoutManager = LinearLayoutManager(this)

                //------Pearson------
                val pearsonAdapter = PearsonAdapter(mutableListOf())
                pearsonAdapter.doPearson(statisticsAdapter.getList())
                rvPearson.adapter = pearsonAdapter
                rvPearson.layoutManager = LinearLayoutManager(this)
                rvPearson?.layoutManager =
                        LinearLayoutManager(rvPearson.context,
                                LinearLayoutManager.VERTICAL,
                                false)

                //------Regression------
                val regressionAdapter = RegressionAdapter(mutableListOf())
                regressionAdapter.doRegression(statisticsAdapter.getList())
                rvRegression.adapter = regressionAdapter
                rvRegression.layoutManager = LinearLayoutManager(this)
                rvRegression?.layoutManager =
                        LinearLayoutManager(rvPearson.context,
                                LinearLayoutManager.VERTICAL,
                                false)

                //------Line-Plot------
                val linePlotAdapter = LinePlotAdapter(mutableListOf(), this)
                linePlotAdapter.addDataSet(statisticsAdapter.getList())
                rvLineCharts.layoutManager = LinearLayoutManager(this)
                rvLineCharts.adapter = linePlotAdapter
            }
        })


    }
}