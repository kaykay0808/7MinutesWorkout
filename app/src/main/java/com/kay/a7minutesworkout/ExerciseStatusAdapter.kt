package com.kay.a7minutesworkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kay.a7minutesworkout.databinding.ItemExerciseStatusBinding

class ExerciseStatusAdapter(val items: MutableList<ExerciseModel>) :
    RecyclerView.Adapter<ExerciseStatusAdapter.MainViewHolder>() {
    class MainViewHolder(val binding: ItemExerciseStatusBinding) :
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

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text =
            model.getId().toString() // <- Getting the Id as a string from our model.
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
