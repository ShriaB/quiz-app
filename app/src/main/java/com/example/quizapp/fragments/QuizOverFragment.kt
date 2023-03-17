package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import com.example.quizapp.QuizActivity
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentQuizOverBinding

class QuizOverFragment : Fragment() {

    private lateinit var binding: FragmentQuizOverBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.scoreTextView.text = getString(R.string.score_text,arguments?.getInt("score"))
        binding.tryAgainBtn.setOnClickListener{
            (activity as QuizActivity).newGame()
        }
    }
}