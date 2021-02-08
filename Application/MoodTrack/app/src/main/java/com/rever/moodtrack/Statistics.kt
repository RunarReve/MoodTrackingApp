package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val intent = getIntent()
        //val name = intent.getStringArrayListExtra("arr")
        val name = intent.getStringExtra("arr")
        println("YoKO: $name")
        tvStat.text = name

        val actionBar = supportActionBar
        actionBar!!.title = "Satistics"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}