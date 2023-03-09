package com.example.quizapp.data

import androidx.lifecycle.ViewModel
import com.example.quizapp.model.Question

class QuizViewModel: ViewModel() {
    private var _currentQuestionIndex = -1
    val currentQuestionIndex: Int
        get() = _currentQuestionIndex

    private lateinit var _currentQuestion: Question
    val currentQuestion: Question
        get() = _currentQuestion

    private var _score = 0
    val score: Int
        get() = _score

    private var _answerList: MutableList<String> = MutableList(MAX_QUESTION){""}

    fun getNextQuestion(): Boolean{
        _currentQuestionIndex++
        if(_currentQuestionIndex >= MAX_QUESTION){
            // If questions are over then return false
            return false
        }
        _currentQuestion = questionList[_currentQuestionIndex]
        return true
    }

    fun addAnswer(answer: String){
        _answerList[currentQuestionIndex] = answer
    }

    fun getPreviousQuestion(){
        if(isFirstQuestion())
            return
        _currentQuestionIndex--
        _currentQuestion = questionList[_currentQuestionIndex]

    }

    fun evaluateAnswers(){
        for(i in 0 until MAX_QUESTION){
            if(_answerList[i].equals(questionList[i].answer)){
                _score++
            }
        }
    }

    fun isFirstQuestion(): Boolean{
        return _currentQuestionIndex == 0
    }

    fun isLastQuestion(): Boolean{
        return _currentQuestionIndex == (MAX_QUESTION - 1)
    }

    fun hasPlayerWon(): Boolean{
        return score == MAX_QUESTION
    }

    fun isSelectedAnswer(option: String): Boolean{
        return (option == _answerList[currentQuestionIndex])
    }

    init{
        getNextQuestion()
    }

}