package com.rever.moodtrack

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.google.firebase.database.FirebaseDatabase
import com.rever.moodtrack.data.CustomNeed
import kotlinx.android.synthetic.main.dialog_add_need_item.*

class AddNeedItemDialog (context: Context, userID: String):AppCompatDialog(context){
    val userID = userID

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_need_item)

        val database = FirebaseDatabase.getInstance().reference

        btnAddNeed.setOnClickListener {
            val needTitle = etNeedTitle.text.toString()
            if(needTitle.isEmpty()){
                Toast.makeText(context, "Please enter information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var type = 0
            if(sIsSubjective.isChecked)
                type = 1
            val need = CustomNeed(needTitle, type)
            database.child("user").child(userID).child("customNeed").child(need.NeedTitle).setValue(need)

            Toast.makeText(context, "Added this: ${needTitle}", Toast.LENGTH_SHORT).show()
            dismiss()
        }

        btnCancel.setOnClickListener {
            cancel()
        }
    }

}