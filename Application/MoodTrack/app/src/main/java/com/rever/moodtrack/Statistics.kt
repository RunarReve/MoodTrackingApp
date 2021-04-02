package com.rever.moodtrack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rever.moodtrack.Adapters.LinePlotAdapter
import com.rever.moodtrack.Adapters.PearsonAdapter
import com.rever.moodtrack.Adapters.RegressionAdapter
import com.rever.moodtrack.Adapters.StatisticsAdapter
import com.rever.moodtrack.data.Question
import com.rever.moodtrack.data.questionCollection
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    private lateinit var statisticsAdapter: StatisticsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        val actionBar = supportActionBar
        actionBar!!.title = "Statistics"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val userID = FirebaseAuth.getInstance().currentUser.uid

        statisticsAdapter = StatisticsAdapter(mutableListOf())
        //Get data stored in db
        val database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newList = mutableListOf<questionCollection>()
                val shot = snapshot.child("user").child(userID).child("data")
                shot.children.forEach { date ->
                    val questionList = mutableListOf<Question>()
                    date.children.forEach {
                        val title = it.child("title").getValue().toString()
                        val type = it.child("type").getValue().toString().toInt()
                        val rate = it.child("rate").getValue().toString().toInt()
                        questionList.add(Question(title, type, rate))
                    }
                    questionList.forEach {
                        println("LOL: ${it}")
                    }
                    newList.add(questionCollection(date.key.toString(), questionList))
                }
                statisticsAdapter.setList(newList)

                //------Input-List------
                rvStatistics.adapter = statisticsAdapter
                rvStatistics.layoutManager = LinearLayoutManager(this@Statistics)

                //------Pearson------
                val pearsonAdapter = PearsonAdapter(mutableListOf())
                pearsonAdapter.doPearson(statisticsAdapter.getList())
                rvPearson.adapter = pearsonAdapter
                rvPearson.layoutManager = LinearLayoutManager(this@Statistics)
                rvPearson?.layoutManager =
                        LinearLayoutManager(rvPearson.context,
                                LinearLayoutManager.VERTICAL,
                                false)

                //------Spearman------
                val spearmanAdapter = PearsonAdapter(mutableListOf())
                spearmanAdapter.doSpearman(statisticsAdapter.getList())
                rvSpearman.adapter = spearmanAdapter
                rvSpearman.layoutManager = LinearLayoutManager(this@Statistics)
                rvSpearman?.layoutManager =
                        LinearLayoutManager(rvSpearman.context,
                                LinearLayoutManager.VERTICAL,
                                false)

                //------Regression------
                val regressionAdapter = RegressionAdapter(mutableListOf())
                regressionAdapter.doRegression(statisticsAdapter.getList())
                rvRegression.adapter = regressionAdapter
                rvRegression.layoutManager = LinearLayoutManager(this@Statistics)
                rvRegression?.layoutManager =
                        LinearLayoutManager(rvPearson.context,
                                LinearLayoutManager.VERTICAL,
                                false)

                //------Line-Plot------
                val linePlotAdapter = LinePlotAdapter(mutableListOf(), this@Statistics)
                linePlotAdapter.addDataSet(statisticsAdapter.getList())
                rvLineCharts.layoutManager = LinearLayoutManager(this@Statistics)
                rvLineCharts.adapter = linePlotAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read anything, Do nothing
            }
        })
    }
}