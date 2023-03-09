package com.example.quizapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import com.example.quizapp.QuizActivity
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.databinding.FragmentQuizBinding
import androidx.fragment.app.viewModels
import com.example.quizapp.R
import com.example.quizapp.data.MAX_QUESTION

class QuizFragment: Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        binding.optionGroup.clearCheck()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindDataToView()

        binding.nextBtn.setOnClickListener{
            handleClickOnNextBtn()
        }

        binding.prevBtn.setOnClickListener{
            handleClickOnPrevBtn()
        }
    }

    fun bindDataToView() {

        // clearing on check listener for radio group
        // otherwise when the selection is cleared that also triggers the listener
        binding.optionGroup.setOnCheckedChangeListener(null)

        // clearing selection for all the options
        binding.optionGroup.clearCheck()

        // setting the question text
        binding.questionView.text = viewModel.currentQuestion.question

        // setting the text of radio buttons and checking the button which was selected as answer
        // Option 1
        binding.option1.text = viewModel.currentQuestion.option1
        if(viewModel.isSelectedAnswer(viewModel.currentQuestion.option1))
            binding.option1.isChecked = true

        // Option 2
        binding.option2.text = viewModel.currentQuestion.option2
        if(viewModel.isSelectedAnswer(viewModel.currentQuestion.option2))
            binding.option2.isChecked = true

        // Option 3
        binding.option3.text = viewModel.currentQuestion.option3
        if(viewModel.isSelectedAnswer(viewModel.currentQuestion.option3))
            binding.option3.isChecked = true

        // Option 4
        binding.option4.text = viewModel.currentQuestion.option4
        if(viewModel.isSelectedAnswer(viewModel.currentQuestion.option4))
            binding.option4.isChecked = true

        // setting the question number
        binding.currentQuestion.text = getString(R.string.current_question, viewModel.currentQuestionIndex+1, MAX_QUESTION)

        // setting on change listener to radio group
        binding.optionGroup.setOnCheckedChangeListener{group,checkedId ->
            handleAnswerSelect(group, checkedId)
        }
    }

    fun handleClickOnNextBtn(){
        if(viewModel.getNextQuestion()){
            // If questions are not over then
            // then question is fetched and bound to the views in the fragment
            if (viewModel.isLastQuestion()) {
                // If the current question is the last question
                // then the text of next button changes from "Next" to "Submit"
                binding.nextBtn.text = getString(R.string.submit_btn)
            }
            else{
                binding.nextBtn.text = getString(R.string.next_btn)

            }
            bindDataToView()
        }
        else{
            // If questions are over then answers are evaluated
            viewModel.evaluateAnswers()
            // the result fragment is displayed based on the score
            (activity as QuizActivity).showScoreFragment(viewModel.hasPlayerWon(), viewModel.score)
        }
    }

    fun handleClickOnPrevBtn(){
        // If the current question is not the first question then the previous question is loaded
        if(!viewModel.isFirstQuestion()) {
            viewModel.getPreviousQuestion()
            // If the current question is not the last question
            // then the text of next button changes to "Next"
            if(binding.nextBtn.text == "Submit" && !viewModel.isLastQuestion()){
                binding.nextBtn.text = getString(R.string.next_btn)
            }
            bindDataToView()
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