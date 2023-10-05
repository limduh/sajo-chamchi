package com.team4.sajochamchi.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.team4.sajochamchi.data.model.SaveCategory
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
    
    companion object{
        private const val TAG = "UnselectedCategoriesAda"
    }
    
    interface ItemClick {
        fun onClick(position: Int, saveCategory: SaveCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemUnselectedCategoriesBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: $position")
        holder.bind()
    }

    inner class ViewHolder(private val binding: ItemUnselectedCategoriesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val pos = adapterPosition
            val currentItem = currentList[pos]
            tvUnselectedCategoryName.text = currentItem.title
            addImageView.setOnClickListener {
                itemClick.onClick(adapterPosition,currentItem)
            }
        }
    }
}