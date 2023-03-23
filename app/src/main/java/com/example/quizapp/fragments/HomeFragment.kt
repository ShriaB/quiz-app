package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.data.QuizViewModelFactory
import com.example.quizapp.data.quizSessionActive
import com.example.quizapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: QuizViewModel by activityViewModels {
        QuizViewModelFactory(
            (activity?.application as QuizApplication).database.questionDao()
        )
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        /**
         * If the user is not in an active quiz session then a new set of question is loaded
         * Two cases:
         * a) When user launches the activity for the first time
         * b) When user back navigates from the last fragment (QuizWon or QuizOver)
         */
        if(!quizSessionActive) {
            viewModel.getQuestions()
        }
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.startQuizBtn.setOnClickListener{
            /**
             * As the user clicks on the start button
             * quiz session is active
             * User goes to the quiz fragment
             * The quiz fragment is displayed which contains the questionnaire
             * The index of the first question (0) is passed to this fragment
             */
            quizSessionActive = true
            view.findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToQuizFragment(0)
            )
            }
        }
    }
