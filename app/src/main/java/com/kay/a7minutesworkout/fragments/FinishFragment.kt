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
import com.kay.a7minutesworkout.R
import com.kay.a7minutesworkout.data.HistoryEntity
import com.kay.a7minutesworkout.databinding.FragmentFinishBinding
import com.kay.a7minutesworkout.model.ExerciseViewModel
import com.kay.a7minutesworkout.model.HistoryDao
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class FinishFragment : Fragment() {

    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!

    private val exerciseViewModel: ExerciseViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFinishBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // toolbar setup for fragments
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarFinishFragment)
        // Back button setup on toolbar for fragments
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnFinish.setOnClickListener {
            findNavController().navigate(R.id.action_finishFragment_to_startFragment)
        }
        addDateToDatabase()
    }

    private fun addDateToDatabase () {
        // setup calender
        val myCalendar = Calendar.getInstance()
        val dateTime = myCalendar.time
        Log.e("Date: ","" + dateTime)

        // Date Formatter
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm:ss", Locale.getDefault())
        // dateTime is formatted in the given format.
        val date = sdf.format(dateTime)
        // Formatted date is printed in the log.
        Log.e("Formatted Date : ", "" + date)

        lifecycleScope.launch {
            exerciseViewModel.insertData(HistoryEntity(date))
            //historyDao.insert(HistoryEntity(date)) // <- Add date function is called
            Log.e(
                "Date : ",
                "Added..."
            ) // Printed in log which is printed if the complete execution is done.
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
