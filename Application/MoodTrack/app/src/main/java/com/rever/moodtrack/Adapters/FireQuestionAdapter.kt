package com.rever.moodtrack.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.recyclerview.widget.RecyclerView
import com.rever.moodtrack.R
import com.rever.moodtrack.data.CustomNeed
import com.rever.moodtrack.data.FireQuestion
import com.rever.moodtrack.data.NeedStore.Need
import com.rever.moodtrack.data.QuestionStore.Question
import kotlinx.android.synthetic.main.need_question_item.view.*

class FireQuestionAdapter (
    private val questions: MutableList<FireQuestion>
        ): RecyclerView.Adapter<FireQuestionAdapter.QuestionViewHolder>() {
        class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        return QuestionViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.need_question_item,
                parent,
                false
            )
        )
    }

    fun addNeed(need: CustomNeed){
        val q = FireQuestion(need.NeedTitle, need.type, -1)
        questions.add(q)
        notifyItemInserted(questions.size - 1)
    }

    fun addQuestion(question: FireQuestion){
        questions.add(question)
        notifyItemInserted(questions.size - 1)
    }

    fun addQuestionPrimary(title: String){
        val q = FireQuestion(title, 1,4)
        questions.add(q)
        notifyItemInserted(questions.size - 1)
    }
    fun addQuestion(title: String){
        val q = FireQuestion(title, 0,4)
        questions.add(q)
        notifyItemInserted(questions.size - 1)
    }

    //Returns a comma seperated string with
    fun getItemCount2(i: Int): String {
        return questions[i].title + ',' + questions[i].rate
    }

    fun getSize(): Int {
        return questions.size
    }
    fun getTitle(i: Int): String{
        return questions[i].title
    }
    fun getrate(i: Int): Int{
        return questions[i].rate
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
            override fun onStartTrackingTouch(p0: SeekBar?) {return}
            //Do nothing
            override fun onStopTrackingTouch(p0: SeekBar?) {return}
        })
    }

    override fun getItemCount(): Int {
        return questions.size
    }

}