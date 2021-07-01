package com.amoschoojs.livinginchrist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class BookStudy : AppCompatActivity() {
    private val arrayType: Type =object: TypeToken<ArrayList<String?>?>(){}.type
    private val gson= Gson()
    private lateinit var books:Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_study)

        books= arrayOf("Genesis","Exodus","Numbers","Leviticus","Deutoronomy")
        val bookRecyclerViewAdapter=BooksRecyclerViewAdapter(books)
        val bookrecview=findViewById<RecyclerView>(R.id.bookrecview)
        val linearLayoutManager=LinearLayoutManager(this)
        bookrecview.layoutManager=linearLayoutManager
        bookrecview.adapter=bookRecyclerViewAdapter
        val bookrecviewAdapter= bookrecview.adapter
        bookRecyclerViewAdapter.notifyDataSetChanged()


    }
}