package com.amoschoojs.livinginchrist.provider

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData


class QuizViewModel(application: Application) : AndroidViewModel(application) {
    private var quizRepository: QuizRepository = QuizRepository(application)
    private val allCars: LiveData<List<Quiz?>?>? = quizRepository.getAllCars()

    fun getAllCars(): LiveData<List<Quiz?>?>? {
        return allCars
    }


}