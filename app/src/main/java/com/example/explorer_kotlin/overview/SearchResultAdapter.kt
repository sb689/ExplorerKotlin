package com.example.explorer_kotlin.overview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.explorer_kotlin.databinding.GridViewItemBinding
import com.example.explorer_kotlin.model.Data
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.model.SpaceResponse

class SearchResultAdapter ( val onClickListener: OnClickListener)
    :ListAdapter<Item, SearchResultAdapter.SearchResultVieHolder>(DiffCallback){

    class SearchResultVieHolder(private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(itemData : Item)
        {
            binding.property = itemData
            binding.executePendingBindings()
        }
    }


        companion object DiffCallback: DiffUtil.ItemCallback<Item>() {
            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return  oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem.data[0].nasa_id == newItem.data[0].nasa_id
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultVieHolder {
       return SearchResultVieHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: SearchResultVieHolder, position: Int) {

       val property = getItem(position)
        holder.bind(property)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(property)
        }
    }

    class OnClickListener(val clickListener: (property: Item) -> Unit){
        fun onClick (property: Item) = clickListener(property)
    }
}