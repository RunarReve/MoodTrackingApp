package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.relationCollection
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.relationMethods.Correlation
import kotlinx.android.synthetic.main.pearson_item.view.*

class PearsonAdapter(
    private var relationList: List<relationCollection>
) : RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>(){

    fun doPearson(questionCollList: MutableList<questionCollection>){
        relationList = Correlation.doPearson(questionCollList)
        notifyItemInserted(relationList.size - 1)
    }
    fun doSpearman(questionCollList: MutableList<questionCollection>){
        relationList = Correlation.doSpearman(questionCollList)
        notifyItemInserted(relationList.size - 1)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticsAdapter.StatisticViewHolder {
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.pearson_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        val curPearsonItem = relationList[position]
        val pearsonItemAdapter = PearsonItemAdapter(curPearsonItem)
        holder.itemView.apply {
            rvPearsonItem.adapter =pearsonItemAdapter
            rvPearsonItem?.layoutManager =
                LinearLayoutManager(
                    rvPearsonItem.context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            tvPearsonItemHeader.text = curPearsonItem.id
        }
    }

    override fun getItemCount(): Int {
        return relationList.size
    }
}