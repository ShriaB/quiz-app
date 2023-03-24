package com.example.quizapp.data.model

class FakeQuestionDao(private var questions: List<Question>): QuestionDao {
    override fun getQuestions(): List<Question> {
        return questions
    }
}