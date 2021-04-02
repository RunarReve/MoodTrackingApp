package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.rever.moodtrack.R
import com.rever.moodtrack.data.CustomNeed
import kotlinx.android.synthetic.main.need_item.view.*

class NeedAdapter(
        private val userID: String,
        private var needList: MutableList<CustomNeed>
):RecyclerView.Adapter<NeedAdapter.NeedViewHolder>() {
    inner class NeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeedViewHolder {
        return NeedViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.need_item,
                parent,
                false
            )
        )
    }

    fun getList(): List<CustomNeed>{
        return needList
    }
    fun newList(newList: MutableList<CustomNeed>){
        needList = newList
    }


    override fun onBindViewHolder(holder: NeedViewHolder, position: Int) {
        val curNeed = needList[position]
        holder.itemView.tvNeedHeader.text = curNeed.NeedTitle
        if (curNeed.type == 0)
            holder.itemView.tvNeedType.text = "Need"
        else
            holder.itemView.tvNeedType.text = "Want"
        holder.itemView.ivDeleteNeed.setOnClickListener {   //Delete button
            val database = FirebaseDatabase.getInstance().reference
            database.child("user").child(userID).child("customNeed").child(curNeed.NeedTitle).removeValue()
        }
    }

    override fun getItemCount(): Int {
        return needList.size
    }
}