package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.NeedStore.Need
import kotlinx.android.synthetic.main.need_item.view.*

class NeedAdapter(
    private val needList: MutableList<Need>
):RecyclerView.Adapter<NeedAdapter.NeedViewHolder>() {
    class NeedViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NeedViewHolder {
        return NeedViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.need_item,
                parent,
                false
            )
        )
    }

    fun addNeed(need: Need){
        println("ADDING ${need}, aka ${need.needTitle}")
        needList.add(need)
        notifyItemInserted(needList.size -1)
    }

    fun clearNeed(){
        needList.clear()
        notifyItemInserted(needList.size -1)
    }

    fun getList(): MutableList<Need>{
        return needList
    }

    override fun onBindViewHolder(holder: NeedViewHolder, position: Int) {
        val curNeed = needList[position]

        println("WORK GOD DAMN YOU!")

        holder.itemView.apply {
            tvNeedHeader.text = curNeed.needTitle
        }
    }

    override fun getItemCount(): Int {
        println("BIG DICK BE: ${needList.size}")
        return needList.size
    }
}