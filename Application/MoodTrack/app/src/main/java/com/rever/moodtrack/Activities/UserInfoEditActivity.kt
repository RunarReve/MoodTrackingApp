package com.rever.moodtrack.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.rever.moodtrack.R
import com.rever.moodtrack.data.User
import kotlinx.android.synthetic.main.activity_user_info_edit.*


class UserInfoEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info_edit)

        val userID = FirebaseAuth.getInstance().currentUser.uid

        val user = User(userID)
        val database = FirebaseDatabase.getInstance().reference.child("user").child(userID)

        if(intent.getStringExtra("from") != "register"){
            // Read from the database
            btnBackUserInfo.visibility = View.VISIBLE
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val shot = snapshot.child("userInfo")
                    user.ethnicity = shot.child("ethnicity").getValue().toString()
                    user.gender = shot.child("gender").getValue().toString()
                    user.nationality = shot.child("nationality").getValue().toString()
                    user.postCode = shot.child("postCode").getValue().toString()

                    //TODO Check radio group
                    etNationality.setText(user.nationality)
                    etPostCode.setText(user.postCode)
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read anything, Do nothing
                }
            })
        }

        btnBackUserInfo.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnDoneUserInfo.setOnClickListener {
            val genderID = rgGender.checkedRadioButtonId
            val ethnicityID = rgEthnicity.checkedRadioButtonId
            if(genderID == -1 || ethnicityID == -1){
                Toast.makeText(this, "Fill out all boxes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            user.gender = findViewById<RadioButton>(genderID).text.toString()
            user.ethnicity = findViewById<RadioButton>(ethnicityID).text.toString()
            user.nationality = etNationality.text.toString().trim()
            user.postCode = etPostCode.text.toString().trim()

            database.child("userInfo").setValue(user).addOnCompleteListener { task ->
                if (task.isComplete) {
                    Toast.makeText(this, "Completed Registration", Toast.LENGTH_SHORT).show()

                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else
                    Toast.makeText(this, task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}