package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.databinding.ItemSelectedCategoriesBinding

class SelectedCategoriesAdpter(private val itemClick: ItemClick) :
    ListAdapter<SaveCategory, SelectedCategoriesAdpter.ViewHolder>(
        object : DiffUtil.ItemCallback<SaveCategory>() {
            override fun areItemsTheSame(oldItem: SaveCategory, newItem: SaveCategory): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SaveCategory, newItem: SaveCategory): Boolean {
                return oldItem.id == newItem.id
            }

        }
    ) {

    interface ItemClick {
        fun onClick(position: Int, saveCategory: SaveCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemSelectedCategoriesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemSelectedCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val pos = adapterPosition
            val currentItem = currentList[pos]
            tvSelecetedCategoryName.text = currentItem.title
            closeImageButton.setOnClickListener {
                itemClick.onClick(adapterPosition, currentItem)
            }
        }
    }
}