package com.example.quizapp.data

import androidx.lifecycle.*
import androidx.room.Index
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.model.QuestionDao
import kotlinx.coroutines.launch

/**
 * @property questionList list of the questions after fetching them from database and shuffling and taking the first 5 questions
 * @property _currentQuestionIndex index of the current question in the list of questions
 * @property _answerList list of the attempted answers
 * @property _attempted no of questions user has attempted
 * @property _score no of questions user has answered correctly
 */

class QuizViewModel(private val questionDao: QuestionDao): ViewModel() {
    lateinit var questionList: List<Question>
    private var _answerList: MutableList<String> = MutableList(NO_OF_QUESTIONS){""}
    private var _currentQuestionIndex = -1
    val currentQuestionIndex: Int
        get() = _currentQuestionIndex

    private var _attempted = 0
    val attempted: Int
        get() = _attempted

    private var _score = 0
    val score: Int
        get() = _score


    fun setCurrentIndex(index: Int){
        _currentQuestionIndex = index
    }

    /**
     * Fetches the question from database using the questionDao
     * shuffles them
     * takes the first 5 questions
     * This gets us some random questions everytime a new quiz is started
     */
    fun getQuestions(){
        questionList = questionDao.getQuestions().shuffled().take(NO_OF_QUESTIONS)
    }

    /**
     * If there are more questions left then _currentQuestionIndex is incremented by one
     * and true is returned to indicate that more questions are left
     * Else _currentQuestionIndex is decremented by one to keep it inside the array index range
     * and false is returned to indicated no more questions are left
     */
    fun getNextQuestion(): Boolean{
        _currentQuestionIndex++
        if(_currentQuestionIndex >= NO_OF_QUESTIONS){
            // If questions are over then return false
            _currentQuestionIndex--
            return false
        }
        return true
    }

    /**
     * If the current question is the first questions then it returns false
     * indicating that there is no previous question
     * Else decrements _currentQuestionIndex by one and returns true
     * indicating that there are questions before
     */
    fun getPreviousQuestion(): Boolean{
        if(isFirstQuestion())
            return false
        _currentQuestionIndex--
        return true
    }

    /**
     * Adds the users selected answer to the answerList
     */
    fun addAnswer(answer: String){
        _answerList[_currentQuestionIndex] = answer
    }

    /**
     * Evaluates the no. of correct answers and attempted answers at the end of the quiz
     */
    fun evaluateAnswers(){
        for(i in 0 until NO_OF_QUESTIONS){
            if(_answerList[i] != "") {
                _attempted++
            }
            if(_answerList[i].equals(questionList[i].answer)){
                _score++
            }
        }
    }

    /**
     * Checks if current question is the first question
     */
    fun isFirstQuestion(): Boolean{
        return _currentQuestionIndex == 0
    }

    /**
     * Checks if current question is the last question
     */
    fun isLastQuestion(): Boolean{
        return _currentQuestionIndex == (NO_OF_QUESTIONS - 1)
    }

    /**
     * Checks if player has won
     */
    fun hasPlayerWon(): Boolean{
        return score == NO_OF_QUESTIONS
    }

    /**
     * Checks if the given option was previously selected as an answer by the user
     */
    fun isSelectedAnswer(option: String): Boolean{
        return (option == _answerList[_currentQuestionIndex])
    }

    /**
     * Resetting few variables of the viewModel
     * so that if another quiz is started then
     * currentQuestionIndex, answerList, score and attempted are reset
     */
    fun resetViewModel(){
        _currentQuestionIndex = -1
        _score = 0
        _attempted = 0
        _answerList = MutableList(NO_OF_QUESTIONS){""}
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