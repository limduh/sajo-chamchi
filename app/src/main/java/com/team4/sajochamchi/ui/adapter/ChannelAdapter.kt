package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team4.sajochamchi.data.model.SaveChannel
import com.team4.sajochamchi.databinding.ItemHorizontalChannelBinding
import com.team4.sajochamchi.databinding.ItemHorizontalVideoBinding


class ChannelAdapter(private val onClickEvent: (SaveChannel) -> (Unit)) :
    ListAdapter<SaveChannel, ChannelAdapter.ViewHolder>(object :
        DiffUtil.ItemCallback<SaveChannel>() {
        override fun areItemsTheSame(oldItem: SaveChannel, newItem: SaveChannel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: SaveChannel, newItem: SaveChannel): Boolean {
            return oldItem.thumbnailUrl == newItem.thumbnailUrl
        }
    }) {

    inner class ViewHolder(private val binding: ItemHorizontalChannelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val pos = adapterPosition
            val item = currentList[pos]
            Glide.with(root)
                .load(item.thumbnailUrl)
                .into(itemImageView)

            itemTextView.text = item.title

            root.setOnClickListener {
                onClickEvent(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(ItemHorizontalChannelBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }
}