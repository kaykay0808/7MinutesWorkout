package com.kay.a7minutesworkout.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.kay.a7minutesworkout.data.HistoryDatabase
import com.kay.a7minutesworkout.data.HistoryEntity
import com.kay.a7minutesworkout.repository.ExerciseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {

    private val historyDao = HistoryDatabase.getInstance(application).historyDao()
    private val repository: ExerciseRepository = ExerciseRepository(historyDao)

    val getAllData: LiveData<List<HistoryEntity>> = repository.getAllData

    fun insertData(historyEntity: HistoryEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(historyEntity)
        }
    }
}