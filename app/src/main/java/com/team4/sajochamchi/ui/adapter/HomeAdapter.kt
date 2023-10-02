package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.team4.sajochamchi.data.model.SaveItem
import com.team4.sajochamchi.databinding.ItemRecyclerViewBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.ItemViewHolder>() {

    private val list = arrayListOf<SaveItem>()

    fun addItems(items: List<SaveItem>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding =
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ItemViewHolder(private val binding: ItemRecyclerViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SaveItem) = with(binding) {

            Glide.with(root)
                .load(item.thumbnailsUrl)
                .into(itemImageView)

            itemTextView.text = item.title
        }

//        var recently_image_view: ImageView = binding.itemImageView
//        var recently_const: ConstraintLayout = binding.itemLayout
//        override fun onClick(v: View?) {
//            TODO("Not yet implemented")
//        }
    }
}