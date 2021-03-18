package com.rever.moodtrack.Adapters

import android.content.Context
import android.graphics.Color.*
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.rever.moodtrack.data.questionCollection
import com.rever.moodtrack.R
import com.rever.moodtrack.relationMethods.pearsonCorrelation
import kotlinx.android.synthetic.main.line_plot_item.view.*

class LinePlotAdapter(
    private val dataSet: MutableList<LineDataSet>,
    val context: Context
): RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>() {

    //Currently 6 different colors
    private var qTitleList = mutableListOf<String>()
    private val colorList = arrayOf(
            parseColor("#FF1100"),
            parseColor("#155802"),
            parseColor("#3F51B5"),
            parseColor("#CDDC39"),
            parseColor("#D84315"),
            parseColor("#CC5880"))

    //Takes in a list of questionCollections and plots it
    fun addDataSet(list : List<questionCollection>){//TODO sort so annotation is same as rest
        val newList = pearsonCorrelation.questionCollection2PearsonCollection(list)
        val primaryList = pearsonCorrelation.getPrimaryTitels(list)
        newList.forEach {
            val current = it.id
            val entries = ArrayList<Entry>()
            var day = 0f
            it.rateList.forEach {
                day += 1f
                if (it >= 0.0)
                    entries.add(Entry(day, it.toFloat()))
            }
            val data = LineDataSet(entries, current)
            val x = getTitlePoss(current)
            data.color = colorList[x]
            if (it.id in primaryList)
                data.lineWidth = 6f
            else
                data.lineWidth = 4f

            data.setDrawValues(false)
            dataSet.add(data)
        }
        notifyItemInserted(dataSet.size - 1)
    }

    //Gets index for color to use
    private fun getTitlePoss(title: String):Int{
        for(i in 0 until qTitleList.size) //Check if it has been added
            if(title == qTitleList[i])
                return i
        if(qTitleList.size >= colorList.size) //If it more than available, give last
            return colorList.size-1
        qTitleList.add(title) //Not found, give the colors position
        return qTitleList.size-1
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): StatisticsAdapter.StatisticViewHolder {
        Utils.init(parent.context)
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line_plot_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        holder.itemView.apply {
            lineChart.data = LineData(dataSet as List<ILineDataSet>)
            lineChart.setPinchZoom(true)
            lineChart.description.text = "Need2Want"
            //lineChart.setScaleEnabled(false)
            lineChart.axisRight.isEnabled = false //Removes values from right axis
            lineChart.xAxis.isGranularityEnabled = true //Set the interval to Natural numbers

        }
    }

    override fun getItemCount(): Int {
        return 1
    }
}