package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.data.QuestionCollection
import com.rever.moodtrack.R
import com.rever.moodtrack.data.QuestionStore.Question
import kotlinx.android.synthetic.main.statistics_item.view.*

class StatisticsAdapter(
        private val statistics: MutableList<QuestionCollection>
): RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>() {

    private lateinit var statObjAdapter: StatObjAdapter
    class StatisticViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticViewHolder {
        return StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                    R.layout.statistics_item,
                    parent,
                    false
            )
        )
    }
    fun getList():MutableList<QuestionCollection>{
        return statistics
    }

    fun addStat(question: Question){
        var check = false //check if the question has been added
        for(i in 0 until statistics.size){
            if(statistics[i].qList.get(0).time == question.time) {
                statistics[i].qList.add(question)
                check = true
            }
        }
        if(!check) {
            val o = QuestionCollection(question.time)
            o.qList.add(question)
            statistics.add(o)
        }
        notifyItemInserted(statistics.size - 1)
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        val curStats = statistics[position]

        //Fill each RV with each set of questions
        statObjAdapter = StatObjAdapter(mutableListOf())
        holder.itemView.apply {
            curStats.qList.forEach {
                statObjAdapter.addObj(it)
            }
            rvStatisticItem.adapter = statObjAdapter
            rvStatisticItem?.layoutManager =
                LinearLayoutManager(rvStatisticItem.context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            tvDayHeader.text = curStats.qList[0].time //TODO change to only date format
        }
        return
    }

    override fun getItemCount(): Int {
        return statistics.size
    }
}