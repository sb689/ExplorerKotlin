package com.example.explorer_kotlin.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.explorer_kotlin.model.Item

class DetailViewModelFactory(
        private val  result: Item,
        private val app: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(result, app) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
