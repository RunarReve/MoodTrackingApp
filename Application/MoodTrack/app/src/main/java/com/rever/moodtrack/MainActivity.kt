package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Home"

        btnNew.setOnClickListener {
            startActivity(Intent(this, DayMood::class.java))
        }

        btnStat.setOnClickListener {
            startActivity(Intent(this, Statistics::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))
        }

        btnAbout.setOnClickListener {
            startActivity(Intent(this, About::class.java))
        }


    }



}