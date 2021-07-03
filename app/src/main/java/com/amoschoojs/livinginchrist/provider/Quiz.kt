package com.amoschoojs.livinginchrist.provider

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quiz")
class Quiz(

    @ColumnInfo(name="question")
    private val question: String,

    @ColumnInfo(name="answer")
    private val answer: String,

    @ColumnInfo(name="choice1")
    private val choice1: String,

    @ColumnInfo(name="choice2")
    private val choice2: String,

    @ColumnInfo(name="choice3")
    private val choice3: String,

    @ColumnInfo(name="choice4")
    private val choice4: String,

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name="quizID")
    private val quizID:Int
) {

}