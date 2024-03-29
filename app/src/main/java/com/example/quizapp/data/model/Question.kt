package com.example.quizapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question")
data class Question(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "question")
    val question: String,
    @ColumnInfo(name = "option1")
    val option1: String,
    @ColumnInfo(name = "option2")
    val option2: String,
    @ColumnInfo(name = "option3")
    val option3: String,
    @ColumnInfo(name = "option4")
    val option4: String,
    @ColumnInfo(name = "answer")
    val answer: String
)
