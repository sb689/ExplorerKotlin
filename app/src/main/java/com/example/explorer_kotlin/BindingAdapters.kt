package com.example.explorer_kotlin

import android.view.View
import android.widget.ImageView
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

@BindingAdapter("searchStatus")
fun bindStatus(statusImageView: ImageView, status: SearchQueryStatus?) {
    when (status) {
        SearchQueryStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        SearchQueryStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        SearchQueryStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}