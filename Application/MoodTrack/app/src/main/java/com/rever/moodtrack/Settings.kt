package com.rever.moodtrack

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.rever.moodtrack.data.QuestionStore.QuestionViewModel
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
        btnEditNeed.setOnClickListener {
            startActivity(Intent(this, SettingsNeedEdit::class.java))
        }

        //TODO Edit method for custom needs


    }
}