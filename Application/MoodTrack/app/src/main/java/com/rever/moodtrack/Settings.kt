package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_settings.*

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val actionBar = supportActionBar
        actionBar!!.title = "Settings"
        actionBar.setDisplayHomeAsUpEnabled(true)

        btnDeleteTable.setOnClickListener {
            val mUserViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
            mUserViewModel.deleteLocalData()
            Toast.makeText(this, "Deleted Local Data", Toast.LENGTH_SHORT).show()
        }


    }
}