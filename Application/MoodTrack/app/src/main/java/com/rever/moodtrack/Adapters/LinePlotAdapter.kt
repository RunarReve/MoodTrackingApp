package com.rever.moodtrack.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.Utils
import com.rever.moodtrack.R
import kotlinx.android.synthetic.main.line_plot.view.*

class LinePlotAdapter(
    private val dataSet: MutableList<LineDataSet>,
    val context: Context
): RecyclerView.Adapter<StatisticsAdapter.StatisticViewHolder>() {

    fun addDataSet(){

    }

    fun testAdd(){
        println("ECHO Test ")
        val entries = ArrayList<Entry>()
        for (x in 0..7) {
            println("ECHO Test loop ${x}")
            entries.add(Entry(x.toFloat(), x.toFloat()))
        }
        println("ECHO Test 2")

        val vl = LineDataSet(entries, "Thing One")
        println("ECHO Test 3")
        vl.setDrawValues(false)
        println("ECHO Test 4")
        vl.lineWidth = 5f
        vl.color = R.color.black

        dataSet.add(vl)
        println("ECHO ${dataSet}")
        notifyItemInserted(dataSet.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): StatisticsAdapter.StatisticViewHolder {
        Utils.init(parent.context)
        return StatisticsAdapter.StatisticViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.line_plot,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: StatisticsAdapter.StatisticViewHolder, position: Int) {
        holder.itemView.apply {
            lineChart.data = LineData(dataSet as List<ILineDataSet>)
            lineChart.setPinchZoom(true)
        }
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}