package com.amoschoojs.livinginchrist.provider

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@Database(entities = [Quiz::class],version = 1)
abstract class QuizDatabase:RoomDatabase() {

    private val noOfThreads = 4

    val databaseWriteExecutor: ExecutorService = Executors.newFixedThreadPool(noOfThreads)


    companion object{
        private const val quizDatabaseName = "quiz_database"

        @Volatile
        private var instance: QuizDatabase? = null

        @JvmStatic
        fun  getDatabase(context: Context): QuizDatabase? {
            if (instance == null) {
            synchronized(Quiz::class.java) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuizDatabase::class.java, quizDatabaseName
                    )
                        .build()
                }
            }
        }
        return instance
    }
    }



    abstract fun quizDAO():QuizDAO
}