package com.example.quizapp.data

import com.example.quizapp.data.model.FakeQuestionDao
import com.example.quizapp.data.model.Question
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.util.*


internal class QuizViewModelTest{

    private val question1 =  Question(question = "Under which of the following Android is licensed?",
    option1 =  "OSS" , option2 = "Sourceforge" , option3 = "Apache/MIT" , option4 = "None of the above", answer = "Apache/MIT")
    private val question2 = Question(question = "Which of the following virtual machine is used by the Android operating system?", option1 =  "JVM", option2= "Dalvik virtual machine", option3 = "Simple virtual machine", option4 =  "None of the above", answer = "Dalvik virtual machine")
    private val question3 = Question(question ="Android is based on which of the following language?",
    option1 = "Java", option2 = "C++", option3 = "C", option4 = "None of the above", answer = "Java")
    private val question4 = Question(question = "Which of the following converts Java byte code into Dalvik byte code?",
    option1 =  "Dalvik converter", option2 = "Dex compiler", option3 = "Mobile interpretive compiler (MIC)", option4 = "None of the above", answer = "Dex compiler")
    private val question5 = Question(question = "Which of the following kernel is used in Android?",
    option1 =  "MAC", option2 =  "Windows", option3 = "Linux", option4 = "Redhat", answer = "Linux")

    private var questions = listOf<Question>(question1, question2, question3, question4, question5)
    private lateinit var viewModelTest: QuizViewModel

    @Before
    fun setUpViewModel(){
        viewModelTest = QuizViewModel(FakeQuestionDao(questions))
    }

    @Test
    fun getQuestions_returnsAllQuestions(){
        viewModelTest.getQuestions()

        assertTrue(viewModelTest.questionList.toSet() == questions.toSet())
    }

    @Test
    fun getNextQuestion_currentIndexIsAlwaysLessThanNoOfQuestions(){
        viewModelTest.setCurrentIndex(NO_OF_QUESTIONS + 1)
        viewModelTest.getNextQuestion()

        assertTrue(viewModelTest.currentQuestionIndex < NO_OF_QUESTIONS)
    }

    @Test
    fun getPreviousQuestion_currentIndexIsAlwaysGreaterThanEqualToZero(){
        viewModelTest.setCurrentIndex(-3)
        viewModelTest.getPreviousQuestion()

        assertTrue(viewModelTest.currentQuestionIndex >= 0)
    }
}