package com.kay.a7minutesworkout.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kay.a7minutesworkout.Constants
import com.kay.a7minutesworkout.ExerciseModel
import com.kay.a7minutesworkout.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment() {

    // Variable for 10 seconds / Variable for Rest timer
    /** --Pause tid-- */
    private var restTimer: CountDownTimer? =
        null // <- CountDownTimer is an abstract class. We need to create a new instance with an object notation.
    private var restProgress =
        0 // <- Variable for timer progress. As initial value the rest progress is set to 0. as we are about to start

    private val restTimerStartValue = 10

    // Exercise timer / ExerciseProgress
    /** --Trenings tid-- */
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

    /** Exercise models*/
    private var exerciseList: MutableList<ExerciseModel>? = null
    private var currentExercisePosition = -1

    private var _binding: FragmentExerciseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentExerciseBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // toolbar setup for fragments
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarExercise)
        // Back button setup on toolbar for fragments
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        exerciseList = Constants.defaultExerciseList()

        // navigate back with navHost
        binding.toolbarExercise.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        setupRestView()
    }

    private fun setupRestView() {
        // (1) Check if the timer (-CountDownTimer-) is running. if it is not null then cancel the running timer and start the new one.
        // (2) Initiate  the restProgress which is 0.
        // In other words, we reset the timer if we go back to the first fragment
        binding.flRestView.visibility = View.VISIBLE
        binding.tvTitle.visibility = View.VISIBLE
        binding.tvExerciseName.visibility = View.INVISIBLE
        binding.flExerciseView.visibility = View.INVISIBLE
        binding.ivExerciseImage.visibility = View.INVISIBLE
        // upcoming Text views
        binding.upcomingLabel.visibility = View.VISIBLE
        binding.tvUpcomingExerciseName.visibility = View.VISIBLE
        // set the text view on the next exercise
        binding.tvUpcomingExerciseName.text = exerciseList!![currentExercisePosition + 1].getName()

            if (restTimer != null) {
            restTimer!!.cancel() // <- !! means this is for sure not null
            restProgress = 0 // <- Initiate restProgress
        }

        // This function is used to set the progress details.
        setRestProgressBar()
    }

    // function which will set the rest progressbar
    private fun setRestProgressBar() {
        binding.progressBar.progress = restProgress

        restTimer = object :
            CountDownTimer(10000, 1000) { // <- it start from 10 sec and every countdown is 1 second
            override fun onTick(millisUntilFinished: Long) {
                // increase restProgress by 1 value
                restProgress++
                // set progressBar value
                binding.progressBar.progress = restTimerStartValue - restProgress
                // set the timer value
                binding.tvTimer.text = (restTimerStartValue - restProgress).toString()
            }

            override fun onFinish() {
                currentExercisePosition++
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {
        // here we reset the timer if we go back to the first fragment
        binding.flRestView.visibility = View.INVISIBLE // <- Rest View Frame Layout is invisible.
        binding.tvTitle.visibility = View.INVISIBLE
        binding.tvExerciseName.visibility = View.VISIBLE
        binding.flExerciseView.visibility = View.VISIBLE // <- Exercise view is visible
        binding.ivExerciseImage.visibility = View.VISIBLE
        // upcoming Text Views
        binding.upcomingLabel.visibility = View.INVISIBLE
        binding.tvUpcomingExerciseName.visibility = View.INVISIBLE

        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
        // set the image view.
        binding.ivExerciseImage.setImageResource(exerciseList!![currentExercisePosition].getImage())
        // set the exercise name
        binding.tvExerciseName.text = exerciseList!![currentExercisePosition].getName()

        // This function is used to set the progress details.
        setExerciseProgressBar()
    }

    private fun setExerciseProgressBar() {
        binding.progressBarExercise.progress = exerciseProgress

        exerciseTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // increase restProgress by 1 value
                exerciseProgress++
                binding.progressBarExercise.progress = 30 - exerciseProgress
                binding.tvTimerExercise.text = (30 - exerciseProgress).toString()
            }

            override fun onFinish() {
                // we need to go back to the rest view again when we are finish
                if (currentExercisePosition < exerciseList?.size!! - 1){
                    setupRestView()
                } else {
                    Toast.makeText(
                        context,
                        "SHIT!! YOU MADE IT AND COMPLETED 7 MINUTES WORKOUT",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }.start()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        if (exerciseTimer != null) {
            exerciseTimer?.cancel()
            exerciseProgress = 0
        }
        super.onDestroy()
        _binding = null
    }
}
