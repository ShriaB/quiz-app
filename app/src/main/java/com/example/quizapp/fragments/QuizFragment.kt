package com.example.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.quizapp.data.QuizViewModel
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.QuizApplication
import com.example.quizapp.R
import com.example.quizapp.data.NO_OF_QUESTIONS
import com.example.quizapp.data.QuizViewModelFactory
import com.example.quizapp.data.model.Question
import com.example.quizapp.data.quizSessionActive

/**
 * @property binding FragmentQuizBinding for view binding
 * @property viewModel object of the QuizViewModel
 * @property navController for navigation in using Navigation Component
 * @property currentIndex index of the question the fragment is bound to
 * @property currentQuestion the question displyed in the fragment
 */

class QuizFragment: Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by activityViewModels{
        QuizViewModelFactory(
            (activity?.application as QuizApplication).database.questionDao()
        )
    }
    private lateinit var navController: NavController

    private var currentIndex: Int = -1
    private lateinit var currentQuestion: Question

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * Fetching the index of the current question passed to the fragment
         * We get the current question from the questionList
         */
        currentIndex = arguments?.getInt("index")!!
        currentQuestion = viewModel.questionList[currentIndex]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        /**
         * Setting the currentIndex in viewmodel to this index
         * Two way binding is achieved so that when the user lands on this fragment while back navigation
         * Then also the current index in viewModel gets updated
         * Otherwise if we back navigate then the current index in view model does not decrease
         */
        viewModel.setCurrentIndex(currentIndex)
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navController = view.findNavController()
        bindDataToView()
    }

    private fun bindDataToView(){
        /**
         * Setting the question
         */
        binding.questionView.text = currentQuestion.question

        /**
         * For each of the options we first set the text
         * Then check if it was selected as the answer by the user previously or not
         * If yes then we check the corresponding radio button
         */
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

        binding.currentQuestion.text = getString(R.string.current_question, currentIndex+1, NO_OF_QUESTIONS)

        /**
         * If the current question is the last question
         * then the text of next button changes from "Next" to "Submit"
         * Otherwise it remains next
         */
        if (viewModel.isLastQuestion()) {
            binding.nextBtn.text = getString(R.string.submit_btn)
        }
        else{
            binding.nextBtn.text = getString(R.string.next_btn)

        }

        binding.optionGroup.setOnCheckedChangeListener{group,checkedId ->
            handleAnswerSelect(group, checkedId)
        }

        binding.nextBtn.setOnClickListener{
            /**
             * If more questions are left then call the quiz fragment and pass the index
             */
            if(viewModel.getNextQuestion()) {
                navController.navigate(
                    QuizFragmentDirections.actionQuizFragmentSelf(
                        viewModel.currentQuestionIndex
                    )
                )
            }
            /**
             * If all the questions are exhausted then
             * evaluate the answers
             * call the quiz over fragment or the quiz won fragment based on the results
             * Reset the score, attempted and answerlist variables
             */
            else{
                /**
                 * Quiz session is over so it is set to false
                 * Attempted answers are evaluated against the correct answers
                 */
                quizSessionActive = false
                viewModel.evaluateAnswers()
                /**
                 * Empty the backstack upto homefragment not inclusive
                 * So that when we back navigate from QuizWon or QuizOver fragments then we land on the
                 * HomeFragment and not on the last question of the QuizFragment
                 */
                navController.popBackStack(R.id.homeFragment, false)

                if(viewModel.hasPlayerWon()){
                    /**
                     * If player wins QuizWonFragment is called
                     */
                    navController.navigate(R.id.quizWonFragment)
                }else{
                    /**
                     * If the player is not able to answer all the questions correctly then QuizOverFragment is called
                     * The total questions attempted and the no. of correct answers are passes to it
                     */
                    navController.navigate(
                        R.id.quizOverFragment,
                        args = bundleOf(
                            "score" to viewModel.score,
                            "attempted" to viewModel.attempted
                        )
                    )
                }
                viewModel.resetViewModel()
            }
        }

        binding.prevBtn.setOnClickListener{
            /**
             * When previous button is clicked the we go to the previous fragment by navigating up
             */
            if(viewModel.getPreviousQuestion()) {
                navController.navigateUp()
            }
        }
    }

    private fun handleAnswerSelect(group: RadioGroup, checkedId: Int) {
        /**
         * When any option is selected it is added to the answer list
         */
        if (checkedId != -1) {
            val checkedRadio = group.findViewById<RadioButton>(checkedId)
            viewModel.addAnswer(checkedRadio.text.toString())
        }
    }
}