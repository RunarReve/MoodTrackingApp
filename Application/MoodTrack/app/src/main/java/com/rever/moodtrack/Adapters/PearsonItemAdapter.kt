package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.data.QuestionCollection
import com.rever.moodtrack.R
import com.rever.moodtrack.data.PearsonCollection
import com.rever.moodtrack.relationMethods.PearsonObject
import com.rever.moodtrack.relationMethods.pearsonCorrelation
import kotlinx.android.synthetic.main.stat_obj_item.view.*

class PearsonItemAdapter(
        private var pearsonList: PearsonCollection
        ) : RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.StatisticViewHolder {
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.stat_obj_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        holder.itemView.apply {
            tvQuestionTitle.text = pearsonList.titleList[position]
            tvQuestionRate.text  = String.format("%.3f",pearsonList.rateList[position])
        }
    }

    override fun getItemCount(): Int {
        return pearsonList.titleList.size
    }
}