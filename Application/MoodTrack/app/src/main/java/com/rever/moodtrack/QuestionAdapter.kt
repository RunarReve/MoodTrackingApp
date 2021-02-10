package com.rever.moodtrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.question_item.view.*

class QuestionAdapter (
    private val questions: MutableList<Question>
        ): RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder>() {
        class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.question_item,
                parent,
                false
            )
        )
    }

    fun addQuestion(question: Question){
        questions.add(question)
        notifyItemInserted(questions.size - 1)
    }

    //Returns a comma seperated string with
    fun getItemCount2(i: Int): String {
        return questions[i].title + ',' + questions[i].rate
    }

    fun getSize(): Int {
        return questions.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val curQuestion = questions[position]
        holder.itemView.apply {
            tvHeader.text = curQuestion.title
            sbRankBar.progress = curQuestion.rate
        }
        holder.itemView.sbRankBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                curQuestion.rate = p1
            }

            //Do nothing
            override fun onStartTrackingTouch(p0: SeekBar?) {
                return
            }
            //Do nothing
            override fun onStopTrackingTouch(p0: SeekBar?) {
                return
            }


        })
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}