package com.amoschoojs.livinginchrist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BookStudyAdapter(private val qnas:ArrayList<QnAModel>):RecyclerView.Adapter<BookStudyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val question = itemView.findViewById<TextView>(R.id.question)
        val answer = itemView.findViewById<TextView>(R.id.answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.qna_layout, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val qna=qnas[holder.adapterPosition]

        val qtv=holder.question
        val atv=holder.answer
        qtv.text=qna.question
        atv.text=qna.answer

    }

    override fun getItemCount(): Int {
        return qnas.size
    }
}