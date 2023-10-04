package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team4.sajochamchi.databinding.ItemHorizontalStringBinding


class SearchHistoryAdapter(private val onClickEventListener: (String) -> (Unit)) :
    ListAdapter<String, SearchHistoryAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }) {

    inner class ViewHolder(private val binding: ItemHorizontalStringBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val pos = adapterPosition
            val item = currentList[pos]
            titleTextView.setOnClickListener {
                onClickEventListener(item)
            }
            deleteImageView.setOnClickListener {
                notifyItemRemoved(adapterPosition)
            }
            titleTextView.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHorizontalStringBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}