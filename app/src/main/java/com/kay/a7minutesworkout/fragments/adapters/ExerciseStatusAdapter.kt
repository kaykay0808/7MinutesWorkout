package com.kay.a7minutesworkout.fragments.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.kay.a7minutesworkout.ExerciseModel
import com.kay.a7minutesworkout.R
import com.kay.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: MutableList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.MainViewHolder>() {
    class MainViewHolder(private val binding: ItemExerciseStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvItem = binding.tvItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    // Assign every id to each circle
    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text =
            model.getId().toString() // <- Getting the Id as a string from our model.

        when {
            model.getIsSelected() -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_thin_color_accent_border
                )
                holder.tvItem.setTextColor(
                    Color.parseColor("#212121")
                )
            }
            model.getIsCompleted() -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_accent_background
                )
                holder.tvItem.setTextColor(
                    Color.parseColor("#FFFFFF")
                )
            }
            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.itemView.context,
                    R.drawable.item_circular_color_gray_background
                )
                holder.tvItem.setTextColor(
                    Color.parseColor("#FFFFFF")
                )
            }
        }
    }

    // This say how many elements we have
    override fun getItemCount(): Int {
        return items.size
    }
}
