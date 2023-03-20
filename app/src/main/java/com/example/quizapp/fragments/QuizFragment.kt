package com.example.quizapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.activityViewModels
import com.example.quizapp.QuizActivity
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.data.MAX_QUESTION
import com.example.quizapp.data.QuizViewModelFactory
import com.example.quizapp.data.model.Question

class QuizFragment: Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by activityViewModels {
        QuizViewModelFactory(
            (activity?.application as QuizApplication).database.questionDao()
        )
    }

    private var currentIndex: Int = -1
    private lateinit var currentQuestion: Question

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        currentIndex = arguments?.getInt("index")!!
        viewModel.currentQuestionIndex = currentIndex
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        currentQuestion = viewModel.questionList[currentIndex]
        bindDataToView()
    }

    fun bindDataToView(){
        binding.questionView.text = currentQuestion.question

        binding.option1.text = currentQuestion.option1
        if(viewModel.isSelectedAnswer(currentQuestion.option1))
            binding.option1.isChecked = true

        binding.option2.text = currentQuestion.option2
        if(viewModel.isSelectedAnswer(currentQuestion.option2))
            binding.option2.isChecked = true

        binding.option3.text = currentQuestion.option3
        if(viewModel.isSelectedAnswer(currentQuestion.option3))
            binding.option3.isChecked = true

        binding.option4.text = currentQuestion.option4
        if(viewModel.isSelectedAnswer(currentQuestion.option4))
            binding.option4.isChecked = true

        binding.currentQuestion.text = getString(R.string.current_question, currentIndex+1, MAX_QUESTION)

        if (viewModel.isLastQuestion()) {
            // If the current question is the last question
            // then the text of next button changes from "Next" to "Submit"
            binding.nextBtn.text = getString(R.string.submit_btn)
        }
        else{
            binding.nextBtn.text = getString(R.string.next_btn)

        }

        binding.optionGroup.setOnCheckedChangeListener{group,checkedId ->
            handleAnswerSelect(group, checkedId)
        }

        binding.nextBtn.setOnClickListener{
            (activity as QuizActivity).getNextFragment()
        }

        binding.prevBtn.setOnClickListener{
            (activity as QuizActivity).getPrevFragment()
        }

    }

    fun handleAnswerSelect(group: RadioGroup, checkedId: Int) {
        // When any option is selected it is added to the answer list
        if (checkedId != -1) {
            val checkedRadio = group.findViewById<RadioButton>(checkedId)
            viewModel.addAnswer(checkedRadio.text.toString())
        }
    }
}