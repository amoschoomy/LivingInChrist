package com.amoschoojs.livinginchrist.provider

import android.app.Application
import androidx.lifecycle.LiveData


class QuizRepository(application: Application?) {
    private var quizDAO: QuizDAO? = null
    private var allQuizzes: LiveData<List<Quiz?>?>? = null

   init {
        val db: QuizDatabase? = application?.let { QuizDatabase.getDatabase(it.applicationContext) }
        quizDAO = db?.quizDAO()
        allQuizzes = quizDAO!!.getAllQuizzes()
    }

    fun getAllCars(): LiveData<List<Quiz?>?>? {
        return allQuizzes
    }
}