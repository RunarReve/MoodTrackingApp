package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.QuestionCollection
import com.rever.moodtrack.R
import com.rever.moodtrack.relationMethods.pearsonCorrelation
import kotlinx.android.synthetic.main.stat_obj_item.view.*

class PearsonAdapter(
        private var titleList: MutableList<String>,
        private var scoreList: MutableList<Double>
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

    fun addAll(titleListInn: MutableList<String>, scoreListInn: MutableList<Double>){
        titleList = titleListInn
        scoreList = scoreListInn
        notifyItemInserted(titleList.size - 1)
    }

    fun doPearson(questionCollList: MutableList<QuestionCollection>){
        pearsonCorrelation.questionCol2PearsonCor(questionCollList).forEach {
            scoreList.add(it)
        }
        pearsonCorrelation.getQuestionTitle(questionCollList).forEach {
            titleList.add(it)
        }
        notifyItemInserted(titleList.size - 1)
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        holder.itemView.apply {
            tvQuestionTitle.text = titleList[position]
            tvQuestionRate.text  = String.format("%.3f",scoreList[position])
        }
    }

    override fun getItemCount(): Int {
        return titleList.size
    }
}