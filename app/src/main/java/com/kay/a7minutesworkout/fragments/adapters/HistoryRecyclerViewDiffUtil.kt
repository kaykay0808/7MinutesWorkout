package com.kay.a7minutesworkout.fragments.adapters

import androidx.recyclerview.widget.DiffUtil
import com.kay.a7minutesworkout.data.HistoryEntity

class HistoryRecyclerViewDiffUtil(
    private val oldList: List<HistoryEntity>,
    private val newList: List<HistoryEntity>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].date == newList[newItemPosition].date
    }
}
