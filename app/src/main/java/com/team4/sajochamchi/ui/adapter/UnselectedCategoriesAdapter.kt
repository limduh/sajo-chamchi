package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.databinding.ItemSelectedCategoriesBinding
import com.team4.sajochamchi.databinding.ItemUnselectedCategoriesBinding

class UnselectedCategoriesAdapter(private val itemClick: ItemClick) :
    ListAdapter<SaveCategory, UnselectedCategoriesAdapter.ViewHolder>(
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
        fun onClick(saveCategory: SaveCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemUnselectedCategoriesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemUnselectedCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val pos = adapterPosition
            val currentItem = currentList[pos]
            tvUnselectedCategoryName.text = currentItem.title
            addImageView.setOnClickListener {
                itemClick.onClick(currentItem)
            }
        }
    }
}