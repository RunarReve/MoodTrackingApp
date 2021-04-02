package com.rever.moodtrack.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rever.moodtrack.R
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        val actionBar = supportActionBar
        actionBar!!.title = "SettingsActivity"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val userID = FirebaseAuth.getInstance().currentUser.uid

        btnDeleteTable.setOnClickListener {
            val database = FirebaseDatabase.getInstance().reference.child("user").child(userID)
            database.child("data").removeValue()
        }
        btnEditNeed.setOnClickListener {
            startActivity(Intent(this, SettingsNeedEditActivity::class.java))
        }

        btnLoadPreData.setOnClickListener {
      /*     val questionViewModel = ViewModelProvider(this).get(QuestionViewModel::class.java)
            FabData.addData().forEach { questionCollection ->
                questionCollection.qList.forEach{question ->
                    questionViewModel.addQuestion(question)
                }
            }*/
        }

        btnEditUserData.setOnClickListener {
            val intent = Intent(this, UserInfoEditActivity::class.java)
            intent.putExtra("from", "edit")
            startActivity(intent)
        }
    }
}