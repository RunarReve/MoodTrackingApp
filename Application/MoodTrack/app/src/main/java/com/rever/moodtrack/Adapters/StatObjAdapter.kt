package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.Question
import com.rever.moodtrack.R
import com.rever.moodtrack.data.QuestionStore
import kotlinx.android.synthetic.main.stat_obj_item.view.*

class StatObjAdapter(
        private val stats: MutableList<QuestionStore>
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

    fun addObj(question: QuestionStore){
        stats.add(question)
        notifyItemInserted(stats.size - 1)
    }
    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        val curStat = stats[position]
        holder.itemView.apply {
            tvQuestionTitle.text = curStat.questionTitle
            tvQuestionRate.text  = curStat.rate.toString()
        }
    }
    override fun getItemCount(): Int {
        return stats.size
    }
}