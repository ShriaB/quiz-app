package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.data.QuizViewModelFactory
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.fragments.QuizFragment
import com.example.quizapp.fragments.QuizOverFragment
import com.example.quizapp.fragments.QuizWonFragment

class QuizActivity : AppCompatActivity(){

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by viewModels {
        QuizViewModelFactory()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
        getNextFragment()
    }

    fun showScoreFragment(hasPlayerWon: Boolean, score: Int){
        if(hasPlayerWon){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuizWonFragment())
                .commit()
        }
        else{
            val newFragment = QuizOverFragment()
            newFragment.arguments = bundleOf("score" to score)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)
                .commit()
        }
    }

    fun getNextFragment(){
        if(viewModel.getNextQuestion()){
            val newFragment = QuizFragment()
            newFragment.arguments = bundleOf(
                "index" to viewModel.currentQuestionIndex
            )
            /**
             First Fragment should not be added to the backstack
             otherwise when we will back navigate it will show blank activity after first fragment is popped
             */
            val sft = supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, newFragment)

            if(supportFragmentManager.fragments.isNotEmpty())
                sft.addToBackStack("question").commit()
            else
                sft.commit()
        }
        else{
            /**
             When all the questions are exhausted then "question" backstack will be cleared and the score fragment will be added to the activity
             So that when we are in score fragment then if back button is pressed we come back to the main activity
             */
            // If questions are over then answers are evaluated
            viewModel.evaluateAnswers()
            supportFragmentManager.popBackStack("question", FragmentManager.POP_BACK_STACK_INCLUSIVE)
            // the result fragment is displayed based on the score
            showScoreFragment(viewModel.hasPlayerWon(), viewModel.score)
        }
    }

    fun getPrevFragment(){
        if(!viewModel.isFirstQuestion()) {
            viewModel.getPreviousQuestion()
            supportFragmentManager.popBackStack()
        }
    }

    fun newGame(){
        finish()
    }
}