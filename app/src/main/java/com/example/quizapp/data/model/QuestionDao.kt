package com.example.quizapp.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {
    @Query("SELECT * FROM question")
    fun getQuestions(): List<Question>

    @Insert
    suspend fun insertQuestion(question: Question)
}