package com.amoschoojs.livinginchrist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner

class ThemeStudy : AppCompatActivity() {

    private lateinit var arrayPeace:ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_theme_study)
        val peaceSpinner=findViewById<Spinner>(R.id.peacespinner)
        arrayPeace= ArrayList()
        val peaceAdapter=ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayPeace)
        peaceSpinner.adapter=peaceAdapter
    }
}