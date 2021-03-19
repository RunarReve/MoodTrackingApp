package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.data.relationCollection
import com.rever.moodtrack.relationMethods.Regression
import kotlinx.android.synthetic.main.pearson_item.view.*

class RegressionAdapter(
    private var relationList: List<relationCollection>
) : RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>(){

    fun doRegression(questionCollList: MutableList<questionCollection>){
        relationList = Regression.doRegression(questionCollList)
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
        val curRegressionItem = relationList[position]
        val regressionAdapter = PearsonItemAdapter(curRegressionItem)

        holder.itemView.apply {
            rvPearsonItem.adapter = regressionAdapter
            rvPearsonItem?.layoutManager =
                LinearLayoutManager(
                    rvPearsonItem.context,
                    LinearLayoutManager.HORIZONTAL,
                    false)
            tvPearsonItemHeader.text = curRegressionItem.id
        }
    }

    override fun getItemCount(): Int {
        return relationList.size
    }
}