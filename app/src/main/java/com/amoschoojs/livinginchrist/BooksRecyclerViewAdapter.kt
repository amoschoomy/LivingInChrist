package com.amoschoojs.livinginchrist

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.shape.MaterialShapeDrawable

/**
 * RecyclerViewAdapter for book list to be displayed
 */
class BooksRecyclerViewAdapter(private val array: Array<String>) :
    RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder>() {

    /**
     * View Holder class that extends [RecyclerView.ViewHolder] class
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val bookView: TextView = itemView.findViewById(R.id.book)


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BooksRecyclerViewAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        // Inflate the custom layout
        val bookView = inflater.inflate(R.layout.booklist, parent, false)
        // Return a new holder instance
        return ViewHolder(bookView)
    }

    override fun onBindViewHolder(holder: BooksRecyclerViewAdapter.ViewHolder, position: Int) {
        val book = array[holder.adapterPosition]
        val textView = holder.bookView
        val context = textView.context

        val shapeDrawable = MaterialShapeDrawable()
        shapeDrawable.fillColor =
            ContextCompat.getColorStateList(context, android.R.color.transparent)
        shapeDrawable.setStroke(1.0f, ContextCompat.getColor(context, R.color.light_blue_A200))
        ViewCompat.setBackground(textView, shapeDrawable) //set TextView borders

        textView.text = book
        textView.setOnClickListener {
            val i = Intent(context, SpecificBook::class.java)
            i.putExtra("whatBook", book)
            context.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        return array.size
    }
}