package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.QuestionCollection
import com.rever.moodtrack.R
import com.rever.moodtrack.relationMethods.PearsonObject
import com.rever.moodtrack.relationMethods.pearsonCorrelation
import kotlinx.android.synthetic.main.stat_obj_item.view.*

class PearsonAdapter(
        private var pearsonList: MutableList<PearsonObject>
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

    fun doPearson(questionCollList: MutableList<QuestionCollection>){
        pearsonList = pearsonCorrelation.doAll(questionCollList)
        notifyItemInserted(pearsonList.size - 1)
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        holder.itemView.apply {
            tvQuestionTitle.text = pearsonList[position].title
            tvQuestionRate.text  = String.format("%.3f",pearsonList[position].score)
        }
    }

    override fun getItemCount(): Int {
        return pearsonList.size
    }
}