package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rever.moodtrack.Adapters.FireQuestionAdapter
import kotlinx.android.synthetic.main.activity_day_want.*

class InputWant : AppCompatActivity() {
    private lateinit var questionAdapter: FireQuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_day_want)

        val actionBar = supportActionBar
        actionBar!!.title = "Rate want fulfillment"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val userID = FirebaseAuth.getInstance().currentUser.uid

        questionAdapter = FireQuestionAdapter(mutableListOf())
        rvWantRateList.adapter = questionAdapter
        rvWantRateList.layoutManager = LinearLayoutManager(this)

        //-------------Setting up the Questions being asked
        //Preset constant LastStep goals
        questionAdapter.addQuestionPrimary("Happiness")
        //Get custom needs goals from DB
        val database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val shot = snapshot.child("user").child(userID).child("customNeed")
                shot.children.forEach {
                    val type = it.child("type").getValue().toString().toInt()
                    val needName = it.child("needTitle").getValue().toString()
                    if(type == 1 )
                        questionAdapter.addQuestionPrimary(needName)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read anything, Do nothing
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