package com.rever.moodtrack

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialog
import com.rever.moodtrack.data.NeedStore.Need
import com.rever.moodtrack.data.NeedStore.NeedViewModel
import kotlinx.android.synthetic.main.dialog_add_need_item.*

class AddNeedItemDialog (context: Context, var needViewModel: NeedViewModel):AppCompatDialog(context){

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_need_item)

        btnAddNeed.setOnClickListener {
            val needTitle = etNeedTitle.text.toString()
            if(needTitle.isEmpty()){
                Toast.makeText(context, "Please enter information", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var isPrimary = 0
            if(sIsSubjective.isChecked)
                isPrimary = 1
            val need = Need(isPrimary, needTitle,"Temp", 0)
            needViewModel.addNeed(need)
            Toast.makeText(context, "Added this: ${needTitle}", Toast.LENGTH_SHORT).show()

            dismiss()
        }

        btnCancel.setOnClickListener {
            cancel()
        }
    }

}