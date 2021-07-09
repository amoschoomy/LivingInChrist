package com.amoschoojs.livinginchrist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val dataSet: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {


    class ViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val streakCount: TextView = itemView.findViewById(R.id.streakhistory)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.recview_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.streakCount.text = dataSet[position] + " completed"
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}
