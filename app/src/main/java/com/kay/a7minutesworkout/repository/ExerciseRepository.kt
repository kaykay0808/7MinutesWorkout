package com.kay.a7minutesworkout.repository

import androidx.lifecycle.LiveData
import com.kay.a7minutesworkout.data.HistoryEntity
import com.kay.a7minutesworkout.model.HistoryDao

class ExerciseRepository(private val historyDao: HistoryDao) {

    val getAllData: LiveData<List<HistoryEntity>> = historyDao.fetchAllData()

    suspend fun insert(employeeEntity: HistoryEntity) {
        historyDao.insert(employeeEntity)
    }
}
