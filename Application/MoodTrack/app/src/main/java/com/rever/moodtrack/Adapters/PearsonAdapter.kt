package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.PearsonCollection
import com.rever.moodtrack.data.QuestionCollection
import com.rever.moodtrack.relationMethods.pearsonCorrelation
import kotlinx.android.synthetic.main.pearson_item.view.*

class PearsonAdapter(
private var pearsonList: List<PearsonCollection>
) : RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>(){

    fun doPearson(questionCollList: MutableList<QuestionCollection>){
        pearsonList = pearsonCorrelation.doPearson(questionCollList)
        notifyItemInserted(pearsonList.size - 1)
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
        val curPearsonItem = pearsonList[position]
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
        return pearsonList.size
    }

}