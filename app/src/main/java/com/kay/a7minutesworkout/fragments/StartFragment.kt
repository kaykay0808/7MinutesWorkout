package com.kay.a7minutesworkout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.kay.a7minutesworkout.R
import com.kay.a7minutesworkout.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentStartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.flStart.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_exerciseFragment)
        }

        binding.flBmi.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_bmiFragment)
        }

        binding.flHistory.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_historyFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
