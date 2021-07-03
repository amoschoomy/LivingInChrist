package com.amoschoojs.livinginchrist.provider

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuizDAO {

    @Query("SELECT * from quiz")
    fun getAllQuizzes(): LiveData<List<Quiz?>?>

}