package com.amoschoojs.livinginchrist

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.VibrationEffect
import android.os.Vibrator


interface QuizHandler {

    fun mReadDataOnce(onGetDataListener: OnGetDataListener)
    fun checkDatabase()
    fun randomSetter(): List<Int> {
        var set = setOf(1, 2, 3, 4)
        val first = set.random()
        set = set.minus(first)
        val second = set.random()
        set = set.minus(second)
        val third = set.random()
        set = set.minus(third)
        val fourth = set.iterator().next()
        return listOf(first, second, third, fourth)

    }

    fun setButtonVal(index: Int, choice: String, list: List<Int>)
    fun quizDisplay()
    fun handleAnswer()
    fun resetState()
    fun disableButtonAfterAnswering()
    fun nextQuestion()
    fun vibrateAns(context: Context) {
        (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
            VibrationEffect.createOneShot(
                200,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }


}