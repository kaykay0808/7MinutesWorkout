package com.kay.a7minutesworkout.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kay.a7minutesworkout.data.HistoryEntity

@Dao
interface HistoryDao {
    @Insert
    suspend fun insert(historyEntity: HistoryEntity)

    // reading data / retrieving data?
    @Query("SELECT * FROM `history-table`")
    fun fetchAllData(): LiveData<List<HistoryEntity>>
}
