package com.amoschoojs.livinginchrist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment


class BibleStudyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bible_study, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val themeCard = view.findViewById<CardView>(R.id.themecard)
        val bookCard = view.findViewById<CardView>(R.id.bookcard)

        themeCard.setOnClickListener {
            val intent = Intent(activity, ThemeStudy::class.java)
            startActivity(intent)
        }

        bookCard.setOnClickListener {
            val intent = Intent(activity, BookStudy::class.java)
            startActivity(intent)
        }
    }


}