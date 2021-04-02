package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.Question
import kotlinx.android.synthetic.main.need_score_item.view.*

class StatisticsItemAdapter(
        private val stats: MutableList<com.rever.moodtrack.data.Question>
): RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.StatisticViewHolder {
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.need_score_item,
                parent,
                false
            )
        )
    }

    fun addObj(question: com.rever.moodtrack.data.Question){
        stats.add(question)
        stats.sortBy { it.title} //Sorting method to get alphabetical ...
        stats.sortByDescending { it.type}// ...but also moods first
        notifyItemInserted(stats.size - 1)
    }

    fun deleteObj(question: Question){
        stats.remove(question)
        notifyItemInserted(stats.size -1)
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