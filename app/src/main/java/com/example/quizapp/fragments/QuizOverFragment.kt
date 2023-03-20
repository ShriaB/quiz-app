package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quizapp.QuizActivity
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.data.MAX_QUESTION
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.data.QuizViewModelFactory
import com.example.quizapp.databinding.FragmentQuizOverBinding

class QuizOverFragment : Fragment() {

    private lateinit var binding: FragmentQuizOverBinding
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
        binding = FragmentQuizOverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            totalQuestion.text = getString(R.string.total_ques, MAX_QUESTION)
            attemptedQuestion.text = getString(R.string.attempted_ques, arguments?.getInt("attempted"))
            correctQuestion.text = getString(R.string.correct_ques, arguments?.getInt("score"))
            tryAgainBtn.setOnClickListener{
                (activity as QuizActivity).newGame()
            }
//            recyclerView.layoutManager = LinearLayoutManager(context)
//            recyclerView.adapter = QuizOverListAdapter(requireContext(), viewModel.questionList, viewModel._answerList )
        }

    }
}