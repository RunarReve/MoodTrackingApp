package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.R
import com.rever.moodtrack.data.QuestionStore.Question
import kotlinx.android.synthetic.main.statistics_item.view.*

class StatisticsAdapter(
        private val statistics: MutableList<questionCollection>
): RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>() {

    private lateinit var statisticsItemAdapter: StatisticsItemAdapter
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
    fun getList():MutableList<questionCollection>{
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
            val o = questionCollection(question.time)
            o.qList.add(question)
            statistics.add(o)
        }
        sortList()
        notifyItemInserted(statistics.size - 1)
    }

    fun sortList(){
        statistics.sortByDescending { it.qList[0].time}
    }

    override fun onBindViewHolder(holder: StatisticViewHolder, position: Int) {
        val curStats = statistics[position]

        //Fill each RV with each set of questions
        statisticsItemAdapter = StatisticsItemAdapter(mutableListOf())
        holder.itemView.apply {
            curStats.qList.forEach {
                statisticsItemAdapter.addObj(it)
            }
            rvStatisticItem.adapter = statisticsItemAdapter
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