package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.databinding.ItemHorizontalVideoBinding


class HorizontalVideoAdapter(private val onClickEvent: (SaveItem) -> (Unit)) :
    ListAdapter<SaveItem, HorizontalVideoAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<SaveItem>() {
        override fun areItemsTheSame(oldItem: SaveItem, newItem: SaveItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SaveItem, newItem: SaveItem): Boolean {
            return oldItem.videoId == newItem.videoId
        }
    }) {

    inner class ViewHolder(private val binding: ItemHorizontalVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val pos = adapterPosition
            val item = currentList[pos]
            Glide.with(root)
                .load(item.thumbnailsUrl)
                .into(itemImageView)

            itemTextView.text = item.title

            root.setOnClickListener {
                onClickEvent(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHorizontalVideoBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}