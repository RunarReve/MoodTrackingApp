package com.rever.moodtrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        notifyItemInserted(questions.size -1)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val curQuestion = questions[position]
        holder.itemView.apply {
            tvHeader.text = curQuestion.title
            sbRankBar.progress = curQuestion.rate
            //sbRankBar
        }
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}