package com.kay.a7minutesworkout.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.kay.a7minutesworkout.R
import com.kay.a7minutesworkout.databinding.FragmentBmiBinding
import com.kay.a7minutesworkout.databinding.FragmentExerciseBinding


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
    }
}