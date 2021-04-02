package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rever.moodtrack.data.QuestionStore.QuestionViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Home"

        val userID = FirebaseAuth.getInstance().currentUser.uid
        val database = FirebaseDatabase.getInstance().reference.child("user").child(userID)

        //Fill the trophy to be number of instances of inputs
        database.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                tvMainScreenText.text = snapshot.child("data").childrenCount.toString()
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        //--------------Home-Buttons-----------------
        btnNewInput.setOnClickListener {
            startActivity(Intent(this, InputWant::class.java))
        }

        btnStat.setOnClickListener {
            startActivity(Intent(this, Statistics::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))
        }

        btnAbout.setOnClickListener {
            startActivity( Intent(this, About::class.java))
        }

        btnLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
        //--------------Home-Buttons-----------------
    }
}