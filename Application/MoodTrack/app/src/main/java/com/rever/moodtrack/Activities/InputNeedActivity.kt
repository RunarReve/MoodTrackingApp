package com.rever.moodtrack.Activities

import android.content.Context
import android.content.Intent
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rever.moodtrack.Adapters.QuestionAdapter
import com.rever.moodtrack.Adapters.StepCounterAdapter
import com.rever.moodtrack.R
import com.rever.moodtrack.data.Question
import kotlinx.android.synthetic.main.activity_day_need.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class InputNeedActivity : AppCompatActivity() {
    private lateinit var questionAdapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_need)

        val actionBar = supportActionBar
        actionBar!!.title = "Rate need fulfillment"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val userID = FirebaseAuth.getInstance().currentUser.uid

        val stepCounterAdapter = StepCounterAdapter()
        stepCounterAdapter.startCount( getSystemService(Context.SENSOR_SERVICE) as SensorManager)

        questionAdapter = QuestionAdapter(mutableListOf())
        rvNeedRateList.adapter = questionAdapter //Display
        rvNeedRateList.layoutManager = LinearLayoutManager(this)

        //Preset constant LastStep
        questionAdapter.addQuestion("Sleep")
        questionAdapter.addQuestion("Movement")
        questionAdapter.addQuestion("Social")
        //Get custom needs goals from DB
        val database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val shot = snapshot.child("user").child(userID).child("customNeed")
                shot.children.forEach {
                    val type = it.child("type").getValue().toString().toInt()
                    val needName = it.child("needTitle").getValue().toString()
                    if(type == 0 )
                        questionAdapter.addQuestionPrimary(needName)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read anything, Do nothing
            }
        })

        btnGetStep.setOnClickListener {
            etStepBox.setText(stepCounterAdapter.getCurrentStep())
        }

        btnToStat.setOnClickListener {
            val questionList = mutableListOf<Question>()
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("y_MM_dd-H:mm:ss")).toString()

            //Get previous activities questions
            intent.getStringArrayExtra("moodQ")?.forEach {
                val split = it.split(',')
                val q = Question(split[0], 1, split[1].toInt())
                questionList.add(q)
            }
            //Current activities questions
            for(i in 0 until questionAdapter.getSize()) {
                val q = Question(questionAdapter.getTitle(i), 0, questionAdapter.getrate(i))
                questionList.add(q)
            }
            //Get steps
            val steps = etStepBox.text.toString().toIntOrNull()
            if(steps != null)
                questionList.add(Question("Steps", -1, steps))

            //Upload gathered data
            val uploadLocation = database.child("user").child(userID).child("data").child(time)

            questionList.forEach {question ->
                uploadLocation.child(question.title).setValue(question)
            }
            val intent = Intent(this, StatisticsActivity::class.java)
            startActivity(intent)
        }

    }
}