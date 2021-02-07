package com.example.explorer_kotlin.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.explorer_kotlin.databinding.SingleViewItemBinding

import com.example.explorer_kotlin.model.Item

class SearchResultAdapter ( val onClickListener: OnClickListener)
    :ListAdapter<Item, SearchResultAdapter.SearchResultViewHolder>(DiffCallback){

    class SearchResultViewHolder(private var binding: SingleViewItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(itemData : Item)
        {
            binding.property = itemData
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return  oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.data[0].nasa_id == newItem.data[0].nasa_id
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
       return SearchResultViewHolder(SingleViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {

       val property = getItem(position)

        holder.itemView.setOnClickListener {
            onClickListener.onClick(property)
        }
        holder.bind(property)
    }

    class OnClickListener(val clickListener: (property: Item) -> Unit){
        fun onClick (property: Item) = clickListener(property)
    }
}