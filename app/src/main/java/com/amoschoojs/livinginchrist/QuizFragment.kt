package com.amoschoojs.livinginchrist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class QuizFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val howToPlayButton = view.findViewById<Button>(R.id.how)
        val scoreMode = view.findViewById<Button>(R.id.score)
        val casualMode = view.findViewById<Button>(R.id.casual)
        val highScore = view.findViewById<Button>(R.id.highscore)

        howToPlayButton.setOnClickListener {
            activity?.let { it1 ->
                MaterialAlertDialogBuilder(it1).setTitle("How to Play")
                    .setMessage(R.string.instructions)
                    .setNeutralButton("OK", null).show()
            }
        }

        casualMode.setOnClickListener {
            val i = Intent(activity, CasualQuiz::class.java); startActivity(i)
        }
        scoreMode.setOnClickListener {
            val i = Intent(activity, TimedQuiz::class.java); startActivity(i)
        }
        highScore.setOnClickListener {
            val sharedPreferences = activity?.getSharedPreferences("abc", 0)
            val highScoreFromPref = sharedPreferences?.getInt("highscore", 0)
            Toast.makeText(activity, "High Score: $highScoreFromPref", Toast.LENGTH_LONG).show()


        }
    }

}