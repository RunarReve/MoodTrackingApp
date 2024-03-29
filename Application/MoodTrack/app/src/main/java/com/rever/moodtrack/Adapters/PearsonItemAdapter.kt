package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.relationCollection
import kotlinx.android.synthetic.main.need_score_item.view.*

class PearsonItemAdapter(
        private var relationList: relationCollection
        ) : RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.StatisticViewHolder {
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.need_score_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        holder.itemView.apply {
            tvQuestionTitle.text = relationList.titleList[position]
            tvQuestionRate.text  = String.format("%.3f",relationList.rateList[position])
        }
    }

    override fun getItemCount(): Int {
        return relationList.titleList.size
    }
}