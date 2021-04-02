package com.rever.moodtrack

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rever.moodtrack.Adapters.FireNeedAdapter
import com.rever.moodtrack.data.CustomNeed
import kotlinx.android.synthetic.main.activity_settings_need_edit.*

class SettingsNeedEdit() : AppCompatActivity() {
    private lateinit var needAdapter: FireNeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_need_edit)

        val actionBar = supportActionBar
        actionBar!!.title = "Edit custom needs"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val userID = FirebaseAuth.getInstance().currentUser.uid

        needAdapter = FireNeedAdapter(userID,mutableListOf<CustomNeed>())
        rvNeedList.layoutManager = LinearLayoutManager(this)
        rvNeedList.adapter = needAdapter

        // Read from the database
        val database = FirebaseDatabase.getInstance().reference
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newList = mutableListOf<CustomNeed>() //New list to update with
                val shot = snapshot.child("user").child(userID).child("customNeed")
                shot.children.forEach {
                    val needName = it.child("needTitle").getValue().toString()
                    val type = it.child("type").getValue().toString().toInt()
                    newList.add(CustomNeed(needName,type))
                }
                needAdapter.newList(newList)
                needAdapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                // Failed to read anything, Do nothing
            }
        })

        fabAdd.setOnClickListener {
            AddNeedItemDialog(this, userID).show()
        }
    }
}