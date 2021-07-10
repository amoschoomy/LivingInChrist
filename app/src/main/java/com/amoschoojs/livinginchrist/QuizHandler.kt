package com.amoschoojs.livinginchrist

import android.content.Context
import android.content.Context.VIBRATOR_SERVICE
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * Interface for quiz handler methods
 */
interface QuizHandler {

    /**
     * Function to read data once from database
     */
    fun mReadDataOnce(onGetDataListener: OnGetDataListener)

    /**
     * Function to check database for data
     */
    fun checkDatabase()

    /**
     * Function to randomly set a number from 1-4
     * to different positions
     * @return [List<Int>]
     */
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

    /**
     * Function to set Button value according to the positions in the list given
     */
    fun setButtonVal(index: Int, choice: String, list: List<Int>)

    /**
     * Display quiz to UI
     */
    fun quizDisplay()

    /**
     * Function to handle answers
     */
    fun handleAnswer()

    /**
     * Reset state of the UI in the quiz
     */
    fun resetState()

    /**
     * Disable button choices after answering
     */
    fun disableButtonAfterAnswering()

    /**
     * Move to the next questions
     */
    fun nextQuestion()

    /**
     * Vibration effect
     */
    fun vibrateAns(context: Context) {
        (context.getSystemService(VIBRATOR_SERVICE) as Vibrator).vibrate(
            VibrationEffect.createOneShot(
                200,
                VibrationEffect.DEFAULT_AMPLITUDE
            )
        )
    }


}