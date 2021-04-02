package com.rever.moodtrack.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rever.moodtrack.R

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        val actionBar = supportActionBar
        actionBar!!.title = "AboutActivity"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}