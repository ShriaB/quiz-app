package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.data.NO_OF_QUESTIONS
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.data.QuizViewModelFactory
import com.example.quizapp.databinding.FragmentQuizOverBinding

class QuizOverFragment : Fragment() {

    private lateinit var binding: FragmentQuizOverBinding
    private val args: QuizOverFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            /**
             * Using arguments passed to this fragment for the text view to give feedback on user's performance
             */
            totalQuestion.text = getString(R.string.total_ques, NO_OF_QUESTIONS)
            attemptedQuestion.text = getString(R.string.attempted_ques, args?.attempted)
            correctQuestion.text = getString(R.string.correct_ques, args?.score)

            /**
             * When try again button is clicked then we navigate to Quiz fragment
             * Where we pass the index of the first question
             */
            tryAgainBtn.setOnClickListener{
                view.findNavController().navigate(
                    QuizOverFragmentDirections.actionQuizOverFragmentToQuizFragment(0)
                )
            }
        }

    }
}