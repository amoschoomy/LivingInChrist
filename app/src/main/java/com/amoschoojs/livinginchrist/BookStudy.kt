package com.amoschoojs.livinginchrist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * BookStudy activity
 */
class BookStudy : AppCompatActivity() {

    private lateinit var books: Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_study)

        books = arrayOf(
            "Genesis",
            "Exodus",
            "Numbers",
            "Leviticus",
            "Deuteronomy",
            "Joshua",
            "Ruth",
            "1 Samuel",
            "2 Samuel",
            "1 Kings",
            "2 Kings",
            "1 Chronicles",
            "2 Chronicles",
            "Ezra",
            "Nehemiah",
            "Nehemiah",
            "Esther",
            "Job",
            "Psalm",
            "Proverbs",
            "Ecclesiastes",
            "Song of Songs",
            "Isaiah",
            "Jeremiah",
            "Lamentations",
            "Ezekiel",
            "Daniel",
            "Hosea",
            "Joel",
            "Amos",
            "Obadiah",
            "Jonah",
            "Micah",
            "Micah",
            "Nahum",
            "Habakkuk",
            "Zephaniah",
            "Haggai",
            "Zechariah",
            "Malachi",
            "Matthew",
            "Mark",
            "Luke",
            "John",
            "Acts",
            "Romans",
            "1 Corinthians",
            "2 Corinthians",
            "Galatians",
            "Ephesians",
            "Philippians",
            "Colossians",
            "1 Thessalonians",
            "2 Thessalonians",
            "1 Timothy",
            "2 Timothy",
            "Titus",
            "Philemon",
            "Hebrews",
            "James",
            "1 Peter",
            "2 Peter",
            "1 John",
            "2 John",
            "3 John",
            "Jude",
            "Revelations"
        )

        //set the array to the adapter
        val bookRecyclerViewAdapter = BooksRecyclerViewAdapter(books)
        val bookrecview = findViewById<RecyclerView>(R.id.bookrecview)
        val linearLayoutManager = LinearLayoutManager(this)
        bookrecview.layoutManager = linearLayoutManager

        //set adapter to the recycler view
        bookrecview.adapter = bookRecyclerViewAdapter
        bookRecyclerViewAdapter.notifyDataSetChanged()


    }
}