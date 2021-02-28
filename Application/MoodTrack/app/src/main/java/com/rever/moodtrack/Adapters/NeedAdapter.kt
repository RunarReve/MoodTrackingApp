package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.NeedStore.Need
import com.rever.moodtrack.data.NeedStore.NeedViewModel
import kotlinx.android.synthetic.main.need_item.view.*

class NeedAdapter(
    var needList: List<Need>,
    private val needViewModel: NeedViewModel
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

    fun getList(): List<Need>{
        return needList
    }

    override fun onBindViewHolder(holder: NeedViewHolder, position: Int) {
        val curNeed = needList[position]

        holder.itemView.tvNeedHeader.text = curNeed.needTitle
        if (curNeed.isPrimary == 0)
            holder.itemView.tvNeedType.text = "Active Need"
        else
            holder.itemView.tvNeedType.text = "Subjective  Need"

        holder.itemView.ivDeleteNeed.setOnClickListener {
            println("LLLLLLOOOOLLL")
            needViewModel.deleteNeed(curNeed)
        }
    }

    override fun getItemCount(): Int {
        return needList.size
    }
}