package com.example.quizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import com.example.quizapp.fragments.QuizOverFragment
import com.example.quizapp.fragments.QuizWonFragment

class QuizActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)
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

    fun newGame(){
        finish()
    }
}