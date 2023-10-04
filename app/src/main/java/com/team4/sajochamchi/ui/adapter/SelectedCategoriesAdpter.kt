package com.team4.sajochamchi.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.team4.sajochamchi.data.model.SaveCategory
import com.team4.sajochamchi.databinding.ItemSelectedCategoriesBinding

class SelectedCategoriesAdpter (private val mItems: ArrayList<SaveCategory>) : RecyclerView.Adapter<SelectedCategoriesAdpter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemSelectedCategoriesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }
        holder.name.text = mItems[position].title
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return mItems.size
    }

    inner class Holder(binding: ItemSelectedCategoriesBinding) : RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvSelecetedVideoName
        var id = 0
        fun bind(){
            adapterPosition
        }
    }
}