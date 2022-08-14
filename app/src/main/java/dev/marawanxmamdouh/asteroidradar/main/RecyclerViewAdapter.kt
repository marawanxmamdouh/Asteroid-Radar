package dev.marawanxmamdouh.asteroidradar.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.marawanxmamdouh.asteroidradar.databinding.AsteroidListItemBinding
import dev.marawanxmamdouh.asteroidradar.model.Asteroid

class RecyclerViewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Asteroid, RecyclerViewAdapter.ViewHolder>(AsteroidDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AsteroidListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val asteroid = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(asteroid)
        }
        holder.bind(asteroid)
    }

    class ViewHolder(private val binding: AsteroidListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(asteroid: Asteroid) {
            binding.asteroid = asteroid
            binding.executePendingBindings()
        }
    }

    companion object AsteroidDiffCallback : DiffUtil.ItemCallback<Asteroid>() {
        override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (asteroid: Asteroid) -> Unit) {
        fun onClick(asteroid: Asteroid) = clickListener(asteroid)
    }
}