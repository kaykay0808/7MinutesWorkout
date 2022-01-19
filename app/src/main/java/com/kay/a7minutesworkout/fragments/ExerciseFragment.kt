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
import com.kay.a7minutesworkout.databinding.FragmentExerciseBinding

class ExerciseFragment : Fragment() {

    // Variable for 10 seconds rest timer?
    // Variable for Rest timer. We will initialize it later.
    private var restTimer: CountDownTimer? = null

    // Variable for timer progress. As initial value the rest progress is set to 0. as we are about to start
    private var restProgress = 0

    // exercise timer and exerciseProgress
    private var exerciseTimer: CountDownTimer? = null
    private var exerciseProgress = 0

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
        // toolbar setup
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarExercise)
        // Back button on toolbar
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // navigate back with navHost
        binding.toolbarExercise.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        setupRestView()
    }

    private fun setupRestView() {
        /**
         * Here firstly we will check if the timer is running the and it is not null then cancel the running timer and start the new one.
         * And set the progress to initial which is 0.
         */
        // In other words, we reset the timer if we go back to the first fragment
        if (restTimer != null) {
            restTimer!!.cancel()
            restProgress = 0
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
                binding.progressBar.progress = 10 - restProgress
                binding.tvTimer.text = (10 - restProgress).toString()
            }

            override fun onFinish() {
                setupExerciseView()
            }
        }.start()
    }

    private fun setupExerciseView() {
        // In other words, we reset the timer if we go back to the first fragment
        binding.flProgressBar.visibility = View.INVISIBLE
        binding.tvTitle.text = "Exercise Name"
        binding.flExerciseView.visibility = View.VISIBLE
        if (exerciseTimer != null) {
            exerciseTimer!!.cancel()
            exerciseProgress = 0
        }
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
                Toast.makeText(
                    context,
                    "30 seconds are over, lets go to the rest view",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.start()
    }

    override fun onDestroy() {
        if (restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        super.onDestroy()
        _binding = null
    }
}
