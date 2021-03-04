package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rever.moodtrack.data.QuestionStore.QuestionViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.need_item.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionBar = supportActionBar
        actionBar!!.title = "Home"

        //Fill the trophy to be number of instances of inputs
        val questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
        questionViewModel.numberOfIterations.observe(this, Observer{
            tvMainScreenText.text = "${it}\nDays"
        })

        //--------------Home-Buttons-----------------
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
        //--------------Home-Buttons-----------------
    }
}