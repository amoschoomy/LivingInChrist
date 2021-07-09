package com.amoschoojs.livinginchrist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.MaterialShapeDrawable

class BookStudyAdapter(private val qnas: ArrayList<QnAModel>) :
    RecyclerView.Adapter<BookStudyAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        val question: TextView = itemView.findViewById(R.id.question)
        val answer: TextView = itemView.findViewById(R.id.answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.qna_layout, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val qna = qnas[holder.adapterPosition]

        val qtv = holder.question
        val atv = holder.answer
        val context = qtv.context

        val shapeDrawable = MaterialShapeDrawable()
        shapeDrawable.fillColor =
            ContextCompat.getColorStateList(context, android.R.color.transparent)
        shapeDrawable.setStroke(4.0f, ContextCompat.getColor(context, R.color.light_blue_A200))
        ViewCompat.setBackground(qtv, shapeDrawable)

        val shapeDrawable2 = MaterialShapeDrawable()
        shapeDrawable2.fillColor =
            ContextCompat.getColorStateList(context, android.R.color.transparent)
        shapeDrawable2.setStroke(
            4.0f, ContextCompat.getColor(
                context,
                R.color.light_yellow
            )
        )
        ViewCompat.setBackground(atv, shapeDrawable2)



        qtv.text = qna.question
        atv.text = qna.answer

    }

    override fun getItemCount(): Int {
        return qnas.size
    }
}