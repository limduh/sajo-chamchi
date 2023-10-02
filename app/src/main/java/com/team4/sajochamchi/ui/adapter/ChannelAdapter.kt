package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team4.sajochamchi.data.model.SaveChannel
import com.team4.sajochamchi.databinding.ItemRecyclerViewBinding

class ChannelAdapter : RecyclerView.Adapter<ChannelAdapter.ViewHolder>() {

    private val list = arrayListOf<SaveChannel>()

    fun addItems(items: List<SaveChannel>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ViewHolder(
        private val binding: ItemRecyclerViewBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SaveChannel) = with(binding) {
            itemTextView.text = item.title
            Glide.with(root)
                .load(item.thumbnailUrl)
                .into(itemImageView)
        }
    }
}