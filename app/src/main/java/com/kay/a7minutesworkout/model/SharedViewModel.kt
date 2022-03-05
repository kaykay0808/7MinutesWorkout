package com.kay.a7minutesworkout.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kay.a7minutesworkout.data.HistoryEntity

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val emptyDatabase: MutableLiveData<Boolean> = MutableLiveData(false)

    fun checkIfDatabaseEmpty(historyData: List<HistoryEntity>) {
        emptyDatabase.value = historyData.isEmpty()
    }
}
