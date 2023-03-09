package com.example.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // As the user clicks on the start button the Quiz activity is started using a explicit intent
        binding.startQuizBtn.setOnClickListener{
            val intent = Intent(applicationContext, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}