package com.kay.a7minutesworkout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kay.a7minutesworkout.databinding.FragmentBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiFragment : Fragment() {

    private var _binding: FragmentBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentBmiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // toolbar setup for fragments
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarBmiFragment)
        // Back button setup on toolbar for fragments
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Title for the action bar
        (activity as AppCompatActivity).supportActionBar?.title = "CALCULATE BMI"

        // navigate back with navHost
        binding.toolbarBmiFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        // TODO: call the the function with the button
        binding.btnCalculateUnits.setOnClickListener {
            // we only want to calculate if validate function return true.
            if (validateMetricUnit()) {
                val heightValue: Float = binding.etMetricUnitHeight.text.toString().toFloat() / 100
                val weightValue: Float = binding.etMetricUnitWeight.text.toString().toFloat()
                val bmi = weightValue / (heightValue * heightValue)
                displayBmiResult(bmi)
            } else {
                Toast.makeText(
                    context,
                    "please enter a valid value",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // TODO: Create a function that validate if we have something inside our inputFile
    private fun validateMetricUnit(): Boolean {
        var isValid = true

        if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

    // TODO: Create a function that will display our message and result
    private fun displayBmiResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (bmi.compareTo(25f) > 0 && bmi.compareTo(30f) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }
        binding.linearLayoutDisplayBmiResult.visibility = View.VISIBLE
        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()
        binding.tvBMIValue.text = bmiValue
        binding.tvBMIType.text = bmiLabel
        binding.tvBMIDescription.text = bmiDescription
    }
}
