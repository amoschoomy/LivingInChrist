package com.amoschoojs.livinginchrist

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class SpecificBook : AppCompatActivity() {
    private lateinit var arrayList:ArrayList<QnAModel>
    private val arrayType: Type =object: TypeToken<ArrayList<QnAModel>?>(){}.type
    private val gson= Gson()
    private lateinit var sharedPreferences:SharedPreferences
    private lateinit var book:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_book)
        val recview=findViewById<RecyclerView>(R.id.specrecview)

        book=intent.getStringExtra("whatBook")!!
        Toast.makeText(this,book,Toast.LENGTH_SHORT).show()
        sharedPreferences=getSharedPreferences("abc",0)

        val bookValues=sharedPreferences.getString(book,"[]")

        arrayList=gson.fromJson(bookValues,arrayType)

        val specAdapter=BookStudyAdapter(arrayList)
        val linearLayoutManager= LinearLayoutManager(this)
        recview.layoutManager=linearLayoutManager
        recview.adapter=specAdapter
        val bookAdapter=recview.adapter

        val addFAB=findViewById<FloatingActionButton>(R.id.addqna)

        addFAB.setOnClickListener {
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            val titleBox = EditText(this)
            titleBox.hint = "Question/Topic"
            linearLayout.addView(titleBox)
            val contentBox = EditText(this)
            contentBox.hint = "Answer/Explanation"
            linearLayout.addView(contentBox)
            MaterialAlertDialogBuilder(this).setTitle("Add Topic").setView(linearLayout)
                .setPositiveButton("Submit"){_,_->
                    val qna=QnAModel(titleBox.text.toString(),contentBox.text.toString())
                    arrayList.add(qna)
                    bookAdapter?.notifyDataSetChanged()
                }
                .setNegativeButton("Cancel") { _, _ -> }.show() }

        bookAdapter?.notifyDataSetChanged()

    }

    override fun onStop() {
        super.onStop()
        val arrayString=gson.toJson(arrayList)
        sharedPreferences.edit().putString(book,arrayString).apply()
    }
}