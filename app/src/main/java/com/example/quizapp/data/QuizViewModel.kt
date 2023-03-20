package com.example.quizapp.data

import androidx.lifecycle.*
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.QuestionDao
import kotlinx.coroutines.launch

class QuizViewModel(private val questionDao: QuestionDao): ViewModel() {
    var currentQuestionIndex = -1
//    val currentQuestionIndex: Int
//        get() = _currentQuestionIndex

    private var _attempted = 0
    val attempted: Int
        get() = _attempted

    private var _score = 0
    val score: Int
        get() = _score


    var questionList: List<Question> = questionDao.getQuestions().shuffled().take(MAX_QUESTION)

    var _answerList: MutableList<String> = MutableList(MAX_QUESTION){""}


    fun getNextQuestion(): Boolean{
        currentQuestionIndex++
        if(currentQuestionIndex >= MAX_QUESTION){
            // If questions are over then return false
            currentQuestionIndex--
            return false
        }
        return true
    }

    fun addAnswer(answer: String){
        _answerList[currentQuestionIndex] = answer
    }

    fun getPreviousQuestion(){
        if(isFirstQuestion())
            return
        currentQuestionIndex--
    }

    fun evaluateAnswers(){
        for(i in 0 until MAX_QUESTION){
            if(_answerList[i] != "") {
                _attempted++
            }
            if(_answerList[i].equals(questionList[i].answer)){
                _score++
            }
        }
    }

    fun isFirstQuestion(): Boolean{
        return currentQuestionIndex == 0
    }

    fun isLastQuestion(): Boolean{
        return currentQuestionIndex == (MAX_QUESTION - 1)
    }

    fun hasPlayerWon(): Boolean{
        return score == MAX_QUESTION
    }

    fun isSelectedAnswer(option: String): Boolean{
        return (option == _answerList[currentQuestionIndex])
    }

//    init {
//        getAllQuestions()
//    }

    fun insertQuestions(question: Question){
        viewModelScope.launch {
            questionDao.insertQuestion(question)
        }
    }

}

class QuizViewModelFactory(private val questionDao: QuestionDao): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(questionDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}