package com.kay.a7minutesworkout.fragments.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kay.a7minutesworkout.R
import com.kay.a7minutesworkout.data.HistoryEntity
import com.kay.a7minutesworkout.databinding.ItemHistoryRowBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {

    var items = emptyList<HistoryEntity>()

    class ViewHolder(binding: ItemHistoryRowBinding):RecyclerView.ViewHolder(binding.root) {
        val llHistoryItemMain = binding.llHistoryItemMain
        val tvItem = binding.tvItem
        val tvPosition = binding.tvPosition
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHistoryRowBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = items[position]
        holder.tvPosition.text = (position + 1).toString()
        holder.tvItem.text = date.toString()

        // change the background color of the linearLayout
        // %2 means every second items
        if (position % 2 == 0) {
            holder.llHistoryItemMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.lightGrey
                )
            )
        } else {
            holder.llHistoryItemMain.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(historyEntity : List<HistoryEntity>){
        val historyDiffUtil = HistoryRecyclerViewDiffUtil(items, historyEntity)
        val historyDiffResult = DiffUtil.calculateDiff(historyDiffUtil)
        this.items = historyEntity
        historyDiffResult.dispatchUpdatesTo(this)
    }
}