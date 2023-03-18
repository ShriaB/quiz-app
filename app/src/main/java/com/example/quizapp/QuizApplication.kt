package com.example.quizapp

import android.app.Application
import com.example.quizapp.data.model.QuestionRoomDatabase

class QuizApplication: Application() {
    val database: QuestionRoomDatabase by lazy { QuestionRoomDatabase.getDatabase(this) }
}