package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.FireQuestion
import com.rever.moodtrack.data.QuestionStore.Question
import kotlinx.android.synthetic.main.need_score_item.view.*

class StatisticsItemAdapter(
        private val stats: MutableList<FireQuestion>
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

    fun addObj(question: FireQuestion){
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