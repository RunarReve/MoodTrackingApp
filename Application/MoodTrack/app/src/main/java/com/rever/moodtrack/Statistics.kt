package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_statistics.*

class Statistics : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)

        val intent = intent

        val moodQuestion = intent.getStringArrayExtra("moodQ")
        val needQuestion = intent.getStringArrayExtra("needQ")
        var str = ""
        moodQuestion?.forEach {
            str = str + "\n" + it.replace(",", " ")
        }
        str = str + "\n"
        needQuestion?.forEach {
            str = str + "\n" + it.replace(",", " ")
        }
        tvStat.text = str

        val actionBar = supportActionBar
        actionBar!!.title = "Satistics"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}