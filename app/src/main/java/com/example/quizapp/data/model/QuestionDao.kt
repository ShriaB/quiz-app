package com.example.quizapp.data.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface QuestionDao {
    /**
     * Fetches all the questions from the database
     * Returns a List of Question
     */
    @Query("SELECT * FROM question")
    fun getQuestions(): List<Question>
}