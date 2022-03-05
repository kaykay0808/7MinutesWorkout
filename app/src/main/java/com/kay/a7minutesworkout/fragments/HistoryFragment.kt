package com.kay.a7minutesworkout.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.kay.a7minutesworkout.data.HistoryEntity
import com.kay.a7minutesworkout.fragments.adapters.HistoryAdapter
import com.kay.a7minutesworkout.databinding.FragmentHistoryBinding
import com.kay.a7minutesworkout.model.ExerciseViewModel
import kotlinx.coroutines.launch

class HistoryFragment : Fragment() {

    private val exerciseViewModel: ExerciseViewModel by viewModels()
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // toolbar setup for fragments
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHistoryFragment)
        // Back button setup on toolbar for fragments
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Title for the action bar
        (activity as AppCompatActivity).supportActionBar?.title = "HISTORY"
        // navigate back with navHost
        binding.toolbarHistoryFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // recycler view
        recyclerViewSetup()

        getAllCompletedDates()
    }

    private fun getAllCompletedDates() {
        Log.e("Date: ", "getAllCompletedDates run")
        lifecycleScope.launch {
            exerciseViewModel.getAllData
        }
    }

    private fun recyclerViewSetup() {
        val recyclerView = binding.rvHistory
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun showEmptyDatabaseView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.tvHistory.visibility = View.INVISIBLE
            binding.rvHistory.visibility = View.INVISIBLE
            binding.tvNoDataAvailable.visibility = View.VISIBLE
        } else {
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoDataAvailable.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
