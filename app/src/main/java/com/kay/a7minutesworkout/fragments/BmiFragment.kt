package com.kay.a7minutesworkout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kay.a7minutesworkout.R
import com.kay.a7minutesworkout.databinding.FragmentBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BmiFragment : Fragment() {

    companion object {
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW" // Metric Unit View
        private const val US_UNITS_VIEW = "US_UNIT_VIEW" // US Unit View
    }

    // set Default group button
    private var currentVisibleView: String =
        METRIC_UNITS_VIEW // A variable to hold a value to make a selected view visible

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
            calculateUnits()
        }
        // RadioGroup
        makeMetricUnitViewVisible()

        // Add a changeListener to our radioGroup
        binding.radioGroupUnits.setOnCheckedChangeListener { _, checkedId: Int ->
            if (checkedId == R.id.rbMetricUnits) {
                makeMetricUnitViewVisible()
            } else {
                makeUsUnitViewVisible()
            }
        }
    }

    // TODO: Create a function that validate if we have something inside our inputFile
    private fun validateMetricUnit(): Boolean {
        var isValid = true

        when {
            binding.etMetricUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etMetricUnitHeight.text.toString().isEmpty() -> {
                isValid = false
            }
        }

        /*if (binding.etMetricUnitWeight.text.toString().isEmpty()) {
            isValid = false
        } else if (binding.etMetricUnitHeight.text.toString().isEmpty()) {
            isValid = false
        }*/
        return isValid
    }

    private fun validateUsUnits(): Boolean {
        var isValid = true
        when {
            binding.etUsUnitWeight.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsUnitHeightFeet.text.toString().isEmpty() -> {
                isValid = false
            }
            binding.etUsUnitHeightInch.text.toString().isEmpty() -> {
                isValid = false
            }
        }
        return isValid
    }

    // Calculating Us System
    private fun calculateUnits() {
        if (currentVisibleView == METRIC_UNITS_VIEW) {
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
        } else {
            if (validateUsUnits()) {
                val usHeightValueFeet: String = binding.etUsUnitHeightFeet.text.toString()
                val usHeightValueInch: String = binding.etUsUnitHeightInch.text.toString()
                val usWeightValue: Float = binding.etUsUnitWeight.text.toString().toFloat()

                // The weird us formula
                // Merge the height feet and inch values and multiply by 12
                val heightValue = usHeightValueInch.toFloat() + usHeightValueFeet.toFloat() * 12

                // The bmi calculation
                val bmi = 703 * (usWeightValue / (heightValue * heightValue))

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

    // Function that make metric unit Visible
    private fun makeMetricUnitViewVisible() {
        // Current View is updated here
        currentVisibleView = METRIC_UNITS_VIEW
        binding.linearLayoutMetricTextInputHolder.visibility =
            View.VISIBLE // Show metric system LinearLayout
        binding.linearUsLayoutTextInputHolder.visibility = View.INVISIBLE

        binding.etMetricUnitHeight.text!!.clear() // height value is cleared if it is added.
        binding.etMetricUnitWeight.text!!.clear() // weight value is cleared if it is added.

        binding.linearLayoutDisplayBmiResult.visibility = View.INVISIBLE
    }

    // Function that make US Unit view visible.
    private fun makeUsUnitViewVisible() {
        // Current View is updated here
        currentVisibleView = US_UNITS_VIEW
        binding.linearLayoutMetricTextInputHolder.visibility =
            View.INVISIBLE // Show metric system LinearLayout
        binding.linearUsLayoutTextInputHolder.visibility = View.VISIBLE

        binding.etUsUnitHeightFeet.text!!.clear() // height value is cleared if it is added.
        binding.etUsUnitHeightInch.text!!.clear() // height value is cleared if it is added.
        binding.etMetricUnitWeight.text!!.clear() // weight value is cleared if it is added.

        binding.linearLayoutDisplayBmiResult.visibility = View.INVISIBLE
    }
}
