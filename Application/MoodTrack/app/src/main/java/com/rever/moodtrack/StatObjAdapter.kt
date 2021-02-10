package com.rever.moodtrack

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stat_obj_item.view.*

class StatObjAdapter(
        private val stats: MutableList<Question>
): RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.StatisticViewHolder {
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.stat_obj_item,
                    parent,
                    false
            )
        )
    }

    fun addObj(question: Question){
        stats.add(question)
        notifyItemInserted(stats.size - 1)
    }
    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        val curStat = stats[position]
        holder.itemView.apply {
            tvQuestionTitle.text = curStat.title
            tvQuestionRate.text  = curStat.rate.toString()
        }
    }
    override fun getItemCount(): Int {
        return stats.size
    }
}