package com.rever.moodtrack.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.core.view.get
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

        rgAllow4Study.check(rgAllow4Study[1].id) //Default set allow4Study to no

        if(intent.getStringExtra("from") != "register"){
            // Read from the database
            database.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    //Get user data from DB
                    val shot = snapshot.child("userInfo")
                    user.allow4Study = shot.child("allow4Study").getValue().toString()
                    user.ethnicity = shot.child("ethnicity").getValue().toString()
                    user.gender = shot.child("gender").getValue().toString()
                    user.ageGroup = shot.child("ageGroup").getValue().toString()
                    user.nationality = shot.child("nationality").getValue().toString()
                    user.postCode = shot.child("postCode").getValue().toString()

                    setRadioBoxes(user)
                }
                override fun onCancelled(error: DatabaseError) {
                    // Failed to read anything, Do nothing
                }
            })
        }

        btnDoneUserInfo.setOnClickListener {
            val allow4StudyID = rgAllow4Study.checkedRadioButtonId
            val genderID = rgGender.checkedRadioButtonId
            val ethnicityID = rgEthnicity.checkedRadioButtonId
            val ageGroupID = rgAge.checkedRadioButtonId
            if(genderID == -1 || ethnicityID == -1 || allow4StudyID == -1 || ageGroupID == -1){ //Check radio-groups are checked
                Toast.makeText(this, "Please fill out all boxes", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            user.allow4Study = findViewById<RadioButton>(allow4StudyID).text.toString()
            user.gender = findViewById<RadioButton>(genderID).text.toString()
            user.ethnicity = findViewById<RadioButton>(ethnicityID).text.toString()
            user.ageGroup = findViewById<RadioButton>(ageGroupID).text.toString()
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

    private fun setRadioBoxes(user: User){
        setBox(user.allow4Study, rgAllow4Study)
        setBox(user.gender, rgGender)
        setBox(user.ethnicity, rgEthnicity)
        setBox(user.ageGroup, rgAge)

        etNationality.setText(user.nationality)
        etPostCode.setText(user.postCode)
    }
    private fun setBox(string: String, radioGroup: RadioGroup){
        for (index in 0..radioGroup.childCount) {
            radioGroup.check(radioGroup[index].id)
            if(findViewById<RadioButton>(radioGroup.checkedRadioButtonId).text.toString() != string)
                return //If this is correct just return
        }

    }
}