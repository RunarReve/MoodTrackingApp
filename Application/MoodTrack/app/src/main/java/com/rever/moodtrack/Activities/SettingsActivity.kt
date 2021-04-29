package com.rever.moodtrack.Activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.rever.moodtrack.R
import com.rever.moodtrack.data.Fabricated.FabData
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
            deleteDataDialog(userID)
        }

        btnDeleteUser.setOnClickListener {
            deleteUserDialog(userID)
        }

        btnEditNeed.setOnClickListener {
            startActivity(Intent(this, SettingsNeedEditActivity::class.java))
        }

        btnLoadPreData.setOnClickListener {
            val database = FirebaseDatabase.getInstance().reference.child("user").child(userID).child("data")
            FabData.addData().forEach {  questionCollection ->
                questionCollection.qList.forEach { question ->
                    database.child(questionCollection.id).child(question.title).setValue(question)
                }
            }
        }

        btnEditUserData.setOnClickListener {
            val intent = Intent(this, UserInfoEditActivity::class.java)
            intent.putExtra("from", "edit")
            startActivity(intent)
        }


    }
    fun deleteUserDialog(userID: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Delete User")
        builder.setMessage("Are you sure to delete the user?")
        builder.setIcon(R.drawable.ic_launcher_foreground)

        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            FirebaseDatabase.getInstance().reference.child("user").child(userID).removeValue()
            FirebaseAuth.getInstance().currentUser.delete().addOnSuccessListener {
                Toast.makeText(this, "User deletion successful!", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Deletion failed, try again or contact creator", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }

    fun deleteDataDialog(userID: String) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Delete User")
        builder.setMessage("Are you sure to delete the user?")
        builder.setIcon(R.drawable.ic_launcher_foreground)

        builder.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
            FirebaseDatabase.getInstance().reference.child("user").child(userID).child("data").removeValue()
            dialog.dismiss()
        })
        builder.setNegativeButton("No", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
        })

        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }
}