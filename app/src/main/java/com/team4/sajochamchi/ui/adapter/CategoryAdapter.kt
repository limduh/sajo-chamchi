package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.databinding.ItemRecyclerViewBinding

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.HomeViewHolder>() {

    private val list = arrayListOf<SaveItem>()

    fun addItems(items: List<SaveItem>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        return HomeViewHolder(
            ItemRecyclerViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class HomeViewHolder(private val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SaveItem) = with(binding) {
            itemTextView.text = item.title
            Glide.with(root)
                .load(item.thumbnailsUrl)
                .into(itemImageView)
        }
    }
}