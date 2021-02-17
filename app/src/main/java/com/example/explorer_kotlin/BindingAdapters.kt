package com.example.explorer_kotlin

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.explorer_kotlin.model.Item
import com.example.explorer_kotlin.overview.SearchQueryStatus
import com.example.explorer_kotlin.overview.SearchResultAdapter

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

    imgUrl?.let {
       
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}

@BindingAdapter("listSearchResult")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Item>?)
{
    val adapter = recyclerView.adapter as SearchResultAdapter
    adapter.submitList(data)
}


@BindingAdapter("savedDataStatus")
fun bindStatus(statusTextView: TextView, data: List<Item>?) {

    if(data.isNullOrEmpty())
    {
        Log.d("BindingAdapters", "bindStatus, data is null or empty")
        statusTextView.visibility = View.VISIBLE

    }else{
        Log.d("BindingAdapters", "bindStatus, data is not null or empty")
        statusTextView.visibility = View.GONE
    }
}

@BindingAdapter("searchStatus")
fun bindStatus(statusImageView: ImageView, status: SearchQueryStatus?) {
    when (status) {
        SearchQueryStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        SearchQueryStatus.ERROR -> {

            statusImageView.visibility = View.GONE
        }
        SearchQueryStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
        SearchQueryStatus.NO_DATA -> {

            statusImageView.visibility = View.GONE
        }
    }
}
